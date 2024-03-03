package me.ansandr.superfly.task;

import me.ansandr.superfly.Fly;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ansandr.superfly.util.MessageManager.tl;

public class TaskFlyTimer extends BukkitRunnable {
	
	private Fly fly;
	private Player player;
	BossBar timerBossBar;
	private String color;
	private int seconds;
	
	public TaskFlyTimer(Fly fly) {
		this.fly = fly;
		this.player = fly.getPlayer();
		this.seconds = fly.getTime();
		timerBossBar = Bukkit.createBossBar("countdown", BarColor.valueOf(color), BarStyle.SOLID);
		
	}
	
	@Override
	public void run() {
		if (!fly.isFlying()) this.cancel();
		
		if ((seconds -= 1) == 0) {
			this.cancel();
			timerBossBar.removeAll();
		} else {
			timerBossBar.setProgress(seconds / 10D);
		}
		
		if (seconds == 0) {
			if (player.hasPermission("superfly.stay")) {
				fly.teleport();
			}
			player.sendMessage(tl("time_over"));
			this.cancel();
		}
	}
	
	private void bossBarTimer() {
		
	}
	
	private void chatTimer() {
		player.sendMessage(seconds-- + " ������ ��������");
	}
	
	private void actionBarTimer() {
		
	}
}
