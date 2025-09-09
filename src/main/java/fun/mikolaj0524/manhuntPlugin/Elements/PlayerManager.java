package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class PlayerManager implements Listener, CommandExecutor {
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
					noPermsMessage(player, "manhunt");
				}

				return true;
			}

		}
		return false;
	}

	private void noPermsMessage(Player player, String perm){
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&bYou don’t have the <" + perm + "> permission.\n "));
	}

}
