package fr.altaks.helemoney.listener;

import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.BankTier;
import fr.altaks.helemoney.api.MoneyUtil;

public class BankUpgradeListener implements Listener {
	
	private Main main;
	
	public BankUpgradeListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerClickEvent(InventoryClickEvent event) {
		
		if(event.getInventory() == null || event.getClickedInventory() == null) return;
		
		if(event.getView().getTitle().equals("§cAmélioration de banque \u00BB") && event.getClickedInventory().contains(Material.BARRIER)) {
			
			Player player = (Player) event.getWhoClicked();
			int slot = event.getSlot();
			
			if((0 <= slot) && (slot <= 53)) {
				event.setCancelled(true);
				
				int tier = 1;
				
				switch (slot) {
					case 20:
						tier = 2;
						break;
					case 24:
						tier = 3;
						break;
					case 28:
						tier = 4;
						break;
					case 34:
						tier = 5;
						break;
					case 40:
						tier = 6;
						break;
					case 53:
						player.closeInventory();
						return;
				default:
					return;
				}
				
				try {
					tryToUpgradeToTier(player, tier);
				} catch (SQLException e) {
					player.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue, veuillez prévenir le staff afin qu'il règle ce problème au plus vite");
					e.printStackTrace();
				}
				
			} else return;
		}
		
	}
	
	public void tryToUpgradeToTier(Player player, int tier) throws SQLException {
		
		int actualTier = main.getMoneyUtil().getBankTier(player.getUniqueId());
		if(actualTier < tier-1) {
			player.sendMessage(MoneyUtil.API_PREFIX + "§cVous devez d'abord avoir le tier §e" + (tier-1) + " avant d'acquérir le tier §e" + tier + "§c!");
			return;
		} else if(actualTier >= tier) {
			player.sendMessage(MoneyUtil.API_PREFIX + "§cVous possédez déjà le tier §e" + tier + "§c!");
		} else {
			if(BankTier.getFromId(tier).price <= main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId())) {
				main.getMoneyUtil().updateBankTier(player.getUniqueId(), tier);
				main.getMoneyUtil().removeMoneyToPlayer(player.getUniqueId(), BankTier.getFromId(tier).price);
				player.sendMessage(MoneyUtil.API_PREFIX + "§6Vous venez d'acheter la banque de tier " + tier);
			} else {
				player.sendMessage(MoneyUtil.API_PREFIX + "§cVous n'avez pas l'argent requis pour acheter ceci !");
				return;
			}
		}
		
		
		
		
	}
	
	

}
