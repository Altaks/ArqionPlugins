package fr.altaks.arqionpets.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class ItemStackBurningListener implements Listener {
	
	@EventHandler
	public void onItemCombustEvent(EntityCombustEvent event) {
		
		if(event.getEntity() instanceof Item) {
			
			if(event.getEntity().getLocation().getBlock().getType().equals(Material.FIRE)) {
				
				
				Bukkit.getLogger().info("Tu viens de prendre un event EntityCombustEvent");
				
				
			}
			
			
		}
		
	}

}
