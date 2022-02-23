package fr.altaks.heleshop.listener;

import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.altaks.heleshop.Main;
import fr.altaks.heleshop.api.MoneyUtil;
import fr.altaks.heleshop.manager.ShopItem;
import fr.altaks.heleshop.manager.ShopMenu;

public class ShopClickListener implements Listener {

	private Main main;
	
	public ShopClickListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onShopClickEvent(InventoryClickEvent event) {
		
		if(event.getView().getTitle().equals("§eBoutique \u00BB")) {
			if(event.getClickedInventory() == null) return;
			if(event.getClickedInventory().getHolder() == null) {
				
				event.setCancelled(true);
				
				int slot = event.getSlot();
				ShopMenu menuToOpen = ShopMenu.TOOLS;
				
				switch (slot) {
					case 10:
						menuToOpen = ShopMenu.TOOLS;
						break;
					case 12:
						menuToOpen = ShopMenu.FOOD;
						break;
					case 14:
						menuToOpen = ShopMenu.POTIONS;
						break;
					case 16:
						menuToOpen = ShopMenu.LOOTS;
						break;
					case 29:
						menuToOpen = ShopMenu.TERRAIN_BLOCKS;
						break;
					case 31:
						menuToOpen = ShopMenu.BUILDING_BLOCKS;
						break;
					case 33:
						menuToOpen = ShopMenu.SEEDS;
						break;
					case 44:
						event.getWhoClicked().closeInventory();
						return;
					default:
						return;
				}
				
				Player player = (Player) event.getWhoClicked();
				
				Inventory inv = Bukkit.createInventory(null, 5*9, menuToOpen.title);
				
				for (int i = 0; i < 9; i++) {
					inv.setItem(i, ShopItem.glasspane);
					inv.setItem(i+(4*9), ShopItem.glasspane);
				}
				
				main.getShopMenus().get(menuToOpen).forEach(shopitem -> inv.addItem(shopitem.getLoredItem()));
				
				for(int islot = 0; islot < 45; islot++) if(inv.getItem(islot) != null) {
					if(inv.getItem(islot).getType() == Material.AIR) {
						inv.setItem(islot, ShopItem.glasspane);
					}
				} else inv.setItem(islot, ShopItem.glasspane);
				
				inv.setItem(36, ShopItem.returnArrow);
				inv.setItem(44, ShopItem.barrier);
				
				player.openInventory(inv);
				return;
			}
		} else {
			for(ShopMenu menu : ShopMenu.values()) {
				if(event.getView().getTitle().equals(menu.title)) {
					if(event.getClickedInventory() == null) return;
					if(event.getClickedInventory().getHolder() == null) {
						
						// En fonction de l'objet clické et de l'action, Acheter ou vendre.
						event.setCancelled(true);
						List<ShopItem> itemlist = main.getShopMenus().get(menu);
						
						for(ShopItem item : itemlist) {
							if(event.getCurrentItem().equals(item.getLoredItem())) {
								
								ClickType click = event.getClick();
								Player player = (Player)event.getWhoClicked();
								
								try {
									switch (click) {
									case LEFT:
										// Achat simple
										if(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId()) >= item.getBuyingPrice()) {
											main.getMoneyUtil().removeMoneyToPlayer(player.getUniqueId(), item.getBuyingPrice());
											player.getInventory().addItem(item.getRealItem().clone());
										} else {
											player.sendMessage(Main.PREFIX + "§cVous n'avez pas assez d'argent pour acheter ceci ! §r(Argent nécessaire : " + MoneyUtil.formatAmount(item.getBuyingPrice())+")");
										}
										break;
									case SHIFT_LEFT:
										// Achat Stack
										double multiplier = 64 / item.getRealItem().getAmount();
										if(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId())*multiplier >= item.getBuyingPrice()) {
											main.getMoneyUtil().removeMoneyToPlayer(player.getUniqueId(), item.getBuyingPrice());
											ItemStack temp = item.getRealItem().clone();
											temp.setAmount(64);
											player.getInventory().addItem(temp);
										} else {
											player.sendMessage(Main.PREFIX + "§cVous n'avez pas assez d'argent pour acheter ceci ! §r(Argent nécessaire : " + MoneyUtil.formatAmount(item.getBuyingPrice())+")");
										}
										break;
									case RIGHT:
										// Vente simple
										if(player.getInventory().containsAtLeast(item.getRealItem(), item.getRealItem().getAmount())) {
											main.getMoneyUtil().addMoneyToPlayer(player.getUniqueId(), item.getSellingPrice());
											player.getInventory().removeItem(item.getRealItem().clone());
										} else {
											player.sendMessage(Main.PREFIX+"§cVous n'avez pas assez d'objets à vendre !");
										}
										
										break;
									case SHIFT_RIGHT:
										// Vente stack
										double multiplier_ = 64 / item.getRealItem().getAmount();
										if(player.getInventory().containsAtLeast(item.getRealItem(), 64)) {
											main.getMoneyUtil().addMoneyToPlayer(player.getUniqueId(), item.getSellingPrice()*multiplier_);
											ItemStack temp = item.getRealItem().clone();
											temp.setAmount(64);
											player.getInventory().removeItem(temp);
										} else {
											player.sendMessage(Main.PREFIX+"§cVous n'avez pas assez d'objets à vendre !");
										}
										break;
									
									default:
										break;
									}
								} catch (SQLException e) {
									player.sendMessage(Main.PREFIX + "§cUne erreur critique est survenue, veuillez prévenir le staff au plus vite afin qu'il règle ce problème");
								}
								
								return;
							}
						} 
						
						if(event.getSlot() == 36) {
							event.getWhoClicked().closeInventory();
							((Player)event.getWhoClicked()).chat("/shop");
						} else if(event.getSlot() == 44) {
							event.getWhoClicked().closeInventory();
						}
					}
				}
			}

		}
	}

}
