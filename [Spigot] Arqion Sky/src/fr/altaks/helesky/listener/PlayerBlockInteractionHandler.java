package fr.altaks.helesky.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.islandcore.Island;

public class PlayerBlockInteractionHandler implements Listener {
	
	private Main main;
	
	public PlayerBlockInteractionHandler(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerBreakBlockEvent(BlockBreakEvent event) {
		
		// si c'est par un joueur
			// tester si c'est un joueur qui a une ile
				// si oui tester la distance entre lui et son île
					// si il est a moins de (100 + (25 * tier)) / 2 de son anchor alors c'est bon
					// si non il ne peut pas build et il en est informé
				// si non refuser le build dans tous les cas
		// sinon rien
		
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE) return;
		if(main.hasIsland(player.getUniqueId())) {
			Island island = main.getPlayerIsland(player.getUniqueId());
			Location anchor = island.getAnchor();
			
			double distance = event.getBlock().getLocation().distance(anchor);
			if(distance > ((100 + (25 * island.getTier())) / 2)) {
				// le bloc est en dehors du rayon de l'île
				event.setCancelled(true);
				player.sendMessage(Main.PREFIX + "§c Vous ne pouvez pas casser de bloc sur un terrain ou une île qui ne vous appartient pas !");
				return;
			}
		} else {
			player.sendMessage(Main.PREFIX + "§c Vous ne pouvez pas casser de bloc sur un terrain ou une île qui ne vous appartient pas !");
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlockEvent(BlockPlaceEvent event) {
		
		// si c'est par un joueur
			// tester si c'est un joueur qui a une ile
				// si oui tester la distance entre lui et son île
					// si il est a moins de (100 + (25 * tier)) / 2 de son anchor alors c'est bon
					// si non il ne peut pas build et il en est informé
				// si non refuser le build dans tous les cas
		// sinon rien
		
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE) return;
		if(main.hasIsland(player.getUniqueId())) {
			Island island = main.getPlayerIsland(player.getUniqueId());
			Location anchor = island.getAnchor();
			
			double distance = event.getBlock().getLocation().distance(anchor);
			if(distance > ((100 + (25 * island.getTier())) / 2)) {
				// le bloc est en dehors du rayon de l'île
				event.setCancelled(true);
				player.sendMessage(Main.PREFIX + "§c Vous ne pouvez pas placer de bloc sur un terrain ou une île qui ne vous appartient pas !");
				return;
			}
		} else {
			player.sendMessage(Main.PREFIX + "§c Vous ne pouvez pas placer de bloc sur un terrain ou une île qui ne vous appartient pas !");
			event.setCancelled(true);
			return;
		}
	}

}
