package fr.matmatgamer.helebitcoins.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;

public class GenerateYAML {

	private Main main;
	public GenerateYAML(Main main) {
		this.main = main;
	}
	
	File ServeursID = new File(main.getDataFolder(), "Id/serveurs.yml");
	YamlConfiguration ServerFileConfigurationServeurs = YamlConfiguration.loadConfiguration(ServeursID);

	File BatteriesID = new File(main.getDataFolder(), "Id/batteries.yml");
	YamlConfiguration ServerFileConfigurationBatteries = YamlConfiguration.loadConfiguration(BatteriesID);
	
	File AllimEssenceID = new File(main.getDataFolder(), "Id/allimEssence.yml");
	YamlConfiguration ServerFileConfigurationAllimEssence = YamlConfiguration.loadConfiguration(AllimEssenceID);
	
	File AllimSolaireID = new File(main.getDataFolder(), "Id/allimSolaire.yml");
	YamlConfiguration ServerFileConfigurationAllimSolaire = YamlConfiguration.loadConfiguration(AllimSolaireID);
	
	File PCID = new File(main.getDataFolder(), "Id/pc.yml");
	YamlConfiguration ServerFileConfigurationPC = YamlConfiguration.loadConfiguration(PCID);
	
	public void UpdateYAML(Player player) {
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		
		if(!file.exists()) {
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			for (String str : new String[] {"Génération", "CablageEnCour", "PC.placed", "PC.batterie"}) {
				configuration.set(str, "False");
			}
			configuration.set("Bitcoins", 0);
			configuration.set("PC.co", null);
			configuration.set("PC.energie", 0);
			
			AllimEssence allimEssence = new AllimEssence();
			for (int i = 0; i < allimEssence.LimitePerPlayer(); i++) {
				String keyAEss = "AllimEssence." + i;
				configuration.set(keyAEss + ".placed", "False");
				configuration.set(keyAEss + ".co", null);
				configuration.set(keyAEss + ".energie", 0);
				configuration.set(keyAEss + ".batterie", "False");
			}
			
			AllimSolaire allimSolaire = new AllimSolaire();
			for (int i = 0; i < allimSolaire.LimitePerPlayer(); i++) {
				String keyASolaire = "AllimSolaire." + i;
				configuration.set(keyASolaire + ".placed", "False");
				configuration.set(keyASolaire + ".co", null);
				configuration.set(keyASolaire + ".energie", 0);
				configuration.set(keyASolaire + ".batterie", "False");
			}
			
			Batterie batterie = new Batterie();
			for (int i = 0; i < batterie.LimitePerPlayer(); i++) {
				String keyBatterie = "Batterie." + i;
				configuration.set(keyBatterie + ".placed", "False");
				configuration.set(keyBatterie + ".co", null);
				configuration.set(keyBatterie + ".energie", 0);
			}
			
			Serveurs serveurs = new Serveurs();
			for (int i = 0; i < serveurs.LimitePerPlayer(); i++) {
				String keyServ = "Serveurs." + i;
				configuration.set(keyServ + ".placed", "False");
				configuration.set(keyServ + ".co", null);
				configuration.set(keyServ + ".energie", 0);
				configuration.set(keyServ + ".batterie", "False");
			}
			
	
	
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!ServeursID.exists()) {
			
			ServerFileConfigurationServeurs.set("ID", 0);
			try {
				ServerFileConfigurationServeurs.save(ServeursID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		if(!BatteriesID.exists()) {
			
			ServerFileConfigurationBatteries.set("ID", 0);
			try {
				ServerFileConfigurationBatteries.save(BatteriesID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		if(!AllimEssenceID.exists()) {
			
			ServerFileConfigurationAllimEssence.set("ID", 0);
			try {
				ServerFileConfigurationAllimEssence.save(AllimEssenceID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		if(!AllimSolaireID.exists()) {
			
			ServerFileConfigurationAllimSolaire.set("ID", 0);
			try {
				ServerFileConfigurationAllimSolaire.save(AllimSolaireID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		if(!PCID.exists()) {
			
			ServerFileConfigurationPC.set("ID", 0);
			try {
				ServerFileConfigurationPC.save(PCID);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
