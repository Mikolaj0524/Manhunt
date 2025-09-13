package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.checkIsEndGame;
import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.spawn;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;

public class PlayerManager {

	public static void playerGameEnd(Player player){

		if(runnersList.contains(player)){
			runnersList.remove(player);
			deathRunners.add(player);
			player.setGameMode(GameMode.SPECTATOR);
		}

		Bukkit.getScheduler().runTaskLater(getPluginInstance(), () -> {
			player.spigot().respawn();
			player.teleport(spawn);
		}, 10L);

		checkIsEndGame();
	}

	public static void preparePlayers(){

		for(Player p : runnersList){
			preparePlayer(p);
		}
		for(Player p : huntersList){
			preparePlayer(p);
		}
	}

	public static void preparePlayer(Player player){
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setExp(0.0f);
		player.setTotalExperience(0);
	}

	public static void releaseHunters(){
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
	}

	public static void releaseRunners(){
		for(Player player : runnersList){
			player.setInvisible(false);
			player.setInvulnerable(false);
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			player.setInvulnerable(false);
			player.setGameMode(GameMode.SURVIVAL);
		}
	}

	public static void removeAllAdvancements() {
		for(Player player : Bukkit.getOnlinePlayers()){
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

}
