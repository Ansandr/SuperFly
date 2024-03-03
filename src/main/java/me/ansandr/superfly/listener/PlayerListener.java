package me.ansandr.superfly.listener;

import me.ansandr.superfly.SuperFly;
import me.ansandr.superfly.settings.Settings;
import me.ansandr.superfly.task.TaskFlyParticle;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import static me.ansandr.superfly.FlyingManager.storage;
import static me.ansandr.superfly.util.MessageManager.tl;


public class PlayerListener implements Listener {
	
	private SuperFly plugin;
	
	public PlayerListener(SuperFly plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (storage.containsKey(p)) {
			storage.remove(p);
		}
	}
	//PARTICLE
	@EventHandler
	public void onFlying(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.getAllowFlight()) {
			if (p.getGameMode() == GameMode.SPECTATOR) return;
			new TaskFlyParticle(e.getPlayer(), plugin).runTaskTimer(plugin, 1, 3);
		}
	}
	
	//RESTRICATIONS
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if (storage.containsKey(p)) {
			if (!p.hasPermission("superfly.teleport")) {
				if (storage.get(p).isLocked()) {
					e.setCancelled(true);
					p.sendMessage(tl("cannot_teleport"));
				}
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player damager)) return; 	//Если не игрок

		if (damager.isFlying()) {									// Если летае
			if (e.getEntity() instanceof Player) {					// Если ударил игрока
				if (!Settings.REST_PVP) return;
				if (!damager.hasPermission("superfly.bypass.pvp")) {
					damager.sendMessage(tl("restriction.pvp"));
					e.setCancelled(true);
				}
			} else if(e.getEntity() instanceof Mob entity) {
				if (!Settings.REST_PVE) return;
				if (entity instanceof Boss) {
					if (!Settings.REST_PVE_BOSS) return;
					if (!damager.hasPermission("superfly.bypass.pve.boss")) {
						damager.sendMessage(tl("restriction.pve-boss"));
						e.setCancelled(true);
						return;
					}
				}
				if (!damager.hasPermission("superfly.bypass.pve") && Settings.REST_PVE) {
					damager.sendMessage(tl("restriction.pve"));
					e.setCancelled(true);
				}
			}
		}
	}
}
