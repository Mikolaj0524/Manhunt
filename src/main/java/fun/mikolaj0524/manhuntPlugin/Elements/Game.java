package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static fun.mikolaj0524.manhuntPlugin.Elements.BeforeGame.spawn;
import static fun.mikolaj0524.manhuntPlugin.Elements.BeforeGame.world;
import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.compassLore;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.huntersList;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.runnersList;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class Game implements Listener {
	public static boolean gameStatus = false;
	public static boolean gameType = true;
	public static boolean releaseHunters = false;
	public static boolean releaseRunners = false;
	public static int timeForRunners = 40;
	private static int time = 0;
	public static float borderSize = 300f;

	public static void timeCounter(){
		new BukkitRunnable() {
			@Override
			public void run() {
				if(gameStatus){
					time++;
					switch(time) {
						case 1:
							for (Player player : Bukkit.getOnlinePlayers()) {
								player.sendTitle(ChatColor.DARK_RED + "5", "");
								removeAllAdvancements(player);
							}
							for(Player player : runnersList){
								preparePlayer(player);
							}
							for(Player player : huntersList){
								preparePlayer(player);
							}
							break;
						case 2:
							for (Player player : Bukkit.getOnlinePlayers()) {
								player.sendTitle(ChatColor.RED + "4", "");
								if(!gameType){
									world.getWorldBorder().setSize(borderSize);
									world.getWorldBorder().setCenter(0,0);
								}
								else{
									world.getWorldBorder().setSize(60000000f);
								}
							}
							break;
						case 3:
							for (Player player : Bukkit.getOnlinePlayers()) {
								player.sendTitle(ChatColor.GOLD + "3", "");
							}
							break;
						case 4:
							for (Player player : Bukkit.getOnlinePlayers()) {
								player.sendTitle(ChatColor.YELLOW + "2", "");
							}
							break;
						case 5:
							for (Player player : Bukkit.getOnlinePlayers()) {
								player.sendTitle(ChatColor.DARK_GREEN + "1", "");
							}
							break;
						case 6:
							for (Player player : Bukkit.getOnlinePlayers()) {
								player.sendTitle(ChatColor.GREEN + "Runners released!", "");
							}
							for(Player player : runnersList){
								player.setInvisible(false);
								player.setInvulnerable(false);
								for (PotionEffect effect : player.getActivePotionEffects()) {
									player.removePotionEffect(effect.getType());
								}
								player.setInvulnerable(false);
								player.setGameMode(GameMode.SURVIVAL);
							}
							for(Player player : huntersList){
								giveCompass(player);
							}
							releaseRunners = true;
							break;
					}
					if(time < timeForRunners + 6 && time > 6){
						for(Player player : huntersList){
							player.sendTitle(ChatColor.GOLD + "" + (timeForRunners - (time - 6)), "");
						}
						for(Player player : runnersList){
							player.sendMessage("\n " + prefix + "Hunters will be released in " + (timeForRunners - (time - 6)) + " seconds!\n ");
						}
					}
					if(time == timeForRunners + 6){
						for(Player player : huntersList){
							player.sendTitle(ChatColor.GREEN + "GO!", "");
							player.setInvisible(false);
							player.setInvulnerable(false);
						}
						for(Player player : runnersList){
							player.sendMessage("\n " + prefix + "Hunters were released!\n ");
						}
						for(Player player : huntersList){
							player.setInvisible(false);
							player.setInvulnerable(false);
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							}
							player.setRespawnLocation(spawn);
							player.setGameMode(GameMode.SURVIVAL);
							player.setInvulnerable(false);
						}
						releaseHunters = true;
					}

				}
				else{
					time = 0;
				}
			}
		}.runTaskTimer(getPluginInstance(), 0L, 20L);
	}

	public static void giveCompass(Player player){
		ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Compass");
		List<String> lore = new ArrayList<>();
		lore.add(compassLore);
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		player.getInventory().setItem(8, itemStack);
	}

	public static void startGame(){
		gameStatus = true;
		World world = Bukkit.getWorld("world");

		if (world != null) {
			world.setTime(1000);
			world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
		}
	}

	public static void preparePlayer(Player player){
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setExp(0.0f);
		player.setTotalExperience(0);
	}

	public static void checkIsEndGame(){
		if(runnersList.size() <= 0){
			for(Player player : Bukkit.getOnlinePlayers()){
				Location loc = new Location(Bukkit.getWorld("world"), spawn.getX(), spawn.getY() + 1, spawn.getZ());
				player.setGameMode(GameMode.ADVENTURE);
				gameStatus = false;
				player.teleport(loc);
				player.sendMessage("\n " + prefix + "Hunters team won!\n");
				player.sendTitle(ChatColor.GOLD + "Hunters team won!", "");
			}
		}
	}

	public static void removeAllAdvancements(Player player) {
		Iterator<Advancement> advancements = Bukkit.advancementIterator();

		while (advancements.hasNext()) {
			Advancement advancement = advancements.next();
			AdvancementProgress progress = player.getAdvancementProgress(advancement);

			for (String criterion : progress.getAwardedCriteria()) {
				progress.revokeCriteria(criterion);
			}
		}
	}

}
