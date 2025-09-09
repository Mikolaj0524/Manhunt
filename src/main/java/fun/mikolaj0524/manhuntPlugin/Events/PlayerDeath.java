package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.checkIsEndGame;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.runnersList;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();

		if(runnersList.contains(player)){
			runnersList.remove(player);
			for(Player player1 : Bukkit.getOnlinePlayers()){
				player1.sendMessage(prefix + player.getDisplayName() + " died! \n ");
			}
			Bukkit.getScheduler().runTask(getPluginInstance(), () -> {
				if (!player.isOnline()) return;
				player.spigot().respawn();
				player.setGameMode(GameMode.SPECTATOR);
			});
			checkIsEndGame();
		}
	}

}
