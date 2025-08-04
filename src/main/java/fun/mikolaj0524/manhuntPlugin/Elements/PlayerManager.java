package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class PlayerManager implements Listener, CommandExecutor, TabExecutor {
	public static ArrayList<Player> huntersList = new ArrayList<>();
	public static ArrayList<Player> runnersList = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;

		Player player = (Player) sender;

		if (command.getName().equalsIgnoreCase("hunters")) {
			if(player.hasPermission("manhunt.players")){
				handleCommand(player, args, "hunter");
			}
			else{
				noPermsMessage(player, "manhunt.players");
			}
			return true;
		}

		if (command.getName().equalsIgnoreCase("runners")) {
			if(player.hasPermission("manhunt.players")){
				handleCommand(player, args, "runner");
			}
			else {
				noPermsMessage(player, "manhunt.players");
			}
			return true;
		}

		if (command.getName().equalsIgnoreCase("startmanhunt")) {
			if(player.hasPermission("manhunt.start")){
				startGame();
			}
			else{
				noPermsMessage(player, "manhunt.start");
			}
			return true;
		}

		if (command.getName().equalsIgnoreCase("settings")) {
			if(player.hasPermission("manhunt.settings")){
				SettingsInventory.createInventory(player);
			}
			else{
				noPermsMessage(player, "manhunt.settings");
			}
			return true;
		}

		return false;
	}

	private void noPermsMessage(Player player, String perm){
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&bYou don’t have the <" + perm + "> permission.\n "));
	}

	private void handleCommand(Player player, String[] args, String type) {
		ArrayList<Player> list = type.equals("hunter") ? huntersList : runnersList;

		if (args.length == 0) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&cUsage: /" + type + "s <add|remove|list> [player]\n "));
			return;
		}

		if (args[0].equalsIgnoreCase("add") && args.length >= 2) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&cPlayer not found.\n "));
				return;
			}
			if (!list.contains(p)) {
				list.add(p);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&aAdded " + p.getName() + " to " + type + "s.\n "));
			}
		}
		else if (args[0].equalsIgnoreCase("remove") && args.length >= 2) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&cPlayer not found.\n "));
				return;
			}
			if (list.contains(p)) {
				list.remove(p);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&eRemoved " + p.getName() + " from " + type + "s.\n "));
			}
		}
		else if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',"\n"+ prefix + "&a" + type.substring(0, 1).toUpperCase() + type.substring(1) + "s:\n "));
			for (Player p : list) {
				player.sendMessage("- " + p.getName());
			}
		}
		else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"+ prefix + "&cInvalid subcommand.\n "));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completeList = new ArrayList<>();

		if (args.length == 1) {
			completeList.add("add");
			completeList.add("remove");
			completeList.add("list");
		}
		else if (args.length == 2) {
			boolean isHunters = command.getName().equalsIgnoreCase("hunters");
			List<String> selfList = (isHunters ? huntersList : runnersList).stream()
					.map(Player::getName)
					.toList();
			List<String> oppositeList = (isHunters ? runnersList : huntersList).stream()
					.map(Player::getName)
					.toList();
			if (args[0].equalsIgnoreCase("add")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					String name = p.getName();
					if (!selfList.contains(name) && !oppositeList.contains(name)) {
						completeList.add(name);
					}
				}
			}
			else if (args[0].equalsIgnoreCase("remove")) {
				completeList.addAll(selfList);
			}
		}

		return completeList;
	}


}
