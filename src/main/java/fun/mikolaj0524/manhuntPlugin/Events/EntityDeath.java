package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameEnd;

public class EntityDeath implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof EnderDragon) {
			gameEnd(event.getEntity().getKiller());
		}
	}

}
