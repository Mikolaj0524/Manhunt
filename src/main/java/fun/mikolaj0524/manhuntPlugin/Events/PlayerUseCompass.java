package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static fun.mikolaj0524.manhuntPlugin.Elements.Compass.*;

public class PlayerUseCompass implements Listener {

	@EventHandler
	public void onPlayerUseCompass(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction().toString().contains("LEFT_CLICK") && player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
			ItemStack itemInHand = player.getInventory().getItemInMainHand();
			if (itemInHand != null && itemInHand.getType() == Material.COMPASS) {
				ItemMeta meta = itemInHand.getItemMeta();
				if (meta != null && meta.hasDisplayName() && meta.getLore().contains(compassLore)) {
					Player currentSelectedRunner = playerSelected.get(player);
					Player nextRunner = getNextRunner(currentSelectedRunner);

					if (nextRunner != null) {
						playerSelected.put(player, nextRunner);
						player.sendMessage(ChatColor.GREEN + "You are now tracking " + nextRunner.getName());
					} else {
						player.sendMessage(ChatColor.RED + "No runners to track!");
					}
				}
			}
		}
	}

}
