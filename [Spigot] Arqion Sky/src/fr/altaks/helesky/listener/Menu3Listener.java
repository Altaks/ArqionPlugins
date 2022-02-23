package fr.altaks.helesky.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.islandcore.Island.IslandType;
import fr.altaks.helesky.utils.ItemManager;

public class Menu3Listener implements Listener {

	private Main main;
	
	public Menu3Listener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		
		Player player = (Player)event.getWhoClicked();
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		if(event.getClickedInventory().equals(event.getView().getTopInventory())) {
			if(event.getCurrentItem() == null) return;
			if(event.getCurrentItem().equals(ItemManager.PrebuiltItems.inventoryFillingGlassPane)) {
				event.setCancelled(true);
				return;
			}
			
			if(event.getView().getTitle().equals("§cHeleSky \u00BB Création d'île")) {
				event.setCancelled(true);
				
				Material material = event.getCurrentItem().getType();
				IslandType type = IslandType.DIRT;
				
				switch (material) {
					case GRASS_BLOCK:
						type = IslandType.DIRT;
						break;
					case WATER_BUCKET:
						type = IslandType.WATER;
						break;
					case RED_NETHER_BRICKS:
						type = IslandType.FIRE;
						break;
					case FEATHER:
						type = IslandType.AIR;
						break;
				default:
					return;
				}
				
				main.getIslandManager().generateIsland(type, player.getUniqueId());
				return;
			}
			
			
		}
		
		
	}
	
}
