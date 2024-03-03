package me.ansandr.superfly.util;

import me.ansandr.superfly.SuperFly;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * Класс для получений сообщений с помощью path
 */
public class MessageManager {
	
	private static MessageManager instance;
	private FileConfiguration config;
	public String prefix;
	
	public MessageManager(SuperFly plugin) {
		config = plugin.getMessageConfig();
		prefix = config.getString("prefix");
	}
	
	public void onEnable() {
		instance = this;
	}
	
	public static String tl(String path, Object... objects) {
		if (instance == null) {
			return "";
		}
		
		if (objects.length == 0) {
			return StringUtils.color(instance.get(path));
		} else {
			return StringUtils.color(instance.format(path, objects));
		}
	}
	
	private String format(String path, Object... objects) {
		
		String message = get(path);
		MessageFormat messageFormat = new MessageFormat(message);
		
		messageFormat.format(objects).replace("\u00a0", " ");
		return message;
	}
	
	public String get(String path) {
		try {
			return config.getString(path).replace("{prefix}", prefix);
		} catch (MissingResourceException ex) {
			SuperFly.getInstance().getLogger().warning(String.format("Missing translation key \"%s\" in file messages.yml", ex.getKey(), ex));
		}
		return path; //�����
	}
//	
//	String replace(String str, String... parameters){
//	for(int i = 0; i < parameters.length - 1; i = i+2) {
//		str.replaceAll("%" + parameters[i] + "%", parameters[i+1]);
//	}
//	return str;
//}
}
