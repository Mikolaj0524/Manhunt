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

import static fun.mikolaj0524.manhuntPlugin.Elements.BeforeGame.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameStatus;

public class PlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		world = Bukkit.getWorld("world");
		spawn = new Location(world, 0, world.getHighestBlockYAt(0, 0), 0, 90, 0);

		if(!gameStatus){
			if(!spawnExist){ buildSpawn(); }
			Player player = event.getPlayer();
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
	}
}
