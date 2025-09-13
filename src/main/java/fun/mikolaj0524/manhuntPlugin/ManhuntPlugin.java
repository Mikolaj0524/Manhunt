package fun.mikolaj0524.manhuntPlugin;

import fun.mikolaj0524.manhuntPlugin.Commands.Manhunt;
import fun.mikolaj0524.manhuntPlugin.Elements.*;
import fun.mikolaj0524.manhuntPlugin.Events.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.refreshCompass;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.timeCounter;
import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.spawn;
import static fun.mikolaj0524.manhuntPlugin.Elements.WorldManager.world;

public final class ManhuntPlugin extends JavaPlugin implements Listener {

	private static ManhuntPlugin pluginInstance;
	public final static String prefix = "&7[&b&lManhunt&7] &b» &7";
	public final static String prefixConsole = " \n \t\u001B[97m[\u001B[36mManhunt\u001B[97m] \u001B[36m» \u001B[97m ";

	@Override
	public void onEnable() {
		pluginInstance = this;
		getServer().getLogger().info(prefixConsole + "Start!\n");

		/*

		try {
			Files.walkFileTree(Paths.get("./world"), new SimpleFileVisitor<>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
			getServer().getLogger().info(prefixConsole + "Deleted {world}! ");

			Files.walkFileTree(Paths.get("./world_nether"), new SimpleFileVisitor<>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
			getServer().getLogger().info(prefixConsole + "Deleted {world_nether}! ");

			Files.walkFileTree(Paths.get("./world_the_end"), new SimpleFileVisitor<>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
			getServer().getLogger().info(prefixConsole + "Deleted {world_the_end}! ");
		}
		catch (IOException e) {
			getServer().getLogger().info(prefixConsole + "Error deleting folder: {" + e.getMessage() + "}!\n ");
		}

		try {
			Thread.sleep(9000);
		}
		catch (InterruptedException e) {
			getServer().getLogger().info(prefixConsole + "Error: {" + e.getMessage() + "}! \n");

		}

*/
		world = Bukkit.getWorld("world");
		spawn = new Location(world, 0, world.getHighestBlockYAt(0, 0), 0, 90, 0);

		getServer().getPluginManager().registerEvents(this, this);

		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		getServer().getPluginManager().registerEvents(new PlayerPortal(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerUseCompass(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new Advancement(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);

		getCommand("manhunt").setExecutor(new Manhunt());

		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
			PlaceholderAPIHook.registerHook();
		}

		timeCounter();
		refreshCompass();
		Timer.Timer();
	}
	

	@Override
	public void onDisable() {
		getServer().getLogger().info(prefixConsole + "Stop!\n");
	}

	public static ManhuntPlugin getPluginInstance(){
		return pluginInstance;
	}
}
