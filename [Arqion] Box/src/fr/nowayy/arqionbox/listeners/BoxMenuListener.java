package fr.nowayy.arqionbox.listeners;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxItem;
import fr.nowayy.arqionbox.core.BoxType;

public class BoxMenuListener implements Listener {
	
	private Main main;
	
	public BoxMenuListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onMenuInteract(InventoryClickEvent event) {
		
		// on recup l'inv
		
		Inventory clickedInv = event.getClickedInventory();
		
		// on verif si c'est le bon inv
		
		if(event.getView().getTitle().equals("§cArqionBox - §cBox")) {
			if(event.getView().getTopInventory().equals(clickedInv)) {
			
				// cancel l'event
				event.setCancelled(true);
				// on verif si il clique sur une des clés
				ItemStack clickedItem = event.getCurrentItem();
				for(BoxType box : BoxType.values()) {
					if(box.getSymbolItem().equals(clickedItem)){
						Player player = (Player)event.getWhoClicked();	
						
						List<BoxItem> boxitems = main.getBoxDrops().get(box);
						
						ArrayDeque<ItemStack> itemToShow = new ArrayDeque<ItemStack>();
						
						for(BoxItem item : boxitems) {
							
							ItemStack mirrorItem = item.getItem().clone();
							
							ItemMeta meta = mirrorItem.getItemMeta();
							meta.setLore(Arrays.asList("§eTaux d'obtention : §6" + (item.getDropRate()/10) + "%"));
											
							mirrorItem.setItemMeta(meta);
							itemToShow.addLast(mirrorItem);
						}
						
						Inventory dropRateInv = Bukkit.createInventory(null, 9 * 6, "Taux d'obtention : " + box.getName());
						
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							
							while(dropRateInv.firstEmpty() != -1 && !itemToShow.isEmpty()) {
								
								dropRateInv.addItem(itemToShow.pollFirst());
								
								
							}
							
						});
						
						player.openInventory(dropRateInv);
						
					}
				}
		
			}
		} else if(event.getView().getTitle().startsWith("Taux d'obtention : ")){
			
			event.setCancelled(true);
			
		}
		
		// on verif si le joueur a cliqué sur l'inv du haut
		
		
	

	}
	
	
	
	
}
