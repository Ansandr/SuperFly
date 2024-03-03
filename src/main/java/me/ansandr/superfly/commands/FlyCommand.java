package me.ansandr.superfly.commands;

import me.ansandr.superfly.Fly;
import me.ansandr.superfly.SuperFly;
import me.ansandr.superfly.hooks.VaultHook;
import me.ansandr.superfly.settings.Settings;
import me.ansandr.superfly.task.TaskFlyDistance;
import me.ansandr.superfly.task.TaskFlyExp;
import me.ansandr.superfly.task.TaskFlyMoney;
import me.ansandr.superfly.task.TaskFlyTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static me.ansandr.superfly.FlyingManager.storage;
import static me.ansandr.superfly.util.MessageManager.tl;

public class FlyCommand implements CommandExecutor {
	
	private SuperFly plugin;
	
	public FlyCommand(SuperFly plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//FLY YOURSELF
		if (args.length == 0 && sender.hasPermission("superfly.fly")) {
			if (!(sender instanceof Player)) return false;
			Player player = (Player)sender;

			togglePlayer(player);
			return true;
		}

		//FLY OTHER
		if (args.length == 1 && sender.hasPermission("superfly.fly.others")) {
			Player player = plugin.getServer().getPlayerExact(args[0]);
			if (player != null) {
				toggleOtherPlayer(player, sender);
				return true;
			}
		}
		
		/*try {
			//FLY OTHER
			if (args.length == 1) {
				Player player = plugin.getServer().getPlayerExact(args[0]);
				if (player != null) {
					toggleOtherPlayer(player, sender);
					return true;
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			sender.sendMessage(tl("incorect_usage"));
		}*/
		
		sender.sendMessage(tl("no_perm"));
		return false;
	}
	
	private void toggleOtherPlayer(Player player, CommandSender sender) {
		togglePlayer(player);
		boolean isFlying = player.getAllowFlight();
		sender.sendMessage(tl("flyMode_other_" + (isFlying ? "enabled" : "disabled")).replace("{player}", player.getName()));
	}
	
	private void togglePlayer(Player player) {
		// get radius and time
		int radius = 0, time = 0;
		if (Settings.FLY_RADIUS_ENABLE) {
			radius = getRadiusLimit(player);
		}
		if (Settings.FLY_TEMP_ENABLE) {
			time = getTimeLimit(player);
		}
		
		boolean isFlying = player.getAllowFlight();
		
		if (!isFlying) { //if can't fly
			
			if (Settings.FLY_EXP_ENABLED) { // ���� xpfly �������
				float xpCost = Settings.FLY_EXP_COST;
				if (player.getExp() == 0 && player.getExp() < xpCost) { //���� ���� �����
					player.sendMessage(tl("no_enought_exp"));
					return;
				}
				//TODO create exp requirement
			}
			
			if (Settings.FLY_MONEY_ENABLED) {
				if (plugin.getVault() != null) {
					if (plugin.getVault().getEconomy().getBalance(player) <= 0) {//���� ��� �����
						player.sendMessage(tl("no_enought_money"));
						return;
					}
					//TODO create money requirement
				}
				
			}
			// Creating fly object
			Fly fly = new Fly(player, radius, time);
			
			fly.enableFly(); //isFlying = true
			
			if (player.hasPermission("superfly.stay")) {
				fly.putLocation(player.getLocation());
			}
			
			storage.put(player, fly);

			taskSchedulers(fly);
		} else { // else can fly
			Fly fly = storage.remove(player);
			
			if (fly == null) {
				player.setAllowFlight(false); //isFlying = false
				return;
			}
			
			fly.disableFly(); //isFlying = false
		}
	}
	
	private void taskSchedulers(Fly fly) {//TODO move to Fly#taskSchedulers
		if (fly.getRadius() > 0) {
			new TaskFlyDistance(fly, Settings.FLY_RADIUS_CHECKTYPE).runTaskTimer(plugin, 20, Settings.FLY_RADIUS_PERIOD);
		}
		if (fly.getTime() > 0) {
			new TaskFlyTimer(fly).runTaskTimer(plugin, 20, 20);
		}
		if (Settings.FLY_EXP_ENABLED) {
			
			if (fly.getPlayer().getExp() > 0) {
				new TaskFlyExp(fly, fly.getExpCost()).runTaskTimer(plugin, 1, Settings.FLY_EXP_PERIOD);
			}
		}
		if (Settings.FLY_MONEY_ENABLED) {
			VaultHook vaultHook = plugin.getVault();
			if (vaultHook.getEconomy().getBalance(fly.getPlayer()) > 0) {
				new TaskFlyMoney(fly, vaultHook, fly.getMoneyCost()).runTaskTimer(plugin, 1, Settings.FLY_MONEY_PERIOD);
			}
		}
	}
	
	private int getRadiusLimit(Player player) {
		if (player.hasPermission("superfly.radius.unlimited")) return 0;
		
		for (int maxDistance : plugin.maxDistancePermOptions) {
			if (player.hasPermission("superfly.radius." + maxDistance)) {
				return maxDistance;
			}
		}
		return plugin.getConfig().getInt("radiusFly.defaultRadius");
	}
	
	private int getTimeLimit(Player player) {
		if (player.hasPermission("superfly.time.unlimited")) return 0;
		
		for (int maxDistance : plugin.timePermOptions) {
			if (player.hasPermission("superfly.time." + maxDistance)) {
				return maxDistance;
			}
		}
		return plugin.getConfig().getInt("tempFly.defaultTime");
	}
}