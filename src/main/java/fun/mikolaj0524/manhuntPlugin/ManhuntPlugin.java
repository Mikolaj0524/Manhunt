package fun.mikolaj0524.manhuntPlugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static fun.mikolaj0524.manhuntPlugin.Compass.refreshCompass;
import static fun.mikolaj0524.manhuntPlugin.Game.timeCounter;

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
		getCommand("hunters").setExecutor(new PlayerManager());
		getCommand("startmanhunt").setExecutor(new PlayerManager());
		getCommand("hunters").setTabCompleter(new PlayerManager());
		getCommand("runners").setExecutor(new PlayerManager());
		getCommand("runners").setTabCompleter(new PlayerManager());
		timeCounter();
		refreshCompass();
	}

	@Override
	public void onDisable() {

	}

	public static ManhuntPlugin getPluginInstance(){
		return pluginInstance;
	}
}
