package fr.altaks.helesky.listener;

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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.utils.ItemManager.ItemBuilder;
import fr.altaks.helesky.utils.ItemManager;
import fr.altaks.helesky.utils.LoreUtil;

public class Menu5Listener implements Listener {
	
	private Main main;
	
	public Menu5Listener(Main main) {
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
			
			if(event.getView().getTitle().equals("�8HeleSky - Gestion Joueurs")) {
				event.setCancelled(true);
			
				Material clickedType = event.getCurrentItem().getType(); 
				if(clickedType == Material.PLAYER_HEAD) {
					
					// get pseudo
					SkullMeta meta = (SkullMeta) event.getCurrentItem().getItemMeta();
					OfflinePlayer target = meta.getOwningPlayer();
					
					Inventory inv = Bukkit.createInventory(null, 3*9, "�cHeleSky - Gestion "+target.getName());
					
					Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
						
						for(int i : new int[] {0,1,2,3,4,5,6,7,8,9,11,13,15,17,18,19,20,21,22,23,24,25}) {
							inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
						}
						
						
						ItemStack expulse = new ItemManager.ItemBuilder(Material.WITHER_ROSE, 1, "�cExpulser de l'�le").setLore("","�c/!\\ Retire le joueur de l'�quipe/coop�rateurs","").build();
						inv.setItem(10,expulse);
						
						if(main.hasIsland(player.getUniqueId())) {
							Island island = main.getPlayerIsland(player.getUniqueId());
							if(!island.getBannedIds().contains(target.getUniqueId())) {
								ItemStack ban = new ItemManager.ItemBuilder(Material.BARRIER, 1, "�cBannir de l'�le").setLore("","�c/!\\ Bannit le joueur de l'�quipe/coop�rateurs","").build();
								inv.setItem(12, ban);
							} else {
								ItemStack unban = new ItemManager.ItemBuilder(Material.BARRIER, 1, "�cD�bannir de l'�le").setLore("","�c/!\\ D�bannit le joueur de l'�quipe/coop�rateurs","").build();
								inv.setItem(12, unban);
							}
							
							if(!island.getCooperatorsId().contains(target.getUniqueId())) {
								ItemStack coopMove = new ItemManager.ItemBuilder(Material.LEAD, 1, "�cAjouter un coop�rateur").setLore("","�c/!\\ Ajoute le joueur en coop�rateur","�cLes joueurs en coop.", "�cpeuvent vous trahir").build();
								inv.setItem(14, coopMove);
							} else {
								ItemStack coopMove = new ItemManager.ItemBuilder(Material.LEAD, 1, "�cRetirer un coop�rateur").setLore("","�c/!\\ Retire le joueur des coop�rateurs","").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
								inv.setItem(14, coopMove);
							}
							
							if(island.getMembersId().contains(target.getUniqueId())) {
								ItemStack teamMove = new ItemManager.ItemBuilder(Material.TOTEM_OF_UNDYING, 1, "�cRetirer de l'�quipe").setLore("","�c/!\\ Retire le joueur de l'�quipe","").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
								inv.setItem(16, teamMove);
							} else {
								ItemStack teamMove = new ItemManager.ItemBuilder(Material.TOTEM_OF_UNDYING, 1, "�cAjouter � l'�quipe").setLore("","�c/!\\ Ajoute le joueur � l'�quipe","").build();
								inv.setItem(16, teamMove);
							}
						}
						
						inv.setItem(26, new ItemManager.ItemBuilder(Material.ARROW, 1, "�cRetour").build());
					});
					player.openInventory(inv);
					
				} else if(clickedType == Material.ARROW) {
					
					Island island = main.getIslandsFromId().get(main.getIslandIDFromUUID().get(player.getUniqueId()));
					Inventory inv = Bukkit.createInventory(null, 5 * 9, "�cHeleSky \u00BB");
					
					// l'ile existe et le joueur est link a une ile
					ItemStack book = new ItemManager.ItemBuilder(Material.BOOK, 1, "�cInformations").setLore(LoreUtil.getInformationBookModifiedLore(island)).build();

					Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
						
						ItemStack grass = new ItemManager.ItemBuilder(Material.GRASS_BLOCK, 1, "�3Se t�l�porter").build();
						ItemStack bed = new ItemManager.ItemBuilder(Material.RED_BED, 1, "�cD�finir le point de spawn de l��le").build();
						ItemStack goldNugget = new ItemManager.ItemBuilder(Material.GOLD_NUGGET, 1, "�cTop 10 levels").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
						ItemStack sign = new ItemManager.ItemBuilder(Material.OAK_SIGN, 1, "�cD�finir le point de warp de l��le").build();
						
						ItemStack head = new ItemManager.SkullBuilder(1, "�cGestion des joueurs").setOwner(player.getName()).build();

						inv.setItem(4, book);
						
						inv.setItem(20, grass);
						inv.setItem(30, bed);
						inv.setItem(22, goldNugget);
						inv.setItem(32, sign);
						inv.setItem(24, head);
						
						for (int i = 0; i < inv.getSize(); i++) {
							if(inv.getItem(i) != null) {
								if(inv.getItem(i).getType() == Material.AIR) {
									inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
								}
							} else inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
						}
					});

					player.openInventory(inv);
					
				}
			}
		}
	}
}
