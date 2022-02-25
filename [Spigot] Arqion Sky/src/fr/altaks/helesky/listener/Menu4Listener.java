package fr.altaks.helesky.listener;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import fr.altaks.helesky.Main;
import fr.altaks.helesky.api.MoneyUtil;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.core.islandcore.IslandTier;
import fr.altaks.helesky.utils.ItemManager;

public class Menu4Listener implements Listener {
	
	private Main main;
	
	public Menu4Listener(Main main) {
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
			
			if(event.getView().getTitle().equals("§6Am�lioration d'île")) {
				event.setCancelled(true);
				
				Material clickedType = event.getCurrentItem().getType();
				
				// obtenir l'ile du joueur
				// verif si tier actuel < tier voulu
				// check si assez de monnaie
				// si oui -> achat
				// si non -> refus
				
				if(!main.hasIsland(player.getUniqueId())) {
					player.sendMessage(Main.PREFIX + "§cVous ne pouvez pas acheter d'amélioration, vous n'avez pas d'île actuellement !");
					player.closeInventory();
					return;
				} 
				Island island = main.getPlayerIsland(player.getUniqueId());
				Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
					
					IslandTier selectedTier = IslandTier.Tier1;
					switch(clickedType) {
					
						case GOLD_NUGGET:
							selectedTier = IslandTier.Tier1;
							break;
						case GOLD_INGOT:
							selectedTier = IslandTier.Tier2;
							break;
						case GOLD_BLOCK:
							selectedTier = IslandTier.Tier3;
							break;
						case NETHER_STAR:
							selectedTier = IslandTier.Tier4;
							break;
						
						default: return;
					}
					
					if(selectedTier.getTier() > island.getTier()) {
						try {
							if(main.getMoneyManager().getMoneyOfPlayer(player.getUniqueId()) >= selectedTier.getPrice()) {
								// achat
								island.setTier(island.getTier() + 1);
								main.getMoneyManager().removeMoneyToPlayer(player.getUniqueId(), selectedTier.getPrice());
								player.sendMessage(Main.PREFIX + "Vous venez d'acheter le tier " + selectedTier.getTier() + " pour votre île pour un total de " + MoneyUtil.formatAmount(selectedTier.getPrice()));
								return;
							} else {
								// pas assez d'argent
								player.sendMessage(Main.PREFIX + "Vous n'avez pas l'argent nécessaire à cet achat");
								return;
							}
						} catch (SQLException e) {
							player.sendMessage(Main.PREFIX + "§cUne erreur s'est produite, veuillez en informer le staff au plus vite");
							e.printStackTrace();
						}
					} else {
						player.sendMessage(Main.PREFIX + "Le tier séléctionné est inférieur au tier actuel de votre île !");
						return;
					}
					
				});
			}
			
			
		}
		
	}

}
