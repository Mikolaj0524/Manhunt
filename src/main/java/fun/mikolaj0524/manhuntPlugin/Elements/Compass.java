package fun.mikolaj0524.manhuntPlugin.Elements;

import fun.mikolaj0524.manhuntPlugin.Objects.RunnerWorld;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.gameStatus;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.huntersList;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.runnersList;

public class Compass implements Listener {

	public static String compassLore = ChatColor.GREEN + "Compass";
	public static HashMap<Player, Player> playerSelected = new HashMap<>();
	public static HashMap<Player, RunnerWorld> runnerWorld = new HashMap<>();

	public static void refreshCompass() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!gameStatus) return;

				for (Player hunter : huntersList) {
					ItemStack itemInHand = hunter.getInventory().getItemInMainHand();

					if (itemInHand != null && itemInHand.getType() == Material.COMPASS) {
						if (!(itemInHand.getItemMeta() instanceof CompassMeta meta)) continue;
						if (!meta.hasLore() || !meta.getLore().contains(compassLore)) continue;
						Player selectedRunner = playerSelected.get(hunter);
						if (selectedRunner == null || !runnersList.contains(selectedRunner)) {
							selectedRunner = getClosestRunner(hunter);
							playerSelected.put(hunter, selectedRunner);
						}
						if (selectedRunner == null) continue;

						World world = Bukkit.getWorld("world");
						World nether = Bukkit.getWorld("world_nether");
						World end = Bukkit.getWorld("world_the_end");

						Location hunterLoc = hunter.getLocation();
						Location runnerLoc = playerSelected.get(hunter).getLocation();

						Location finalLoc = null;


						if(hunterLoc.getWorld() == runnerLoc.getWorld()){
							finalLoc = runnerLoc;
						}
						else{
							RunnerWorld rw = runnerWorld.get(selectedRunner);

							if(hunterLoc.getWorld().equals(world) && runnerLoc.getWorld().equals(nether)){
								finalLoc = rw.getWorldNether();
							}
							else if(hunterLoc.getWorld().equals(nether) && runnerLoc.getWorld().equals(world)){
								finalLoc = rw.getNetherWorld();
							}
							else if(hunterLoc.getWorld().equals(nether) && runnerLoc.getWorld().equals(end)){
								finalLoc = rw.getNetherWorld();
							}
							else if(hunterLoc.getWorld().equals(world) && runnerLoc.getWorld().equals(end)){
								finalLoc = rw.getWorldEnd();
							}

						}

						if(finalLoc == null) continue;
						double distance = hunterLoc.distance(finalLoc);
						meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lTracking: &f"+ selectedRunner.getName() + "&7 | &a&l" + (int) distance + "&am"));
						List<String> lore = new ArrayList<>();
						lore.add(compassLore);
						meta.setLore(lore);
						World.Environment env = hunter.getWorld().getEnvironment();
						if (env == World.Environment.NORMAL) {
							if (meta.hasLodestone()) {
								meta.setLodestone(null);
								meta.setLodestoneTracked(false);
							}
							hunter.setCompassTarget(finalLoc);
						} else {
							meta.setLodestone(finalLoc);
							meta.setLodestoneTracked(false);
						}
						itemInHand.setItemMeta(meta);
					}
				}
			}
		}.runTaskTimer(getPluginInstance(), 0L, 10L);
	}

	private static Player getClosestRunner(Player hunter) {
		Player closestRunner = null;
		double closestDistance = Double.MAX_VALUE;

		for (Player runner : runnersList) {
			if (runner.getGameMode() != GameMode.SPECTATOR) {
				double distance = hunter.getLocation().distance(runner.getLocation());
				if (distance < closestDistance) {
					closestDistance = distance;
					closestRunner = runner;
				}
			}
		}

		return closestRunner;
	}

	public static Player getNextRunner(Player currentSelectedRunner) {
		boolean foundCurrentRunner = false;

		for (Player runner : runnersList) {
			if (runner.getGameMode() != GameMode.SPECTATOR) {
				if (foundCurrentRunner) {
					return runner;
				}
				if (runner == currentSelectedRunner) {
					foundCurrentRunner = true;
				}
			}
		}

		for (Player runner : runnersList) {
			if (runner.getGameMode() != GameMode.SPECTATOR) {
				return runner;
			}
		}

		return null;
	}
}
