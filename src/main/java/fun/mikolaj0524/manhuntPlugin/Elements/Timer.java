package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.messageToPlayer;
import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.titleToPlayer;
import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.spawn;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;

public class Timer {

	public static int timer = 0;
	public static int time = 8;

	public static void Timer(){

		new BukkitRunnable() {
			@Override
			public void run() {
				if(releaseHunters){
					if(gameType){
						timer++;
					}
					else{
						timer--;
						if(timer == 0){
							Location loc = new Location(Bukkit.getWorld("world"), spawn.getX(), spawn.getY() + 1, spawn.getZ());

							for(Player player : Bukkit.getOnlinePlayers()){
								player.setGameMode(GameMode.ADVENTURE);
								gameStatus = false;
								player.teleport(loc);
								messageToPlayer(player, "Runners team won!");
								titleToPlayer(player, "&^Runners team won!");
								player.setInvulnerable(true);
								player.setInvisible(true);
							}
						}
					}
				}
				else{
					if(gameType){
						timer = 0;
					}
					else{
						timer = time * 60;
					}
				}
			}
		}.runTaskTimer(getPluginInstance(), 0L, 20L);
	}
}
