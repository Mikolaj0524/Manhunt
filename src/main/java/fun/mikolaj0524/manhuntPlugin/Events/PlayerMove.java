package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.huntersList;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.runnersList;

public class PlayerMove implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if(!gameStatus){
			event.setCancelled(true);
		}
		else{
			if(!releaseHunters && huntersList.contains(player)){
				event.setCancelled(true);
			}
			if(!releaseRunners && runnersList.contains(player)){
				event.setCancelled(true);
			}
		}
	}

}
