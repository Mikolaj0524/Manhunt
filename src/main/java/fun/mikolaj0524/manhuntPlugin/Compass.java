package fun.mikolaj0524.manhuntPlugin;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static fun.mikolaj0524.manhuntPlugin.Game.gameStatus;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;
import static fun.mikolaj0524.manhuntPlugin.PlayerManager.huntersList;
import static fun.mikolaj0524.manhuntPlugin.PlayerManager.runnersList;

public class Compass implements Listener {
	public static String compassLore = ChatColor.GREEN + "Compass";
	private static HashMap<Player, Player> playerSelected = new HashMap<>();
	private static HashMap<Player, Location> runnerPortalLocations = new HashMap<>();

	public static void refreshCompass() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (gameStatus) {
					for (Player hunter : huntersList) {
						ItemStack itemInHand = hunter.getInventory().getItemInMainHand();
						if (itemInHand != null && itemInHand.getType() == Material.COMPASS) {
							ItemMeta meta = itemInHand.getItemMeta();
							if (meta != null && meta.hasDisplayName() && meta.getLore().contains(compassLore)) {
								Player selectedRunner = playerSelected.get(hunter);
								if (selectedRunner == null || !runnersList.contains(selectedRunner)) {
									selectedRunner = getClosestRunner(hunter);
									playerSelected.put(hunter, selectedRunner);
								}

								if (selectedRunner != null) {
									Location targetLocation;

									if (!hunter.getWorld().equals(selectedRunner.getWorld())) {
										targetLocation = runnerPortalLocations.get(selectedRunner);
									} else {
										targetLocation = selectedRunner.getLocation();
									}

									if (targetLocation != null) {
										hunter.setCompassTarget(targetLocation);

										double distance = hunter.getLocation().distance(targetLocation);
										meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Tracking: " + ChatColor.WHITE + selectedRunner.getName() + ChatColor.GRAY + " | " + ChatColor.GREEN + "" + ChatColor.BOLD + (int) distance + ChatColor.GREEN + "m");
										List<String> lore = new ArrayList<>();
										lore.add(compassLore);
										meta.setLore(lore);
										itemInHand.setItemMeta(meta);
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(getPluginInstance(), 0L, 20L);
	}

	private static ItemStack getCompassItem() {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta meta = compass.getItemMeta();
		if (meta != null) {
			List<String> lore = new ArrayList<>();
			lore.add(compassLore);
			meta.setLore(lore);
			compass.setItemMeta(meta);
		}
		return compass;
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

	@EventHandler
	public void onPlayerUseCompass(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().toString().contains("LEFT_CLICK") && player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
			ItemStack itemInHand = player.getInventory().getItemInMainHand();
			if (itemInHand != null && itemInHand.getType() == Material.COMPASS) {
				ItemMeta meta = itemInHand.getItemMeta();
				if (meta != null && meta.hasDisplayName() && meta.getLore().contains(compassLore)) {
					Player currentSelectedRunner = playerSelected.get(player);
					Player nextRunner = getNextRunner(currentSelectedRunner);

					if (nextRunner != null) {
						playerSelected.put(player, nextRunner);
						player.sendMessage(ChatColor.GREEN + "You are now tracking " + nextRunner.getName());
					} else {
						player.sendMessage(ChatColor.RED + "No runners to track!");
					}
				}
			}
		}
	}

	private Player getNextRunner(Player currentSelectedRunner) {
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

	@EventHandler
	public void onPlayerPortal(PlayerPortalEvent event) {
		Player player = event.getPlayer();
		if (runnersList.contains(player)) {
			Location from = event.getFrom();
			Bukkit.getScheduler().runTaskLater(getPluginInstance(), () -> {
				if (!player.getWorld().equals(from.getWorld())) {
					runnerPortalLocations.put(player, from);
				}
			}, 1L); // delay to ensure teleport is completed
		}
	}
}
