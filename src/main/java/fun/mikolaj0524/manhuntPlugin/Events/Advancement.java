package fun.mikolaj0524.manhuntPlugin.Events;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import static fun.mikolaj0524.manhuntPlugin.ManhuntPlugin.prefix;

public class Advancement implements Listener {

	@EventHandler
	public void onAdvancement(PlayerAdvancementDoneEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() != GameMode.SURVIVAL) return;
		AdvancementDisplay advancementDisplay = event.getAdvancement().getDisplay();
		if (advancementDisplay == null) return;
		String playerName = player.getDisplayName();
		String title = ChatColor.stripColor(advancementDisplay.getTitle());
		String description = ChatColor.stripColor(advancementDisplay.getDescription());
		TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&',"\n" + prefix + playerName + " &b- "));
		TextComponent advancementTitle = new TextComponent(title + "!");
		advancementTitle.setColor(net.md_5.bungee.api.ChatColor.GRAY);
		advancementTitle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(description).create()));
		message.addExtra(advancementTitle);
		message.addExtra("\n");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(message);
		}
	}
}