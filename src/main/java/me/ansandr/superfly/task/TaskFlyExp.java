package me.ansandr.superfly.task;

import me.ansandr.superfly.Fly;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ansandr.superfly.util.MessageManager.tl;

public class TaskFlyExp extends BukkitRunnable {
	
	private Fly fly;
	private Player player;
	private float xpCost;
	
	public TaskFlyExp(Fly fly, float xpCost) {
		this.fly = fly;
		this.player = fly.getPlayer();
		this.xpCost = xpCost;
	}

	@Override
	public void run() {
		float currentProgress = player.getExp();
		
		if (player.getLevel() == 0 && currentProgress < xpCost) {
			player.sendMessage(tl("exp_over"));
			fly.disableFly();
			this.cancel();
			return;
		}
		
		float newProgress = currentProgress - xpCost;
		
		if (newProgress < 0) {
			player.setLevel(player.getLevel() - 1);
			player.setExp(1.0f + newProgress);
		} else {
			player.setExp(newProgress);
		}
	}

}
