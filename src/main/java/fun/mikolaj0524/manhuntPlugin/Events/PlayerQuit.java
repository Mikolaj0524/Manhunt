package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.*;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		huntersList.remove(player);
		if(runnersList.contains(player)){
			deathRunners.add(player);
			runnersList.remove(player);
			player.setGameMode(GameMode.SPECTATOR);
			for(Player player1 : Bukkit.getOnlinePlayers()){
				player1.sendMessage(prefix + player.getDisplayName() + " died! \n ");
			}
		}
	}


}
