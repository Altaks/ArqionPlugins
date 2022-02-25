package fr.altaks.helesky.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.islandcore.Island;

public class PlayerInteractEntityHandler implements Listener {
	
	private Main main;
	
	public PlayerInteractEntityHandler(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(EntityDamageByEntityEvent event) {
		
		Entity victim = event.getEntity();
		Entity damager = event.getDamager();

		if(damager instanceof Player) {
			Player player = (Player) damager;
			if(player.getGameMode() == GameMode.CREATIVE) return;
			if(!(victim instanceof Monster) && victim instanceof LivingEntity) {
				
				if(main.hasIsland(player.getUniqueId())) {
					
					Island island = main.getPlayerIsland(player.getUniqueId());

					// calcul de la distance
					Location anchor = island.getAnchor();
					Location entityLoc = victim.getLocation();
					
					double distance = entityLoc.distance(anchor);
					
					if(distance > ((100 + (25 * island.getTier())) / 2)) {
						player.sendMessage(Main.PREFIX + "§cVous ne pouvez pas attaquer des animaux qui ne sont pas les vôtres !");
						event.setCancelled(true);
						return;
					}
					
				} else {
					player.sendMessage(Main.PREFIX + "§cVous ne pouvez pas attaquer des animaux qui ne sont pas les vôtres !");
					event.setCancelled(true);
					return;
				}
					
			}
			
		}
	}

}
