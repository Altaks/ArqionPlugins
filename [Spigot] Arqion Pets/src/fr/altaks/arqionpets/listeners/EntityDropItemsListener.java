package fr.altaks.arqionpets.listeners;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
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
				
				event.setCancelled(true);
				event.getEntity().remove();
				
				float dropRate = new Random().nextFloat() * 100;
							
				event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), PluginItems.pet_core.clone());
				return;
				
			}
			
		}
		
	}

	@EventHandler
	public void onPlayerApplyWitherConverter(EntityInteractAtEntityEvent event){

		if(!event.getPlayer().getInventory().getItemInMainHand().equals(PluginItems.wither_pet_converter)) return;
		if(!(event.getClickedEntity() instanceof Wither)) return;
		Wither wither = (Wither) event.getClickedEntity();
		if(wither.isInvulnerable()) { // peut etre besoin de remplacer par un get des ticks de vie

			// on modif le wither
			wither.setMaxHealth(500.0d);
			wither.setHealth(500.0d);
			
			Bossbar witherBossbar = wither.getBossbar();

			witherBossBar.setColor(BarColor.LIME);
			witherBossBar.setStyle(BarStyle.SEGMENTED_10);

			event.getPlayer().setItemInMainHand(null);

		}


	}

}
