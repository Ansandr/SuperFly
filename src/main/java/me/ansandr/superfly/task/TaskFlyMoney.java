package me.ansandr.superfly.task;

import me.ansandr.superfly.Fly;
import me.ansandr.superfly.hooks.VaultHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ansandr.superfly.util.MessageManager.tl;

public class TaskFlyMoney extends BukkitRunnable {
	
	private Fly fly;
	private VaultHook vaulthook;
	private Economy econ;
	private Player player;
	private double moneyCost;
	
	public TaskFlyMoney(Fly fly, VaultHook vaulthook, double moneyCost) {
		this.fly = fly;
		this.vaulthook = vaulthook;
		this.player = fly.getPlayer();
		this.moneyCost = moneyCost;
	}

	@Override
	public void run() {
		double curMoney = econ.getBalance(player);
		
		
		if (econ.getBalance(player) == 0 && curMoney < moneyCost) {
			fly.disableFly();
			player.sendMessage(tl("money_over"));
			this.cancel();
			return;
		}
		// else
		vaulthook.takeMoney(player, moneyCost);
	}
}
