package fr.nowayy.arqionbox.listeners;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxItem;
import fr.nowayy.arqionbox.core.BoxType;

public class AdminBoxMenuListener implements Listener {
	
	private Main main;
	
	public AdminBoxMenuListener(Main main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onAdminMenuInteract(InventoryClickEvent event){
		
		Inventory clickedInv = event.getClickedInventory();
		
		// on verif si c'est le bon inv
		
		if(event.getView().getTitle().equals("§cArqionBox - §cAdminBox")) {
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
						
						Inventory dropRateInv = Bukkit.createInventory(null, 9 * 6, "Items obtensibles : " + box.getName());
						
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							
							while(dropRateInv.firstEmpty() != -1 && !itemToShow.isEmpty()) {
								
								dropRateInv.addItem(itemToShow.pollFirst());
								
								
							}
							
						});
						
						player.openInventory(dropRateInv);
						
					}
				}
		
			}
		} else if(event.getView().getTitle().startsWith("Taux d'obtention \u00BB ")){
			
			Player player = (Player) event.getWhoClicked();
			
			// verif si c'est l'inv du haut
			if(!event.getView().getTopInventory().equals(clickedInv)) {
				return;
			}
			
			event.setCancelled(true);
			// recup l'item cliqué
			ItemStack clickedItem = event.getCurrentItem();
			if(clickedItem == null) return;
			// recup la box
			
			BoxType box = BoxType.IRON;
			for(BoxType boxtype : BoxType.values()){
				if(event.getView().getTitle().replace("Taux d'obtention \u00BB ","").equals(boxtype.getName())){
					box = boxtype;
					break;
				}
			}
			
			// recup le BoxItem correspondant
			
			BoxItem boxItemToFind = null;
			for(BoxItem boxitem : main.getBoxDrops().get(box)) {
				if(boxitem.getItem().getType().equals(clickedItem.getType())) {
					if(clickedItem.hasItemMeta()){
						if(boxitem.getItem().getItemMeta().getDisplayName().equals(clickedItem.getItemMeta().getDisplayName())){
							boxItemToFind = boxitem;
							break;
						}
					} else if(clickedItem.getAmount() == boxitem.getItem().getAmount()){
						boxItemToFind = boxitem;
						break;
					}
				}
			}
			if(boxItemToFind == null) {
				player.sendMessage(Main.PREFIX + "L'item modifié est introuvable dans la box !");
				return;
			}
			
			int itemSlotInList = main.getBoxDrops().get(box).indexOf(boxItemToFind);
			
			
			
			// recup le type de clic sur l'item
			switch(event.getClick()){
			// modif le droprate
				case SHIFT_LEFT:
					boxItemToFind.setDropRate((float)(boxItemToFind.getDropRate() - 1f));
					break;
				case SHIFT_RIGHT:
					boxItemToFind.setDropRate((float)(boxItemToFind.getDropRate() + 1f));
					break;
				case LEFT:
					boxItemToFind.setDropRate((float)(boxItemToFind.getDropRate() - 10f));
					break;	
				case RIGHT:
					boxItemToFind.setDropRate((float)(boxItemToFind.getDropRate() + 10f));
					break;
				case MIDDLE:
					boxItemToFind.setDropRate(1000f);
					break;
				default:
					return;
				
			}
			
			if(boxItemToFind.getDropRate() > 1000 || boxItemToFind.getDropRate() < 0) {
				player.sendMessage(Main.PREFIX + "§cVous ne pouvez pas dépasser les limites de probabilité !");
				return;
			}
			
			main.getBoxDrops().get(box).remove(itemSlotInList);
			main.getBoxDrops().get(box).add(boxItemToFind);
			
			// actualiser le lore
			
			ItemStack mirrorItem = boxItemToFind.getItem().clone();
					
			ItemMeta meta = mirrorItem.getItemMeta();
			meta.setLore(Arrays.asList("§eTaux d'obtention : §6" + (boxItemToFind.getDropRate()/10) + "%"));
			mirrorItem.setItemMeta(meta);
			
			clickedInv.setItem(event.getSlot(), mirrorItem);
			
			
			// save l'item 
			
			main.saveBoxDrops(box, main.getBoxDrops().get(box));
			// reload la box
			main.loadBoxDrops(box);
		}
		
	}
	
	@EventHandler
	public void onInvCloseEvent(InventoryCloseEvent event) {
		
		Inventory closedInv = event.getInventory();
		Player player = (Player) event.getPlayer();
		
		if(event.getView().getTitle().startsWith("Items obtensibles :")) {
			
			BoxType boxtype = BoxType.IRON;
			for(BoxType type : BoxType.values()) {
				if(event.getView().getTitle().contains(type.getName())) {
					boxtype = type;
					break;
				}
			}
			
			List<BoxItem> currentItems = main.getBoxDrops().get(boxtype);
			
			// ajout des items : répertorié
			for(ItemStack invitem : closedInv.getContents()) {
				if(invitem == null) continue;
				boolean found = false;
				for(BoxItem boxitem : currentItems) {
					if(boxitem.getItem().getType().equals(invitem.getType())) {
						if(invitem.hasItemMeta()){
							if(boxitem.getItem().getItemMeta().getDisplayName().equals(invitem.getItemMeta().getDisplayName())){
								found = true;
								break;
							}
						} else if(invitem.getAmount() == boxitem.getItem().getAmount()){
							found = true;
							break;
						}
					}
				}
				if(found) continue;
				
				// sinon item inconnu
				currentItems.add(new BoxItem(invitem, 0.0f));
			}
			
			List<BoxItem> boxItemsToRemove = new ArrayList<BoxItem>();
			
			for(BoxItem boxitem : currentItems) {
				boolean found = false;
				for(ItemStack invitem : closedInv.getContents()) {
					if(invitem == null) continue;
					if(boxitem.getItem().getType().equals(invitem.getType())) {
						if(invitem.hasItemMeta()){
							if(boxitem.getItem().getItemMeta().getDisplayName().equals(invitem.getItemMeta().getDisplayName())){
								found = true;
								break;
							}
						} else if(invitem.getAmount() == boxitem.getItem().getAmount()){
							found = true;
							break;
						}
					}
				}
				if(found) continue;
				
				// sinon item inconnu
				boxItemsToRemove.add(boxitem);
			}
			
			boxItemsToRemove.forEach(item -> currentItems.remove(item));
			
			// save les objets ducon
			main.saveBoxDrops(boxtype, currentItems);
			main.loadBoxDrops(boxtype);
			
			Inventory invToOpen = Bukkit.createInventory(null, 9 * 6, "Taux d'obtention \u00BB " + boxtype.getName());
			
			ArrayDeque<ItemStack> itemsToShow = new ArrayDeque<ItemStack>();
			
			Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
				
				for(BoxItem boxitem : currentItems) {
					
					ItemStack mirrorItem = boxitem.getItem().clone();
					
					ItemMeta meta = mirrorItem.getItemMeta();
					meta.setLore(Arrays.asList("§eTaux d'obtention : §6" + (boxitem.getDropRate()/10) + "%"));
									
					mirrorItem.setItemMeta(meta);
					itemsToShow.addLast(mirrorItem);
					
				}
				
				while(invToOpen.firstEmpty() != -1 && !itemsToShow.isEmpty()) {
					invToOpen.addItem(itemsToShow.pollFirst());
				}
				
				
			});
			
			Bukkit.getScheduler().runTaskLater(main, () -> {
				player.closeInventory();
				player.openInventory(invToOpen);
			}, 1);
			
		}
		
		
	}
	
	
		


}
