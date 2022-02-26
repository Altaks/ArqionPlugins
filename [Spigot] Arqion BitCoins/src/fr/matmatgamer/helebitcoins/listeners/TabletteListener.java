package fr.matmatgamer.helebitcoins.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.utils.ItemBuilder;
import fr.matmatgamer.helebitcoins.utils.items.Tablette;

@SuppressWarnings("deprecation")
public class TabletteListener implements Listener{

	private String invName = "§0§l§ke§0DA§0§l§ke§0R§0§l§kee§0K W§0§l§ke§0EB§0§l§ke";
	
	@EventHandler
	private void onClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		ItemStack item = player.getItemInHand();
		
		if(item.getType().equals(new Tablette().Matos()) && item.getItemMeta().getDisplayName().equals(new Tablette().getName())) {
			Inventory inv = Bukkit.createInventory(null, 27, invName);
			
			inv.setItem(10, new ItemBuilder(Material.DIRT, 1).setName("§cDIRT").setLore("test1 1", "test2 2", "test3 3", "test4 4", "§65 5", "test6 6").build());
			for (int slot : new int[] {11, 12, 13, 14, 15, 16}) {
				inv.setItem(slot, new ItemBuilder(Material.BEDROCK, 1).setName("§cItem Indisponible").build());
			}
			ItemStack glassPane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").hideAttributes().build();
			for (int j = 0; j < inv.getSize(); j++) {
				if(inv.getItem(j) == null) {
					inv.setItem(j, glassPane);
				}
				
			}
			
			player.openInventory(inv);
		}

	}
	
	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		
		if(e.getView().getTitle().equals(invName)) {
			e.setCancelled(true);
			if(item.getType().equals(Material.BEDROCK)) {
				player.sendMessage("§4Cet item ne peut pas être acheté !");
				return;
			}
			String LorePrice = item.getItemMeta().getLore().get(4).replace("§6", "").split(" ")[0];
			int Price = Integer.valueOf(LorePrice);
			Bukkit.broadcastMessage(""+Price);
		}
		
		
		

	}
	
}
