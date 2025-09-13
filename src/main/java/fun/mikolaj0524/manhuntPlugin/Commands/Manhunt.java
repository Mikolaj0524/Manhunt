package fun.mikolaj0524.manhuntPlugin.Commands;

import fun.mikolaj0524.manhuntPlugin.Elements.SettingsInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

import static fun.mikolaj0524.manhuntPlugin.Elements.Communication.messageToPlayer;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class Manhunt implements Listener, CommandExecutor {
	public static ArrayList<Player> huntersList = new ArrayList<>();
	public static ArrayList<Player> runnersList = new ArrayList<>();
	public static ArrayList<Player> deathRunners = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player player){

			if (command.getName().equalsIgnoreCase("manhunt")) {
				if(player.hasPermission("manhunt")){
					SettingsInventory.createInventory(player);
				}
				else{
					messageToPlayer(player, "&bYou don’t have <manhunt> permission.\n ");
				}

				return true;
			}

		}
		return false;
	}

}
