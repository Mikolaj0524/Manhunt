package fun.mikolaj0524.manhuntPlugin.Elements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Timer.*;

public class SettingsInventory implements Listener {

	public static final String invName = ChatColor.translateAlternateColorCodes('&', "        &4&lManhunt settings");

	public static void createInventory(Player player){
		Inventory inventory = Bukkit.createInventory(player, 27, invName);
		for(int i = 0; i < 27; i++){

			inventory.setItem(i, createItemStack(Material.GRAY_STAINED_GLASS_PANE, " "));
		}
		reloadIcons(inventory);
		player.openInventory(inventory);
	}

	public static void reloadIcons(Inventory inventory){
		inventory.setItem(10,
				createItemStack(Material.REDSTONE_LAMP,
						"&b&lGame type:",
						List.of(
								"",
								ChatColor.translateAlternateColorCodes('&', "&eCurrent: " + (gameType ? "normal" : "hunt")),
								"",
								ChatColor.translateAlternateColorCodes('&', "&a ⬆ (LMB)"),
								ChatColor.translateAlternateColorCodes('&', "&c ⬇ (RMB)"),
								""
						)));


		inventory.setItem(12,
				createItemStack(Material.EMERALD_BLOCK,
						"&b&lWorld border:",
						List.of(
								"",
								ChatColor.translateAlternateColorCodes('&', "&eCurrent: " + borderSize),
								"",
								ChatColor.translateAlternateColorCodes('&', "&a +100 (LMB)"),
								ChatColor.translateAlternateColorCodes('&', "&c -100 (RMB)"),
								""
						)));

		inventory.setItem(14,
				createItemStack(Material.COMPASS,
						"&b&lHunters release time:",
						List.of(
								"",
								ChatColor.translateAlternateColorCodes('&', "&eCurrent: " + timeForRunners),
								"",
								ChatColor.translateAlternateColorCodes('&', "&a +5 (LMB)"),
								ChatColor.translateAlternateColorCodes('&', "&c -5 (RMB)"),
								""
						)));

		inventory.setItem(16,
				createItemStack(Material.CLOCK,
						"&b&lMax game time:",
						List.of(
								"",
								ChatColor.translateAlternateColorCodes('&', "&eCurrent: " + time),
								"",
								ChatColor.translateAlternateColorCodes('&', "&a +1 (LMB)"),
								ChatColor.translateAlternateColorCodes('&', "&c -1 (RMB)"),
								""
						)));
	}

	private static ItemStack createItemStack(Material mat, String name){
		ItemStack itemStack = new ItemStack(mat);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&', name));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	private static ItemStack createItemStack(Material mat, String name, List<String> lore){
		ItemStack itemStack = new ItemStack(mat);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&', name));
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

}
