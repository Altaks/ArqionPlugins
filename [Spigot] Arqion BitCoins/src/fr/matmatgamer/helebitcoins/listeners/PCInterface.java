package fr.matmatgamer.helebitcoins.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;
import fr.matmatgamer.helebitcoins.utils.items.AdminWrench;
import fr.matmatgamer.helebitcoins.utils.items.PC;
import fr.matmatgamer.helebitcoins.utils.items.Wrench;

public class PCInterface implements Listener {

	private Main main;
	
	public PCInterface(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(!e.hasBlock()) {
			return;
		}
		Material block = e.getClickedBlock().getType();
		Location loc = e.getClickedBlock().getLocation();
		
		
		if(player.getItemInHand().getType().equals(new Wrench().Matos()) || player.getItemInHand().getType().equals(new AdminWrench().Matos())) return; 
		if(block.equals(new PC().Matos())) {
			File file = new File(main.getDataFolder(), "Id/pc.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
		
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(i + "").equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(loc)) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							e.setCancelled(true);
							
							Inventory inv = Bukkit.createInventory(null, 27, Main.prefix + "§8- §4Menu de contr�le");
							
							
							String OnOff;
							
							if(configuration2.get("Génération").equals("True")){
								OnOff = "§aOn";
							} else {
								OnOff = "§4Off";
							}
							
							try {
								inv.setItem(10, new ItemBuilder(Material.BARRIER).setNBT("{ID:OnOff}").setName("§4On§c/§4Off")
										.setLore("§cArrête ou démarre la production de BitCoins.", "§cL'énergie se génère toujours !","", "§cProduction: " + OnOff).build());
								for (int slot : new int[] {13, 16}) {
									inv.setItem(slot, new ItemBuilder(Material.BEDROCK).setNBT("{ID:Soon}").setName("§c§l§nSoon").build());								
									}
								
							} catch (Exception e1) {
								e1.printStackTrace();
							};
							
							ItemStack glassPane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").hideAttributes().build();
							for (int j = 0; j < inv.getSize(); j++) {
								if(inv.getItem(j) == null) {
									inv.setItem(j, glassPane);
								}
								
							}
							
							player.openInventory(inv);
							
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if(e.getView().getTitle().equals(Main.prefix + "§8- §4Menu de contrôle")) {
			
			if(e.getCurrentItem().getType().equals(Material.BARRIER)){
				if(configuration.get("Génération").equals("True")){
					configuration.set("Génération", "False");
					player.sendMessage(Main.prefix + "§cVous venez de désactiver la génération de BitCoins.");
				} else {
					configuration.set("Génération", "True");
					player.sendMessage(Main.prefix + "§cVous venez d'activer la génération de BitCoins.");
				}
				player.closeInventory();

				try {
					configuration.save(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}

	}
	
}
