package fr.nowayy.arqionbox.listeners;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.api.MoneyUtil;
import fr.nowayy.arqionbox.core.BoxType;
import fr.nowayy.arqionbox.core.BoxItems.BoxItems;

public class KeyShopListener implements Listener {
	
	private Main main;
	
	public KeyShopListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent event){
		
		// on recup l'inv
		
		Inventory inv = event.getClickedInventory();
		
		// on verif si c'est le bon inv
		
		if(!event.getView().getTitle().equals("�§ArqionBox - §cClés")) {
			return;
		}
		
		// on verif si le joueur a cliqu� sur l'inv du haut
		
		if(event.getView().getTopInventory().equals(inv)) {
			
			// cancel l'event
			event.setCancelled(true);
			// on verif si il clique sur une des cl�s
			ItemStack clickedItem = event.getCurrentItem();
			for(BoxType box : BoxType.values()) {
				if(main.getDisplayKey(box).equals(clickedItem)){
					Player player = (Player)event.getWhoClicked();	
					try {
						if(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId()) >= box.getPrice()) {
							
							main.getMoneyUtil().removeMoneyToPlayer(player.getUniqueId(), box.getPrice());
							player.getInventory().addItem(box.getKeyItem());
							player.sendMessage(Main.PREFIX + "§aVous venez d'acheter une clé pour la box §6" + box.getName() + " §apour le prix de §6" + MoneyUtil.formatAmount(box.getPrice()));
							player.closeInventory();
							
						} else {
							player.sendMessage(Main.PREFIX + "§cVous n'avez pas l'argent nécessaire pour cet achat §7("+ box.getPrice() + "€)");
							return;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;	
				}
			}
			// si oui,
				// on verif sa thune
				// si il a la thune:
					// on lui retire la thune de la cl�
					// on lui give la cl�
					// on ferme l'inv
				// sinon:
					// le pr�venir qu'il est fauch�
			
		}
		
		
		
	}
	
	@EventHandler
	public void onEggPlace(PlayerInteractEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand().equals(BoxItems.spawnerBoxKey)) {
			event.setCancelled(true);
			return;
		}
	}
	
}