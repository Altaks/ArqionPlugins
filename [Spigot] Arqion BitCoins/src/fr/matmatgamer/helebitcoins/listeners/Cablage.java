package fr.matmatgamer.helebitcoins.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.PC;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;
import fr.matmatgamer.helebitcoins.utils.items.Wires;

public class Cablage implements Listener {

	private Main main;
	
	public Cablage(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(!e.hasBlock()) {
			return;
		}
		Block block = e.getClickedBlock();
		if(!player.getInventory().getItemInHand().getType().equals(new Wires().Matos()) || !player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(new Wires().getName())){
			return;
		}
		e.setCancelled(true);
		if(block.getType().equals(new Serveurs().Matos())) {
			
			File file = new File(main.getDataFolder(), "Id/serveurs.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);

			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(""+i).equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(e.getClickedBlock().getLocation())) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							
							int ServerId = -1;
							for (int j = 0; j < new Serveurs().LimitePerPlayer(); j++) {
								if(configuration2.isSet("Serveurs." + j + ".id") && configuration2.get("Serveurs." + j + ".id").equals(i)) {
									if(configuration2.get("Serveurs." + j + ".batterie").equals("False")) {
										ServerId = j;
									}
									
								}
							}
							
							if(ServerId==-1) {
								return;
							}
							if(!player.getGameMode().equals(GameMode.CREATIVE)) {
								player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							}
							
							if(configuration2.get("CablageEnCour").equals("False")) {
								configuration2.set("CablageEnCour", null);
								configuration2.set("CablageEnCour.loc", e.getClickedBlock().getLocation());
								configuration2.set("CablageEnCour.type", "Serveurs");
								configuration2.set("CablageEnCour.machineNum", ServerId);
								player.sendMessage(Main.prefix + "§cPremière machine en mémoire. Veuillez maintenant cliquer sur une Batterie. Vous pouvez annuler a tout moment en faisant §9/cancelCablage");
							} else {
								
								if(configuration2.get("CablageEnCour.type").equals("Batterie")) {
									configuration2.set("Serveurs." + ServerId + ".batterie", configuration2.get("CablageEnCour.machineNum"));
									for (String str : new String[] {"CablageEnCours.loc", "CablageEnCour.type", "CablageEnCour.machineNum"}) {
										configuration2.set(str, null);
									}
									configuration2.set("CablageEnCour", "False");
									player.sendMessage(Main.prefix + "�cLa machine a �t� reli� avec succ�s !");
								}
							}
						}
					}
				}
			}
			
			try {
				configuration2.save(file2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}

		
		if(block.getType().equals(new PC().Matos())) {
	
			File file = new File(main.getDataFolder(), "Id/pc.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);
		
			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(""+i).equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(e.getClickedBlock().getLocation())) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							
							if(!player.getGameMode().equals(GameMode.CREATIVE)) {
								player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							}
							
							if(!configuration2.get("PC.batterie").equals("False")) { return; }
							
							if(configuration2.get("CablageEnCour").equals("False")) {
								for (String str : new String[] {"CablageEnCour", "CablageEnCour.machineNum"}) {
									configuration2.set(str, null);
								}
								
								
								configuration2.set("CablageEnCour.loc", e.getClickedBlock().getLocation());
								configuration2.set("CablageEnCour.type", "PC");
								player.sendMessage(Main.prefix + "§cPremière machine en mémoire. Veuillez maintenant cliquer sur une Batterie. Vous pouvez annuler à tout moment en faisant §9/cancelCablage");
							} else {
								if(configuration2.get("CablageEnCour.type").equals("Batterie")) {
									configuration2.set("PC.batterie", configuration2.get("CablageEnCour.machineNum"));
									for (String str : new String[] {"CablageEnCour.loc", "CablageEnCour.type", "CablageEnCour.machineNum"}) {
										configuration2.set(str, null);
									}
									configuration2.set("CablageEnCour", "False");
									player.sendMessage(Main.prefix + "§cLa machine a été relié avec succès !");
								}
							}
						}
					}
				}
			}
			
			try {
				configuration2.save(file2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
		
		if(block.getType().equals(new AllimEssence().Matos())) {
			
			File file = new File(main.getDataFolder(), "Id/allimEssence.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);

			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(""+i).equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(e.getClickedBlock().getLocation())) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							
							int AllimEssenceId = -1;
							for (int j = 0; j < new AllimEssence().LimitePerPlayer(); j++) {
								if(configuration2.isSet("AllimEssence." + j + ".id") && configuration2.get("AllimEssence." + j + ".id").equals(i)) {
									if(configuration2.get("AllimEssence." + j + ".batterie").equals("False")) {
										AllimEssenceId = j;
									}
								}
							}
							
							if(AllimEssenceId==-1) {
								return;
							}
							
							if(!player.getGameMode().equals(GameMode.CREATIVE)) {
								player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							}
							if(configuration2.get("CablageEnCour").equals("False")) {
								configuration2.set("CablageEnCour", null);
								configuration2.set("CablageEnCour.loc", e.getClickedBlock().getLocation());
								configuration2.set("CablageEnCour.type", "AllimEssence");
								configuration2.set("CablageEnCour.machineNum", AllimEssenceId);
								player.sendMessage(Main.prefix + "§cPremière machine en mémoire. Veuillez maintenant cliqué sur une Batterie. Vous pouvez annuler a tout moment en faisant §9/cancelCablage");
							} else {
								
								if(configuration2.get("CablageEnCour.type").equals("Batterie")) {
									configuration2.set("AllimEssence." + AllimEssenceId + ".batterie", configuration2.get("CablageEnCour.machineNum"));
									for (String str : new String[] {"CablageEnCour.loc", "CablageEnCour.type", "CablageEnCour.machineNum"}) {
										configuration2.set(str, null);
									}
									configuration2.set("CablageEnCour", "False");
									player.sendMessage(Main.prefix + "§cLa machine a été relié avec succès !");
								}
							}
						}
					}
				}
			}
			
			try {
				configuration2.save(file2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
		if(block.getType().equals(new AllimSolaire().Matos())) {
			
			File file = new File(main.getDataFolder(), "Id/allimSolaire.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);

			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(""+i).equals("BREAK")) {
					if(configuration.getLocation(i + ".co").equals(e.getClickedBlock().getLocation())) {
						if(configuration.get(i + ".owner").equals(player.getUniqueId().toString())) {
							
							int AllimSolaireId = -1;
							for (int j = 0; j < new AllimSolaire().LimitePerPlayer(); j++) {
								if(configuration2.isSet("AllimSolaire." + j + ".id") && configuration2.get("AllimSolaire." + j + ".id").equals(i)) {
									if(configuration2.get("AllimSolaire." + j + ".batterie").equals("False")) {
										AllimSolaireId = j;
									}
								}
							}
							
							if(AllimSolaireId==-1) {
								return;
							}
							
							if(!player.getGameMode().equals(GameMode.CREATIVE)) {
								player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							}
							
							if(configuration2.get("CablageEnCour").equals("False")) {
								configuration2.set("CablageEnCour", null);
								configuration2.set("CablageEnCour.loc", e.getClickedBlock().getLocation());
								configuration2.set("CablageEnCour.type", "AllimSolaire");
								configuration2.set("CablageEnCour.machineNum", AllimSolaireId);
								player.sendMessage(Main.prefix + "§cPremière machine en mémoire. Veuillez maintenant cliquer sur une Batterie. Vous pouvez annuler à tout moment en faisant §�9/cancelCablage");
							} else {
								
								if(configuration2.get("CablageEnCour.type").equals("Batterie")) {
									configuration2.set("AllimSolaire." + AllimSolaireId + ".batterie", configuration2.get("CablageEnCour.machineNum"));
									
									for (String str : new String[] {"CablageEnCour.loc", "CablageEnCour.type", "CablageEnCour.machineNum"}) {
										configuration2.set(str, null);
									}
									configuration2.set("CablageEnCour", "False");
									player.sendMessage(Main.prefix + "§cLa machine a été relié avec succès !");
								}
							}
						}
					}
				}
			}
			
			try {
				configuration2.save(file2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
		if(block.getType().equals(new Batterie().Matos())) {
			
			File file = new File(main.getDataFolder(), "Id/batteries.yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			File file2 = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration2 = YamlConfiguration.loadConfiguration(file2);

			int ID = configuration.getInt("ID");
			
			for (int i = 0; i < ID; i++) {
				if(!configuration.get(""+i).equals("BREAK") && configuration.getLocation(i + ".co")
						.equals(e.getClickedBlock().getLocation()) && configuration.get(i + ".owner")
						.equals(player.getUniqueId().toString())) {
							
						int Batterieid = -1;
						for (int j = 0; j < new Batterie().LimitePerPlayer(); j++) {
							if(configuration2.isSet("Batterie." + j + ".id") && configuration2.get("Batterie." + j + ".id").equals(i)) {
								Batterieid = j;
							}
						}
						
						if(Batterieid==-1) return;
						
						if(!player.getGameMode().equals(GameMode.CREATIVE)) {
							player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						}
							
						if(configuration2.get("CablageEnCour").equals("False")) {
							configuration2.set("CablageEnCour", null);
							configuration2.set("CablageEnCour.loc", e.getClickedBlock().getLocation());
							configuration2.set("CablageEnCour.type", "Batterie");
							configuration2.set("CablageEnCour.machineNum", Batterieid);
							player.sendMessage(Main.prefix + "§cPremière machine en mémoire. Veuillez maintenant cliquer sur une autre (Pc, Générateur à essence, Allimentation Solaire, Serveurs). Vous pouvez annuler a tout moment en faisant §9/cancelCablage");
						} else {
							
							for (String str : new String[] {"Serveurs", "PC", "AllimEssence, AllimSolaire"}) {
								if (configuration2.get("CablageEnCour.type").equals(str)) {
									configuration2.set(str +"." + configuration2.get("CablageEnCour.machineNum") + ".batterie", Batterieid);
									for (String str2 : new String[] {"CablageEnCour.loc", "CablageEnCour.type", "CablageEnCour.machineNum"}) {
										configuration2.set(str2, null);
									}
									configuration2.set("CablageEnCour", "False");
									player.sendMessage(Main.prefix + "§cLa machine a été reliée avec succès !");
								}
							}
						
						}
					}
				}
				
			
			
		try {
			configuration2.save(file2);
		} catch (IOException e1) {
			e1.printStackTrace();
			}
			
		}
		
	}
	
}
