package fr.altaks.helesky.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import fr.altaks.helesky.utils.ItemManager;

public class Menu6Listener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		if(event.getClickedInventory().equals(event.getView().getTopInventory())) {
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().equals(ItemManager.PrebuiltItems.inventoryFillingGlassPane)) {
				event.setCancelled(true);
				return;
			}
			if(event.getView().getTitle().equalsIgnoreCase("§8Podium des îles \u00BB")) {
				event.setCancelled(true);
			}
		}
		
		
	}
	
}
