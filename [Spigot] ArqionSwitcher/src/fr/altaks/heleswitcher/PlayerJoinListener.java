package fr.altaks.heleswitcher;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		Inventory inv = player.getInventory();
		
		ItemStack[] itemstacks = inv.getContents();
		
		if(!Arrays.asList(itemstacks).contains(Main.compass)) {
			player.getInventory().addItem(Main.compass);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		
		if(event.hasBlock()) return;
		if(event.getItem().equals(Main.compass) && !event.getAction().equals(Action.PHYSICAL)) {
			
			Inventory inv = Bukkit.createInventory(null, 9, "§8Serveurs");
			
			inv.setItem(1, Servers.Lobby.getSymbol());
			inv.setItem(2, Servers.Server1.getSymbol());
			
			event.getPlayer().openInventory(inv);
			
			
		}
		
	}

}
