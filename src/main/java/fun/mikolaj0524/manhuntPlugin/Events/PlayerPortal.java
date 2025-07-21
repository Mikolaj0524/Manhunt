package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.runnerPortalLocations;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.runnersList;

public class PlayerPortal implements Listener {

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		Player player = event.getPlayer();
		if (runnersList.contains(player)) {
			Location from = event.getFrom();
			Bukkit.getScheduler().runTaskLater(getPluginInstance(), () -> {
				if (!player.getWorld().equals(from.getWorld())) {
					runnerPortalLocations.put(player, from);
				}
			}, 1L);
		}
	}

}
