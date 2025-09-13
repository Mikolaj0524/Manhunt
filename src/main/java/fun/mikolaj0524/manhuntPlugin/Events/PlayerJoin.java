package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameStatus;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.deathRunners;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.huntersList;

public class PlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){

		Player player = event.getPlayer();
		if(!gameStatus){

			if(!spawnExist)
				buildSpawn();

			player.setHealth(20.0);
			Location loc = new Location(world, spawn.getX(), spawn.getY() + 1, spawn.getZ());
			player.teleport(loc);
			player.getInventory().clear();
			player.setFoodLevel(20);
			player.setGameMode(GameMode.ADVENTURE);
			player.setInvulnerable(true);
			player.setInvisible(true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 9));
		}
		if(deathRunners.contains(player)){
			player.setGameMode(GameMode.SPECTATOR);
		}
		else{
			huntersList.add(player);
		}
	}
}
