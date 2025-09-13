package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.messageToAll;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		huntersList.remove(player);
		if(runnersList.contains(player)){
			deathRunners.add(player);
			runnersList.remove(player);
			player.setGameMode(GameMode.SPECTATOR);
			messageToAll(player.getDisplayName() + " died!");
		}
	}


}
