package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameStatus;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.playerGameEnd;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){

		if(!gameStatus){
			event.setCancelled(true);
		}
	}

}
