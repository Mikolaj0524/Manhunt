package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static fun.mikolaj0524.manhuntPlugin.Elements.BeforeGame.spawn;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;

public class Timer {

	public static int timer = 0;

	public static void Timer(){
		int time = 7;

		new BukkitRunnable() {
			@Override
			public void run() {
				if(releaseRunners){
					if(gameType){
						timer++;
					}
					else{
						timer--;
						if(timer == 0){
							for(Player player : Bukkit.getOnlinePlayers()){
								Location loc = new Location(Bukkit.getWorld("world"), spawn.getX(), spawn.getY() + 1, spawn.getZ());
								player.setGameMode(GameMode.ADVENTURE);
								gameStatus = false;
								player.teleport(loc);
								player.sendMessage(ChatColor.GOLD + "Runners team won!");
								player.sendTitle(ChatColor.GOLD + "Runners team won!", "");
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
