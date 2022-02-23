
package fr.matmatgamer.helebitcoins.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.BidonEssenceVide;
import fr.matmatgamer.helebitcoins.utils.items.Essence;


public class AllimEssence implements Listener{

	private Main main;
	
	public AllimEssence(Main main) {
		this.main = main;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		fr.matmatgamer.helebitcoins.utils.items.AllimEssence allimEssence = new fr.matmatgamer.helebitcoins.utils.items.AllimEssence();
		Essence essence = new Essence();

		
		if (block == null) return;
		
		
		if (block.getType().equals(allimEssence.Matos())) {
			
			File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			if (player.getItemInHand().getType().equals(Material.AIR)) {
				e.setCancelled(true);
				for (int j = 0; j < allimEssence.LimitePerPlayer(); j++) {
					if(configuration.isSet("AllimEssence." + j + ".id") && configuration.getLocation("AllimEssence." + j + ".co").equals(block.getLocation())) {
						int ess = configuration.getInt("AllimEssence." + j + ".StockEssence");
						player.sendMessage("§6Ce générateur contient " + ess/1000 + "L/50L.");
						break;
					}
				}
			} else if (player.getItemInHand().getType().equals(essence.Matos()) && player.getItemInHand().getItemMeta().getDisplayName().equals(essence.getName())) {
				e.setCancelled(true);
				for (int j = 0; j < allimEssence.LimitePerPlayer(); j++) {
					if(configuration.isSet("AllimEssence." + j + ".id") && configuration.getLocation("AllimEssence." + j + ".co").equals(block.getLocation())) {
						int ess = configuration.getInt("AllimEssence." + j + ".StockEssence");
						
						if (ess<=45000) {
							ess+=5000;
								
							configuration.set("AllimEssence." + j + ".StockEssence", ess);
							
							try {
								configuration.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							player.sendMessage("§9+5L. §6Ce générateur contient maintenant " + ess/1000 + "L/50L.");
							if(!player.getGameMode().equals(GameMode.CREATIVE)) {
								player.setItemInHand(new BidonEssenceVide().getItem());
							}
							break;
						} else {
							player.sendMessage(Main.prefix + " §4Ce générateur est trop plein pour y rajouter 5L d'essence. (§9" + ess/1000 + "/50§4)");
						}
					}
				}
			}
		}

	}
}
