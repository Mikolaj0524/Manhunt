package fun.mikolaj0524.manhuntPlugin.Elements;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

		if(offlinePlayer != null && offlinePlayer.isOnline()){

			if(params.equalsIgnoreCase("time")){
				String minutes = String.valueOf(timer / 60);
				String seconds = String.valueOf(timer % 60);
				if(minutes.length() == 1){
					minutes = "0" + minutes;
				}
				if(seconds.length() == 1){
					seconds = "0" + seconds;
				}
				return minutes + ":" + seconds;
			}

		}
		return null;
	}

	public static void registerHook(){
		new PlaceholderAPIHook().register();
	}

}
