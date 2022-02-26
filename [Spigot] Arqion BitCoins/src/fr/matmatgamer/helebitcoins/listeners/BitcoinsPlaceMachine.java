package fr.matmatgamer.helebitcoins.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.Essence;
import fr.matmatgamer.helebitcoins.utils.items.PC;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;
import fr.matmatgamer.helebitcoins.utils.items.Wires;
import fr.matmatgamer.helebitcoins.utils.items.Wrench;

public class BitcoinsPlaceMachine implements Listener {

	Serveurs Serveurs = new Serveurs();
	AllimSolaire AllimSolaire = new AllimSolaire();
	PC PC = new PC();
	AllimEssence AllimEssence = new AllimEssence();
	Batterie Batterie = new Batterie();
	Wrench Wrench = new Wrench();
	Essence Essence2 = new Essence();
	Wires Wires = new Wires();
	
	private Main main;
	public BitcoinsPlaceMachine(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void onPlace(BlockPlaceEvent e) {
		
		Player player = e.getPlayer();
		ItemStack item = e.getItemInHand();
		
		if(item.getItemMeta().getDisplayName().equals(Wrench.getName()) || item.getItemMeta().getDisplayName().equals(Wires.getName()) || item.getItemMeta().getDisplayName().equals(Essence2.getName())) {
			e.setCancelled(true);
			player.sendMessage(Main.prefix + "§6Cet item ne peut pas être placé !");
		}
		
		if(item.getItemMeta().getDisplayName().equals(PC.getName())) {
			File file1 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file1);
			
			File file2 = new File(main.getDataFolder(), "Id/pc.yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
			
			int ID = configuration2.getInt("ID");
			
			configuration2.set(ID + ".owner", player.getUniqueId().toString());
			configuration2.set(ID + ".co", e.getBlock().getLocation());
			configuration2.set("ID", ID+1);
			
			final String notPlaced = configuration.getString("PC.placed");
			
			if(notPlaced.equals("True")) {
				e.setCancelled(true);
				player.sendMessage(Main.prefix + "§6Vous avez déjà un ordinateur de contrôle !");
				return;
			}
			
			configuration.set("PC.co", e.getBlock().getLocation());
			configuration.set("PC.placed", "True");
			configuration.set("PC.id", ID);
			
			try {
				configuration.save(file1);
				configuration2.save(file2);
			} catch (IOException error) {
				error.printStackTrace();
			}

			 player.sendMessage(Main.prefix + "§6Vous venez de placer un ordinateur de contrôle.");
			
		}
		
		if(item.getItemMeta().getDisplayName().equals(AllimEssence.getName())) {
			File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			String notPlaced = configuration.getString("AllimEssence.0.placed");
			
			int number = 0;
			
			String allPlaced = "True";
			
			File file2 = new File(main.getDataFolder(), "Id/allimEssence.yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
			
			int ID = configuration2.getInt("ID");
			
			configuration2.set(ID + ".owner", player.getUniqueId().toString());
			configuration2.set(ID + ".co", e.getBlock().getLocation());
			configuration2.set("ID", ID+1);
			
			for (int i = 0; i < AllimEssence.LimitePerPlayer(); i++) {
				if(notPlaced.equals("True")) {
					number++;
					notPlaced = configuration.getString("AllimEssence." + number + ".placed");
				} else {
					allPlaced = "False";
					break;
				}
			}
			if(allPlaced == "True") {
				e.setCancelled(true);
				player.sendMessage(Main.prefix + "§6Vous avez atteint la limite d'Alimentation à essence !");
				return;
			}
			String key = "AllimEssence." + number; 
					
			configuration.set(key + ".co", e.getBlock().getLocation());
			configuration.set(key + ".placed", "True");
			configuration.set(key + ".id", ID);
			
			try {
				configuration.save(file);
				configuration2.save(file2);
			} catch (IOException error) {
				error.printStackTrace();
			}
			
			player.sendMessage(Main.prefix + "§6Vous venez de placer une Alimentation � essence");
			
			
		}
		
		if(item.getItemMeta().getDisplayName().equals(AllimSolaire.getName())) {
			File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			String notPlaced = configuration.getString("AllimSolaire.0.placed");
			
			int number = 0;
			
			String allPlaced = "True";
			
			File file2 = new File(main.getDataFolder(), "Id/allimSolaire.yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
			
			int ID = configuration2.getInt("ID");
			
			configuration2.set(ID + ".owner", player.getUniqueId().toString());
			configuration2.set(ID + ".co", e.getBlock().getLocation());
			configuration2.set("ID", ID+1);
			
			for (int i = 0; i < AllimSolaire.LimitePerPlayer(); i++) {
				if(notPlaced.equals("True")) {
					number++;
					notPlaced = configuration.getString("AllimSolaire." + number + ".placed");
				} else {
					allPlaced = "False";
					break;
				}
			}
			if(allPlaced == "True") {
				e.setCancelled(true);
				player.sendMessage(Main.prefix + "§6Vous avez atteint la limite d'Alimentation Solaire !");
				return;
			}
			
			configuration.set("AllimSolaire." + number + ".co", e.getBlock().getLocation());
			configuration.set("AllimSolaire." + number + ".placed", "True");
			configuration.set("AllimSolaire." + number + ".id", ID);
			
			try {
				configuration.save(file);
				configuration2.save(file2);
			} catch (IOException error) {
				error.printStackTrace();
			}
			
			player.sendMessage(Main.prefix + "§6Vous venez de placer une Alimentation Solaire !");
			
			
		}
		
		if(item.getItemMeta().getDisplayName().equals(Serveurs.getName())) {
			File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			String notPlaced = configuration.getString("Serveurs.0.placed");
			
			int number = 0;
			
			String allPlaced = "True";
			
			File file2 = new File(main.getDataFolder(), "Id/serveurs.yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
			
			int ID = configuration2.getInt("ID");
			
			configuration2.set(ID + ".owner", player.getUniqueId().toString());
			configuration2.set(ID + ".co", e.getBlock().getLocation());
			configuration2.set("ID", ID+1);
			
			for (int i = 0; i < Serveurs.LimitePerPlayer(); i++) {
				if(notPlaced.equals("True")) {
					number++;
					notPlaced = configuration.getString("Serveurs." + number + ".placed");
				} else {
					allPlaced = "False";
					break;
				}
			}
			if(allPlaced == "True") {
				e.setCancelled(true);
				player.sendMessage(Main.prefix + "§6Vous avez atteint la limite de serveurs !");
				return;
			}
			
			configuration.set("Serveurs." + number + ".co", e.getBlock().getLocation());
			configuration.set("Serveurs." + number + ".placed", "True");
			configuration.set("Serveurs." + number + ".id", ID);
			
			try {
				configuration.save(file);
				configuration2.save(file2);
			} catch (IOException error) {
				error.printStackTrace();
			}
			
			player.sendMessage(Main.prefix + "§6Vous venez de placer un serveur.");
			
			
		}
		
		if(item.getItemMeta().getDisplayName().equals(Batterie.getName())) {
			File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			String notPlaced = configuration.getString("Batterie.0.placed");
			
			int number = 0;
			
			String allPlaced = "True";
			
			File file2 = new File(main.getDataFolder(), "Id/batteries.yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
			
			int ID = configuration2.getInt("ID");
			
			configuration2.set(ID + ".owner", player.getUniqueId().toString());
			configuration2.set(ID + ".co", e.getBlock().getLocation());
			configuration2.set("ID", ID+1);
			
			
			for (int i = 0; i < Batterie.LimitePerPlayer(); i++) {
				if(notPlaced.equals("True")) {
					number++;
					notPlaced = configuration.getString("Batterie." + number + ".placed");
				} else {
					allPlaced = "False";
					break;
				}
			}
			if(allPlaced == "True") {
				e.setCancelled(true);
				player.sendMessage(Main.prefix + "§6Vous avez atteint la limite de Batterie !");
				return;
			}
			
			configuration.set("Batterie." + number + ".co", e.getBlock().getLocation());
			configuration.set("Batterie." + number + ".placed", "True");
			configuration.set("Batterie." + number + ".id", ID);
			
			try {
				configuration.save(file);
				configuration2.save(file2);
			} catch (IOException error) {
				error.printStackTrace();
			}
			
			player.sendMessage(Main.prefix + "§6Vous venez de placer une Batterie.");
			
			
		}

	}
}
