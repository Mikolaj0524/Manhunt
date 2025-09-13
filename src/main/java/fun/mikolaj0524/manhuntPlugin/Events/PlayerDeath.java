package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.prepareMessage;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.playerGameEnd;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player1 = event.getEntity();

		if(event.getDamageSource().getCausingEntity() instanceof Player player2){
			event.setDeathMessage(prepareMessage(player2.getDisplayName() + " killed " + player1.getDisplayName() + "!"));
		}
		else{
			event.setDeathMessage(prepareMessage(player1.getDisplayName() + " died!"));
		}
		playerGameEnd(player1);
	}

}
