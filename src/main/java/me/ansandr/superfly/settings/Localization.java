package me.ansandr.superfly.settings;

import me.ansandr.superfly.SuperFly;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Localization {
	
	private FileConfiguration messageConfig = null;
	private File messageFile = null;
	
	private SuperFly plugin;
	
	public Localization(SuperFly plugin) {
		this.plugin = plugin;
		File dataFolder = plugin.getDataFolder();
        if (dataFolder == null)
            throw new IllegalStateException();
        this.messageFile = new File(plugin.getDataFolder(), "messages.yml");
	}
	
	public void reloadLanguageConfig() {
	    if (messageFile == null) {
	    	messageFile = new File(plugin.getDataFolder(), "messages.yml");
	    }
	    messageConfig = YamlConfiguration.loadConfiguration(messageFile);

	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("messages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            messageConfig.setDefaults(defConfig);
        }
	}
	
	public FileConfiguration getConfig() {
	    if (messageConfig == null) {
	    	reloadLanguageConfig();
	    }
	    return messageConfig;
	}
	
	public void saveConfig() {
        if (messageConfig != null && messageFile != null) {
            try {
            	getConfig().save(messageFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + messageFile, ex);
            }
        }
    }
    
    public void saveDefaultConfig() {
        if (!messageFile.exists()) {            
            this.plugin.saveResource("messages.yml", false);
        }
    }
}