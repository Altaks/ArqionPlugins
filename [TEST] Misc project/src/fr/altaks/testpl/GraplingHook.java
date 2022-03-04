package fr.altaks.testpl;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GraplingHook extends JavaPlugin implements Listener {
	
	ArrayList<UUID> playersList = new ArrayList<UUID>();
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		
		Player player = event.getPlayer();
		
			if(event.getPlayer().getItemInHand().getType() == Material.FISHING_ROD) {
				
				if(!playersList.contains(player.getUniqueId())) {
				
					Location hookLoc = event.getHook().getLocation();
					Location pLoc = player.getLocation();
					
					Vector v = new Vector(pLoc.getX() - hookLoc.getX(), pLoc.getY() - hookLoc.getY(), pLoc.getZ() - hookLoc.getZ());
					player.setVelocity(player.getVelocity().subtract(v));
					playersList.add(player.getUniqueId());
					
					new BukkitRunnable() {
						private int seconds = 0;
						
						@Override
						public void run() {
							if(seconds == 3) {
								
								playersList.remove(player.getUniqueId());
								cancel();
							
							}
							seconds++;
						}
						
					}.runTaskTimer(this, 0, 20);
				} else player.sendMessage("attends salope");
				
				
			}
		
	}

}
