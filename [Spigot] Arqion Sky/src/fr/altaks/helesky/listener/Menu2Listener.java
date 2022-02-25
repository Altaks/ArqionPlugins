package fr.altaks.helesky.listener;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.utils.ItemManager;

public class Menu2Listener implements Listener {
	
	private Main main;
	
	public Menu2Listener(Main main) {
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
			
			if(event.getView().getTitle().matches("§8Warps du serveur §7\\([0-9]*\\/[0-9]*\\)")) {
				
				Material clickedType = event.getCurrentItem().getType();
				event.setCancelled(true);
				
				if(clickedType == Material.PAPER) {
					
					// obtenir le nom de l'�le
					String islandname = event.getCurrentItem().getItemMeta().getDisplayName();
						
					// recup l'ile avec la hashmap
					Island targetIsland = main.getIslandsFromId().values().stream().filter(island -> island.getName().equals(islandname)).collect(Collectors.toList()).get(0);
					if(targetIsland == null) {
						player.sendMessage(Main.PREFIX + "§cL'île a désactivé son warp pendant que vous la cherchiez");
					}
					
					// tp le joueur au warp de l'ile
					if(targetIsland.isWarpEnabled()) {
						player.teleport(targetIsland.getWarp());
					} else {
						player.sendMessage(Main.PREFIX + "§cL'île a désactivé son warp pendant que vous la cherchiez");
					}
					return;

				} else if(clickedType == Material.ARROW) {
					
					if(event.getSlot() == 45) {
						// previous
						
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							int page = Integer.parseInt(event.getView().getTitle().replace("§8Warps du serveur §7(", "").replace(")","").split("/")[0]) -1 ;
							Inventory inv = Bukkit.createInventory(null, 6*9, "§8Warps du serveur §7("+page+"/"+ (int) this.main.getIslandsFromId().values().size() / 36+")");
							
							ItemStack infobook = new ItemManager.ItemBuilder(Material.BOOK, 1, "§cInformations").setLore("§eNombre total de warps : " + this.main.getIslandsFromId().values().size()).build();
							
							ItemStack previousarrow = new ItemManager.ItemBuilder(Material.ARROW, 1, "§ePage pr�c�dente").build();
							ItemStack nextarrow = new ItemManager.ItemBuilder(Material.ARROW, 1, "§cPage suivante").build();

							int slot = 9;
							
							inv.setItem(4, infobook);
							
							if(page != 0) inv.setItem(45, previousarrow);
							inv.setItem(53, nextarrow);
							
							List<Island> islands = this.main.getIslandsFromId().values().stream().collect(Collectors.toList());
							
							for(int i = page * 36; i < (page+1)*36; i++) {
								if(i >= islands.size()) break;
								ItemStack islandpaper = new ItemManager.ItemBuilder(Material.PAPER,1, ""+islands.get(i).getName()).build();
								inv.setItem(slot, islandpaper);
								slot++;
							}
							
							for (int i = 0; i < inv.getSize(); i++) {
								if(inv.getItem(i) != null) {
									if(inv.getItem(i).getType() != Material.AIR) {
										inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
									}
								} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
							}
							
							player.openInventory(inv);
						});
					} else if(event.getSlot() == 53) {
						// next
						Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
							int page = Integer.parseInt(event.getView().getTitle().replace("§8Warps du serveur �7(", "").replace(")","").split("/")[0]) -1 ;
							Inventory inv = Bukkit.createInventory(null, 6*9, "§8Warps du serveur �7("+page+"/"+ (int) this.main.getIslandsFromId().values().size() / 36+")");
							
							ItemStack infobook = new ItemManager.ItemBuilder(Material.BOOK, 1, "§cInformations").setLore("§eNombre total de warps : " + this.main.getIslandsFromId().values().size()).build();
							
							ItemStack previousarrow = new ItemManager.ItemBuilder(Material.ARROW, 1, "§ePage pr�c�dente").build();
							ItemStack nextarrow = new ItemManager.ItemBuilder(Material.ARROW, 1, "§cPage suivante").build();

							int slot = 9;
							
							inv.setItem(4, infobook);
							
							inv.setItem(45, previousarrow);
							inv.setItem(53, nextarrow);
							
							List<Island> islands = this.main.getIslandsFromId().values().stream().collect(Collectors.toList());
							
							for(int i = page * 36; i < (page+1)*36; i++) {
								if(i >= islands.size()) break;
								ItemStack islandpaper = new ItemManager.ItemBuilder(Material.PAPER,1, ""+islands.get(i).getName()).build();
								inv.setItem(slot, islandpaper);
								slot++;
							}
							
							for (int i = 0; i < inv.getSize(); i++) {
								if(inv.getItem(i) != null) {
									if(inv.getItem(i).getType() != Material.AIR) {
										inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
									}
								} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
							}
							
							player.openInventory(inv);
						});
					} else return;
					
				}
				
			}
			
			
		}
		
	}

}
