package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.giveCompassToRunners;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.spawn;
import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.world;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.getPluginInstance;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.runnersList;

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
							titleToAll("&45");
							removeAllAdvancements();
							preparePlayers();
							setupBorder();
							break;
						case 2: titleToAll("&c4"); break;
						case 3: titleToAll("&63"); break;
						case 4: titleToAll("&e2"); break;
						case 5: titleToAll("&21"); break;
						case 6:
							titleToAll("&aRunners released!");
							releaseRunners();
							giveCompassToRunners();
							releaseRunners = true;
							break;
					}
					if(time < timeForRunners + 6 && time > 6){
						titleToHunters("&6" + (timeForRunners - (time - 6)));
						messageToRunners("Hunters will be released in " + (timeForRunners - (time - 6)) + " seconds!");
					}
					if(time == timeForRunners + 6){
						titleToHunters("&aGO!");
						messageToRunners("Hunters released!");
						releaseHunters();
						releaseHunters = true;
					}
				}
				else{
					time = 0;
				}
			}
		}.runTaskTimer(getPluginInstance(), 0L, 20L);
	}

	public static void setupBorder(){
		if(!gameType){
			world.getWorldBorder().setSize(borderSize);
			world.getWorldBorder().setCenter(0,0);
		}
		else{
			world.getWorldBorder().setSize(60000000f);
		}
	}

	public static void startGame() {
		gameStatus = true;
		World world = Bukkit.getWorld("world");
		world.setTime(1000);
		world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
	}

	public static void checkIsEndGame(){
		if(runnersList.size() <= 0){
			Location loc = new Location(Bukkit.getWorld("world"), spawn.getX(), spawn.getY() + 1, spawn.getZ());
			for(Player player : Bukkit.getOnlinePlayers()){
				player.setGameMode(GameMode.ADVENTURE);
				gameStatus = false;
				player.teleport(loc);
				messageToPlayer(player, "Hunters team won!");
				titleToPlayer(player, "&6Hunters team won!");
			}
		}
	}

	public static void gameEnd(Player player){
		messageToAll(player.getDisplayName());

		if(runnersList.contains(player)){
			for(Player p : Bukkit.getOnlinePlayers()){
				p.setGameMode(GameMode.ADVENTURE);
				gameStatus = false;
				p.teleport(spawn.clone().add(0, 1, 0));
				messageToPlayer(p, "Runners team won!");
				titleToPlayer(p, "&6Runners team won!");
			}
		}
	}


}
