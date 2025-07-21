package fun.mikolaj0524.manhuntPlugin;

import fun.mikolaj0524.manhuntPlugin.Elements.*;
import fun.mikolaj0524.manhuntPlugin.Events.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.refreshCompass;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.timeCounter;

public final class ManhuntPlugin extends JavaPlugin implements Listener {

	private static ManhuntPlugin pluginInstance;

	@Override
	public void onEnable() {
		pluginInstance = this;
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new PlayerManager(), this);
		getServer().getPluginManager().registerEvents(new BeforeGame(), this);
		getServer().getPluginManager().registerEvents(new Game(), this);
		getServer().getPluginManager().registerEvents(new Compass(), this);


		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		getServer().getPluginManager().registerEvents(new PlayerPortal(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerUseCompass(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

		/*
		getCommand("startmanhunt").setExecutor(new PlayerManager());
		getCommand("hunters").setExecutor(new PlayerManager());
		getCommand("hunters").setTabCompleter(new PlayerManager());
		getCommand("runners").setExecutor(new PlayerManager());
		getCommand("runners").setTabCompleter(new PlayerManager());
		getCommand("type").setExecutor(new PlayerManager());

		 */

		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
			PlaceholderAPIHook.registerHook();
		}

		timeCounter();
		refreshCompass();
		Timer.Timer();
	}

	@Override
	public void onDisable() {

	}

	public static ManhuntPlugin getPluginInstance(){
		return pluginInstance;
	}
}
