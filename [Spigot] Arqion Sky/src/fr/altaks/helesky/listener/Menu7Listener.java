package fr.altaks.helesky.listener;

import java.util.ArrayDeque;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
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

public class Menu7Listener implements Listener {
	
	private Main main;
	
	public Menu7Listener(Main main) {
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
			
			if(event.getView().getTitle().startsWith("§cHeleSky - Gestion ") && !event.getView().getTitle().equals("§cHeleSky - Gestion Joueurs")) {
				event.setCancelled(true);
				
				String targetpseudo = event.getView().getTitle().replace("§cHeleSky - Gestion ","");
				
				Material clickedType = event.getCurrentItem().getType(); 
				switch (clickedType) {
					case WITHER_ROSE:
						player.performCommand("is kick " + targetpseudo);
						break;
					case BARRIER:
						if(event.getCurrentItem().containsEnchantment(Enchantment.DURABILITY)) {
							player.performCommand("is unban " + targetpseudo);
							event.getCurrentItem().removeEnchantment(Enchantment.DURABILITY);
						} else {
							player.performCommand("is ban " + targetpseudo);
							event.getCurrentItem().addUnsafeEnchantment(Enchantment.DURABILITY,1);
						}
						break;
					case LEAD:
						if(event.getCurrentItem().containsEnchantment(Enchantment.DURABILITY)) {
							player.performCommand("is coop remove " + targetpseudo);
							event.getCurrentItem().removeEnchantment(Enchantment.DURABILITY);
						} else {
							player.performCommand("is coop add " + targetpseudo);
							event.getCurrentItem().addUnsafeEnchantment(Enchantment.DURABILITY,1);
						}
						break;
					case TOTEM_OF_UNDYING:
						if(event.getCurrentItem().containsEnchantment(Enchantment.DURABILITY)) {
							player.performCommand("is kick " + targetpseudo);
							event.getCurrentItem().removeEnchantment(Enchantment.DURABILITY);
						} else {
							player.performCommand("is invite " + targetpseudo);
						}
						break;
					case ARROW:
						if(main.hasIsland(player.getUniqueId())) {
							Island island = main.getPlayerIsland(player.getUniqueId());								
							Inventory inv = Bukkit.createInventory(null, 6 * 9, "§8HeleSky - Gestion Joueurs");
							
							for(int slot : new int[] {0,1,2,3,4,5,6,7,8,9,45,46,47,48,49,50,51,52,53,18,27,36,17,26,35,44}) {
								inv.setItem(slot, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
							}
							
							ItemStack returnArrow = new ItemManager.ItemBuilder(Material.ARROW, 1,   "§cRetour").build();
							inv.setItem(53, returnArrow);

							ArrayDeque<ItemStack> members = new ArrayDeque<ItemStack>(), cooperators = new ArrayDeque<ItemStack>(), visitors = new ArrayDeque<ItemStack>();
							for(UUID memberid : island.getMembersId()) {
								OfflinePlayer member = Bukkit.getOfflinePlayer(memberid);
								ItemStack linkeditem = new ItemManager.SkullBuilder(1, "§c[Équipe] \u00BB " + member.getName()).setOwner(member.getName()).build();
								members.add(linkeditem);
							}
							for(UUID coopid : island.getCooperatorsId()) {
								OfflinePlayer cooperator = Bukkit.getOfflinePlayer(coopid);
								ItemStack linkeditem = new ItemManager.SkullBuilder(1, "§c[Coop] \u00BB " + cooperator.getName()).setOwner(cooperator.getName()).build();
								cooperators.add(linkeditem);
							}
							for(Player visitor : island.getVisitors()) {
								ItemStack visitoritem = new ItemManager.SkullBuilder(1, "§c[Visiteur] \u00BB " + visitor.getName()).setOwner(visitor.getName()).build();
								cooperators.add(visitoritem);
							}
							
							int n = 0;
							while(n < 28) {
								if(!members.isEmpty()) {
									inv.addItem(members.pollFirst());
								} else if(!cooperators.isEmpty()) {
									inv.addItem(cooperators.pollFirst());
								} else {				
									if(visitors.isEmpty()) break;
									inv.addItem(visitors.pollFirst());
								}
								n++;
							}
							
							player.openInventory(inv);
						}
						break;
				default:
					break;
				}
				
			}
			
			
		}
		
	}

}
