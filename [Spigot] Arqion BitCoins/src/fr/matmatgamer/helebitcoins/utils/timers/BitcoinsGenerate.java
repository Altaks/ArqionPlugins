package fr.matmatgamer.helebitcoins.utils.timers;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;	

public class BitcoinsGenerate extends BukkitRunnable {
	
	private Main main;
	private Player player;
	
	public BitcoinsGenerate(Player player, Main main) {
		this.main = main;
		this.player = player;
	}
	
	@Override
	public void run() {
		
		Serveurs Serveurs = new Serveurs();
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if(configuration.get("Génération").equals("True")) {
		
			for (int j = 0; j < Serveurs.LimitePerPlayer(); j++) {
				if(configuration.get("Serveurs." + j + ".placed").equals("True")) {
					int energie = configuration.getInt("Serveurs." + j + ".energie");
					if(energie >= 8) {
						float bitcoins = Float.valueOf(configuration.get("Bitcoins").toString());
						
						bitcoins+=new Random().nextFloat()*0.01;
						energie-=8;
						
						configuration.set("Serveurs." + j + ".energie", energie);
						configuration.set("Bitcoins", bitcoins);
						
						try {
							configuration.save(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(!player.isOnline()) {
			cancel();
		}
	}
}
