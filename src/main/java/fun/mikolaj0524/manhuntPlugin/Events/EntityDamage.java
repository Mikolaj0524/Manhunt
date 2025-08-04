package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.checkIsEndGame;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameStatus;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.runnersList;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(player.getHealth() - event.getDamage() < 0){
				if(runnersList.contains(player)){
					runnersList.remove(player);
					event.setCancelled(true);
					player.setGameMode(GameMode.SPECTATOR);
					for(Player player1 : Bukkit.getOnlinePlayers()){
						player1.sendMessage(" \n " + prefix + ChatColor.RED + player.getDisplayName() + " died! \n ");
					}
					checkIsEndGame();
				}
			}
		}

		if(!gameStatus){
			event.setCancelled(true);
		}
	}

}
