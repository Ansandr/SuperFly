package me.ansandr.superfly.task;

import me.ansandr.superfly.SuperFly;
import me.ansandr.superfly.settings.Settings;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskFlyParticle extends BukkitRunnable {

	private Player player;
	
	private double x;
	private double y;
	private double z;
	
	public TaskFlyParticle(Player player, SuperFly plugin) {
		this.player = player;
		getRelativeCoordinates(Settings.PARTICLE_LOCATION);
		if (Settings.PARTICLE_SOUND) {
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_BURN, 5, 5);
		}
	}
	
	@Override
	public void run() {
		if (player.isFlying()) {
			Location pLoc = player.getLocation();

			Location loc = pLoc.add(x, y, z);

			player.spawnParticle(Particle.CLOUD, loc, 2, 0, 0, 0, 0.1);
		} else {
			this.cancel();
		}

	}
	
	private void getRelativeCoordinates(String cords) {
		cords = cords.replace("~", "");

		String[] cord = cords.split(" ");

		x = Double.valueOf(cord[0]);
		y = Double.valueOf(cord[1]);
		z = Double.valueOf(cord[2]);
	}
}
