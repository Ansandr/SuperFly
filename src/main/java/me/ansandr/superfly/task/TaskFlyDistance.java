package me.ansandr.superfly.task;

import me.ansandr.superfly.Fly;
import me.ansandr.superfly.settings.Settings;
import me.ansandr.superfly.util.DistanceUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.ansandr.superfly.util.MessageManager.tl;

public class TaskFlyDistance extends BukkitRunnable {
	
	private Fly fly;
	private Location startLoc;
	private Player player;
	private Settings.Radius option;
	private int radius;
	
	public TaskFlyDistance(Fly fly, Settings.Radius option) {
		this.fly = fly;
		this.player = fly.getPlayer();
		this.startLoc = fly.getLocation(); //��������� ������� ������
		this.option = option;
		this.radius = fly.getRadius();
	}
	
	@Override
	public void run() {
		if (!player.isOnline()) this.cancel(); // ���� ������
		
		if (!fly.isFlying()) this.cancel(); //���� ���� ��������
		
		Location curLoc = player.getLocation(); //������� ������� ������
		
		if (!startLoc.getWorld().equals(curLoc.getWorld())) this.cancel();

		if (!DistanceUtil.isInside(startLoc, curLoc, radius, option) && !player.hasPermission("superfly.distance.unlimited")) {
			player.sendMessage(tl("distance_over"));
			player.setVelocity(
					(startLoc.toVector().subtract(curLoc.toVector())).multiply(0.05f)
			);
			//fly.teleport();
		}
	}
}
