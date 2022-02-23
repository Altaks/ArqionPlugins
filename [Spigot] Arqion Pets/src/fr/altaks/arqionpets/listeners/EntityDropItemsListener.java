package fr.altaks.arqionpets.listeners;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.utils.ItemManager;

public class EntityDropItemsListener implements Listener {
	
	static EntityType[] animal_fat = {
		EntityType.PIG, EntityType.COW, EntityType.SHEEP, EntityType.CHICKEN
	};
	
	@EventHandler
	public void onEntityGetKilled(EntityDeathEvent event) {
		
		if(Arrays.asList(animal_fat).contains(event.getEntityType())) {
			
			// calcul de la chance de drop
			
			float dropRate = new Random().nextFloat();
			
			if(dropRate*100 <= 0.5) {
				event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), PluginItems.animal_fat.clone());
			}
			
		} 
		
		if(!Arrays.asList(EntityType.ENDER_DRAGON, EntityType.WITHER).contains(event.getEntityType()) && event.getEntity() instanceof LivingEntity) {
			
			float dropRate = new Random().nextFloat();
			
			if(dropRate*100 <= 0.3) {
				event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), PluginItems.pet_dust.clone());
			}
			
		}
		
	}
	
	@EventHandler
	public void onWitherKilled(EntityDamageByEntityEvent event) {
		
		if(((LivingEntity) event.getEntity()).getHealth() - event.getDamage() <= 0 && event.getEntityType() == EntityType.WITHER) {
		
			if(event.getEntity().getName().equalsIgnoreCase("§5Wither converti")) {
				
				// TODO faire le drop
				event.setCancelled(true);
				event.getEntity().remove();
				
				float dropRate = new Random().nextFloat() * 100;
				
				ItemManager.ItemBuilder cloned_pet_core = new ItemManager.ItemBuilder(PluginItems.pet_core.clone());
				
				if(dropRate <= 2.5) {
					
					cloned_pet_core.setLore("§r§6[Legendary]");
					
				} else if(dropRate <= 10) {
					
					cloned_pet_core.setLore("§r§5[Epique]");
					
				} else if(dropRate <= 25) {
					
					cloned_pet_core.setLore("§r§9[Rare]");
					
				} else if(dropRate <= 62.5) {
					
					cloned_pet_core.setLore("§r§a[Commun]");
				}
				
				event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), cloned_pet_core.build());
				return;
				
			}
			
		}
		
	}

}
