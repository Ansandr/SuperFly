package me.ansandr.superfly;

import me.ansandr.superfly.commands.Commandsetflyloc;
import me.ansandr.superfly.commands.Commandsuperfly;
import me.ansandr.superfly.commands.FlyCommand;
import me.ansandr.superfly.hooks.VaultHook;
import me.ansandr.superfly.listener.PlayerListener;
import me.ansandr.superfly.settings.Localization;
import me.ansandr.superfly.settings.Settings;
import me.ansandr.superfly.util.MessageManager;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperFly extends JavaPlugin {
	
	private static SuperFly instance;
	// LOCALIZATION
	private Localization localization;
	private MessageManager messageManager;
	
	// HOOKS
	private VaultHook vaultHook;
	
	//CONFIGS
	private Settings settings;
	private FileConfiguration messagesConfig;
	
	// STORAGE
	public List<Integer> maxDistancePermOptions, timePermOptions;
	public static Map<Player, Fly> STORAGE = new HashMap<>();
	
	@Override
	public void onEnable() {
		onReload();
		
		// Messages manager
		messageManager = new MessageManager(this);
		messageManager.onEnable();
		
		new FlyingManager();
		
		//Register commands
		getCommand("fly").setExecutor(new FlyCommand(this));
		getCommand("superfly").setExecutor(new Commandsuperfly(this));
		getCommand("setflyloc").setExecutor(new Commandsetflyloc(this));
		
		// Register Events
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		instance = this;
	}
	
	public void onReload() {
		// Generate config
		this.saveDefaultConfig();
		this.reloadConfig();

		settings = new Settings(this);
		
		// Generate message config
		localization = new Localization(this);
		localization.saveDefaultConfig();
		
		this.saveResource("messages.yml", true);
		localization.reloadLanguageConfig();
		
		messagesConfig = localization.getConfig();
		// Register permissions
		registerPermissions();
		
		if (!setupVault()) {
            getLogger().warning(String.format("[%s] - ������ Vault �� ������!", getDescription().getName()));
            getLogger().warning(String.format("�������� ����� �� ������� ���������!")); //TODO
            return;
        }
	}
	
	private boolean setupVault() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        vaultHook = new VaultHook();
        
        return vaultHook.hooked();
	}

	private void registerPermissions() {
		boolean enableRadius = Settings.FLY_RADIUS_ENABLE;
		boolean enableTime = Settings.FLY_TEMP_ENABLE;
		boolean enableWorldPermissions = Settings.ENABLE_WORLD_PERMISSION;
		if (enableRadius || enableTime || enableWorldPermissions) {
			PluginManager pm = getServer().getPluginManager();
			// load radius permissions
			if (enableRadius) {
				maxDistancePermOptions = Settings.FLY_RADIUS_OPTIONS;
				registerDistancePerms(pm);
			}
			// load time permissions
			if (enableTime) {
				timePermOptions = Settings.FLY_TEMP_OPTIONS;
				registerTimePerms(pm);
			}
			// load world permissions
			if (enableWorldPermissions) {
				for (World world: this.getServer().getWorlds()) {
					String permission = "superfly.world." + world.getName();
					if (pm.getPermission(permission) == null) {
						pm.addPermission(new Permission(permission, "allow player to fly in specific world", PermissionDefault.FALSE));
					}
				}
			}
		}
	}
	
	private void registerDistancePerms(PluginManager pm) {
		//distance permissions
	    for (int number : maxDistancePermOptions) {
	    	String permission = "superfly.radius." + number;
	    	if (pm.getPermission(permission) == null) {
	    		pm.addPermission(new Permission(permission, "The max radius where the player can reach" ,PermissionDefault.FALSE));
	    	}
	    }
	}
	
	private void registerTimePerms(PluginManager pm) {
		//time permissions
	    for (int number : timePermOptions) {
	    	String permission = "superfly.time." + number;
 	    	if (pm.getPermission(permission) == null) {
	    		pm.addPermission(new Permission(permission, "allow player to fly certain amount of time" ,PermissionDefault.FALSE));
	    	}
	    }
	}
	
	public static SuperFly getInstance() {
		return instance;
	}
	
	public VaultHook getVault() {
		return vaultHook;
	}
	
	public FileConfiguration getMessageConfig() {
		return this.messagesConfig;
	}

	public Settings getSettings() {
		return settings;
	}
}
