package fun.mikolaj0524.manhuntPlugin.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static fun.mikolaj0524.manhuntPlugin.Elements.Game.*;
import static fun.mikolaj0524.manhuntPlugin.Elements.Game.timeForRunners;
import static fun.mikolaj0524.manhuntPlugin.Elements.SettingsInventory.invName;
import static fun.mikolaj0524.manhuntPlugin.Elements.SettingsInventory.reloadIcons;
import static fun.mikolaj0524.manhuntPlugin.Elements.Timer.time;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		Inventory inventory = event.getClickedInventory();

		if(event.getView().getTitle().equals(invName)){
			event.setCancelled(true);
			switch(event.getSlot()){
				case 10:
					gameType = !gameType;
					reloadIcons(inventory);
					break;
				case 12:
					if(event.isLeftClick()){
						borderSize += 100;
					}
					if(event.isRightClick()){
						if(borderSize != 100){
							borderSize -= 100;
						}
					}
					reloadIcons(inventory);
					break;
				case 14:
					if(event.isLeftClick()){
						timeForRunners += 5;
					}
					if(event.isRightClick()){
						if(timeForRunners != 5){
							timeForRunners -= 5;
						}
					}
					reloadIcons(inventory);
					break;
				case 16:
					if(event.isLeftClick()){
						time += 1;
					}
					if(event.isRightClick()){
						if(time > 1){
							time -= 1;
						}
					}
					reloadIcons(inventory);
					break;
			}
		}

	}
}
