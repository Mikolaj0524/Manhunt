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

public class PlayerManager implements Listener, CommandExecutor, TabExecutor {
	public static ArrayList<Player> huntersList = new ArrayList<>();
	public static ArrayList<Player> runnersList = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;

		Player player = (Player) sender;

		if (command.getName().equalsIgnoreCase("hunters")) {
			handleCommand(player, args, "hunter");
			return true;
		}

		if (command.getName().equalsIgnoreCase("runners")) {
			handleCommand(player, args, "runner");
			return true;
		}

		if (command.getName().equalsIgnoreCase("startmanhunt")) {
			startGame();
			return true;
		}

		if (command.getName().equalsIgnoreCase("type")) {
			changeGameType(player);
			return true;
		}

		return false;
	}

	private void changeGameType(Player p){
		if(gameType){
			gameType = false;
			borderSize = 300.0f;
		}
		else{
			gameType = true;
			borderSize = 60000000f;
		}

		p.sendMessage(ChatColor.AQUA + "Current manhunt type: " + (gameType ? "NORMAL" : "HUNT"));
	}

	private void handleCommand(Player player, String[] args, String type) {
		ArrayList<Player> list = type.equals("hunter") ? huntersList : runnersList;

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Usage: /" + type + "s <add|remove|list> [player]");
			return;
		}

		if (args[0].equalsIgnoreCase("add") && args.length >= 2) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				player.sendMessage(ChatColor.RED + "Player not found.");
				return;
			}
			if (!list.contains(p)) {
				list.add(p);
				player.sendMessage(ChatColor.GREEN + "Added " + p.getName() + " to " + type + "s.");
			}
		} else if (args[0].equalsIgnoreCase("remove") && args.length >= 2) {
			Player p = Bukkit.getPlayer(args[1]);
			if (p == null) {
				player.sendMessage(ChatColor.RED + "Player not found.");
				return;
			}
			if (list.contains(p)) {
				list.remove(p);
				player.sendMessage(ChatColor.YELLOW + "Removed " + p.getName() + " from " + type + "s.");
			}
		} else if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage(ChatColor.GREEN + type.substring(0, 1).toUpperCase() + type.substring(1) + "s:");
			for (Player p : list) {
				player.sendMessage("- " + p.getName());
			}
		} else {
			player.sendMessage(ChatColor.RED + "Invalid subcommand.");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completeList = new ArrayList<>();

		if (args.length == 1) {
			completeList.add("add");
			completeList.add("remove");
			completeList.add("list");
		} else if (args.length == 2) {
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
			} else if (args[0].equalsIgnoreCase("remove")) {
				completeList.addAll(selfList);
			}
		}

		return completeList;
	}


}
