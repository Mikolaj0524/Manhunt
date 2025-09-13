package fun.mikolaj0524.manhuntPlugin.Elements;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.huntersList;
import static fun.mikolaj0524.manhuntPlugin.Commands.Manhunt.runnersList;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class Communication {



	/* MESSAGES */
	public static void messageToAll(String text){
		for(Player player : Bukkit.getOnlinePlayers()){
			messageToPlayer(player, text);
		}
	}

	public static void spigotMessageToAll(TextComponent component){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.spigot().sendMessage(component);
		}
	}

	public static void messageToRunners(String text){
		for(Player player : runnersList){
			messageToPlayer(player, text);
		}
	}

	public static void messageToHunters(String text){
		for(Player player : huntersList){
			messageToPlayer(player, text);
		}
	}

	public static void messageToPlayer(Player player, String text){
		player.sendMessage(prepareMessage(text));
	}

	/* TITLES */
	public static void titleToAll(String text){
		for(Player player : Bukkit.getOnlinePlayers()){
			titleToPlayer(player, text);
		}
	}

	public static void titleToHunters(String text){
		for(Player player : huntersList){
			titleToPlayer(player, text);
		}
	}

	public static void titleToRunners(String text){
		for(Player player : runnersList){
			titleToPlayer(player, text);
		}
	}

	public static void titleToPlayer(Player player, String text){
		player.sendTitle(colorize(text), "", 10, 20, 10);
	}

	/* FORMATTING */
	public static String colorize(String text){
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String prepareMessage(String text){
		return colorize(" \n " + prefix + text + " \n ");
	}

	public static String stripMessage(String text){
		return ChatColor.stripColor(text);
	}

}
