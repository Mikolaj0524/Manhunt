package fun.mikolaj0524.manhuntPlugin.Events;

import fun.mikolaj0524.manhuntPlugin.Objects.RunnerWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.messageToAll;
import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.runnerWorld;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.runnersList;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameEnd;

public class PlayerPortal implements Listener {

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		Player player = event.getPlayer();
		if (runnersList.contains(player)) {
			World world = Bukkit.getWorld("world");
			World nether = Bukkit.getWorld("world_nether");
			World end = Bukkit.getWorld("world_the_end");
			if(!runnerWorld.containsKey(player)){
				runnerWorld.put(player, new RunnerWorld());
			}
			Location from = event.getFrom();
			Location to = event.getTo();
			if(from.getWorld().equals(world) && to.getWorld().equals(nether)){
				runnerWorld.get(player).setWorldNether(from);
			}
			else if(from.getWorld().equals(nether) && to.getWorld().equals(world)){
				runnerWorld.get(player).setNetherWorld(from);
			}
			else if(from.getWorld().equals(world) && to.getWorld().equals(end)){
				runnerWorld.get(player).setWorldEnd(from);
			}
		}
	}

}
