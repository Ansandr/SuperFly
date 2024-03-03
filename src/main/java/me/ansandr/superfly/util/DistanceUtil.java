package me.ansandr.superfly.util;

import me.ansandr.superfly.settings.Settings;
import org.bukkit.Location;

public class DistanceUtil {
	
	public static boolean isInside(Location start, Location current, int range, Settings.Radius option) {

		if (option == Settings.Radius.CUBE) {
			return isInCube(start, current, range);
		} else if (option == Settings.Radius.SPHERE) {
			return isInSphere(start, current, range);
		}

		
		return true;
	}
	
	private static boolean isInSphere(Location start, Location currect, int range) {
		if(start.distanceSquared(currect) > range * range) {
			return true;
		} else {
			return false;
		}
	}
	
	
	private static boolean isInCube(Location start, Location current, int range) {
		int x = start.getBlockX(), y = start.getBlockY(), z = start.getBlockZ(); 			//Start fly cooridnates
		int x1 = current.getBlockX(), y1 = current.getBlockY(), z1 = current.getBlockZ(); 	//PLAYER coordinates
		
		if (
				x1 <= (x + range) &&
				x1 >= (x - range) &&
				y1 <= (y + range) &&
				y1 >= (y - range) &&
				z1 <= (z + range) &&
				z1 >= (z - range)
				) {
			return true;
		} else {
			return false;
		}
		
	}
}
