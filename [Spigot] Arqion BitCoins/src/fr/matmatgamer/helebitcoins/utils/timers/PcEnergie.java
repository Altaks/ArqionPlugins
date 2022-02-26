package fr.matmatgamer.helebitcoins.utils.timers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.matmatgamer.helebitcoins.Main;	

public class PcEnergie extends BukkitRunnable {
	
	private Main main;
	private Player player;
	
	public PcEnergie(Player player, Main main) {
		this.main = main;
		this.player = player;
	}
	
	@Override
	public void run() {
		
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if(configuration.get("Génération").equals("True")) {
	
			int energie = configuration.getInt("PC.energie");
			if(energie >= 10) {
				
				energie-=10;
						
				configuration.set("PC.energie", energie);
				
				try {
					configuration.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				configuration.set("Génération", "False");
				
				try {
					configuration.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!player.isOnline()) {
			cancel();
		}
	}
}
