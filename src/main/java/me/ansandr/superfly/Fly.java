package me.ansandr.superfly;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import static me.ansandr.superfly.util.MessageManager.tl;

public class Fly {
	
	private Player player;
	private Location loc;
	private double moneyCost;
	private float xpCost;
	private int radius;
	private int time;
	private boolean isFlying;
	public boolean tpLock;

	
	public Fly(Player player, Location loc, int radius, int time) {
		this.player = player;
		this.loc = loc;
		this.radius = radius;
		this.time = time;
	}
	
	public Fly(Player player, int radius, int time) {
		this.player = player;
		this.loc = player.getLocation();
		this.radius = radius;
		this.time = time;
	}
	
	public void enableFly() {
		player.setFallDistance(0f);
		player.setAllowFlight(true);
		isFlying = true;
		player.sendMessage(tl("flyMode_enabled"));
	}
	
	public void disableFly() {
		if (!player.hasPermission("superfly.stay")) {
			teleport();
		}
		player.setAllowFlight(false);
		isFlying = false;

		player.sendMessage(tl("flyMode_disabled"));
	}
	
	// ���������� ��������� �������
	public void putLocation(Location loc) {
		this.loc = loc;
	}
	
	// Return to start location
	public void teleport() {
		if (loc != null) {
			tpLock = false;
			player.teleport(loc);
			tpLock = true;
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public float getExpCost() {
		return xpCost;
	}
	
	public double getMoneyCost() {
		return moneyCost;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getTime() {
		return time;
	}
	
	public boolean isFlying() {
		if (isFlying) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLocked() {
		return tpLock;
	}
}
