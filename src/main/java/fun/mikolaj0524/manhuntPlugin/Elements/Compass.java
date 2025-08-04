package fun.mikolaj0524.manhuntPlugin.Elements;

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
	public static HashMap<Player, Location> runnerPortalLocations = new HashMap<>();

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

						Location targetLocation;
						if (!hunter.getWorld().equals(selectedRunner.getWorld())) {
							targetLocation = runnerPortalLocations.get(selectedRunner);
						}
						else {
							targetLocation = selectedRunner.getLocation();
						}

						if (targetLocation == null) continue;

						if (hunter.getWorld().getEnvironment() == World.Environment.NETHER || hunter.getWorld().getEnvironment() == World.Environment.THE_END) {
							meta.setLodestone(targetLocation);
							meta.setLodestoneTracked(false);
						}
						else {
							hunter.setCompassTarget(targetLocation);
							if (meta.hasLodestone()) {
								meta.setLodestone(null);
								meta.setLodestoneTracked(false);
							}
						}

						double distance = hunter.getLocation().distance(targetLocation);
						meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Tracking: " + ChatColor.WHITE + selectedRunner.getName()
								+ ChatColor.GRAY + " | " + ChatColor.GREEN + "" + ChatColor.BOLD + (int) distance + ChatColor.GREEN + "m");

						List<String> lore = new ArrayList<>();
						lore.add(compassLore);
						meta.setLore(lore);

						itemInHand.setItemMeta(meta);
					}
				}
			}
		}.runTaskTimer(getPluginInstance(), 0L, 20L);
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
