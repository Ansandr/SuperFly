package me.ansandr.superfly.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
@SuppressWarnings("unused")
public class StringUtils {
	
	private static final Pattern HEX_PATTERN = Pattern.compile("&(#[a-f0-9]{6})", 2);//TODO
	
	public static String color(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}
	
	private List<String> color(List<String> input){
        List<String> clore = new ArrayList<>();
        for(String s : input){
            clore.add(color(s));
        }
        return clore;
    }
}
