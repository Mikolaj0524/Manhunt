package fun.mikolaj0524.manhuntPlugin.Elements;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.huntersList;
import static fun.mikolaj0524.manhuntPlugin.Elements.PlayerManager.runnersList;
import static fun.mikolaj0524.manhuntPlugin.Elements.Timer.timer;

public class PlaceholderAPIHook extends PlaceholderExpansion {

	@Override
	public @NotNull String getIdentifier() {
		return "Manhunt";
	}

	@Override
	public @NotNull String getAuthor() {
		return "Mikolaj0524";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {

		if (offlinePlayer != null && offlinePlayer.isOnline()) {

			if (params.equalsIgnoreCase("time")) {
				String minutes = String.valueOf(timer / 60);
				String seconds = String.valueOf(timer % 60);
				if (minutes.length() == 1) {
					minutes = "0" + minutes;
				}
				if (seconds.length() == 1) {
					seconds = "0" + seconds;
				}
				return minutes + ":" + seconds;
			} else if (params.equalsIgnoreCase("type")) {
				return gameType ? "ɴᴏʀᴍᴀʟ" : "ʜᴜɴᴛ";
			} else if (params.equalsIgnoreCase("wait")) {
				return "" + timeForRunners;
			} else if (params.equalsIgnoreCase("worldborder")) {
				return "" + borderSize;
			} else if (params.startsWith("runnersList")) {
				int id = Integer.parseInt(params.substring(params.length() - 1));
				if (id < runnersList.size()) {
					return ChatColor.translateAlternateColorCodes('&', "&c  " + runnersList.get(id).getName());
				}
				return "";
			} else if (params.startsWith("huntersList")) {
				int id = Integer.parseInt(params.substring(params.length() - 1));

				if (id < huntersList.size()) {
					return ChatColor.translateAlternateColorCodes('&', "&a  " + huntersList.get(id).getName());
				}
				return "";
			}

		}
		return null;
	}

	public static void registerHook() {
		new PlaceholderAPIHook().register();
	}

}
