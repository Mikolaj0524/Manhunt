package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.BeforeGame.spawn;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class EntityDeath implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof EnderDragon) {
			for(Player player : Bukkit.getOnlinePlayers()){
				Location loc = new Location(Bukkit.getWorld("world"), spawn.getX(), spawn.getY() + 1, spawn.getZ());
				player.teleport(loc);
				player.sendMessage(" \n " + prefix + "Runners team won! \n ");
				player.sendTitle(ChatColor.GOLD + "Runners team won!", "");
			}
		}
	}

}
