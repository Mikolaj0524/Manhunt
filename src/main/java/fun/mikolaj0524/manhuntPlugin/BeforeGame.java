package fun.mikolaj0524.manhuntPlugin;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static fun.mikolaj0524.manhuntPlugin.Game.*;
import static fun.mikolaj0524.manhuntPlugin.PlayerManager.huntersList;
import static fun.mikolaj0524.manhuntPlugin.PlayerManager.runnersList;

public class BeforeGame implements Listener {

	private World world;
	public static Location spawn;
	private boolean spawnExist = false;

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
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 9));
		}
	}

	public void buildSpawn() {
		int centerX = 0;
		int centerZ = 0;
		int baseY = world.getHighestBlockYAt(centerX, centerZ);

		for (int x = -3; x <= 3; x++) {
			for (int y = baseY; y <= baseY + 6; y++) {
				for (int z = -3; z <= 3; z++) {
					int worldX = centerX + x;
					int worldY = y;
					int worldZ = centerZ + z;

					if (y == baseY) {
						world.getBlockAt(worldX, worldY, worldZ).setType(Material.BEDROCK);
					} else {
						world.getBlockAt(worldX, worldY, worldZ).setType(Material.AIR);
					}
				}
			}
		}
		spawnExist = true;
	}


	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(!gameStatus){
			event.setCancelled(true);
		}
	}

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

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
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
