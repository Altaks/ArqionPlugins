package fr.matmatgamer.helebitcoins.utils.timers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;
import fr.matmatgamer.helebitcoins.utils.items.Serveurs;

public class BatterieSend extends BukkitRunnable {
	
	private Main main;
	private Player player;
	
	public BatterieSend(Player player, Main main) {
		this.main = main;
		this.player = player;
	}
	
	@Override
	public void run() {
		
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		for (int i = 0; i < new Batterie().LimitePerPlayer(); i++) {
			for (int j = 0; j < new Serveurs().LimitePerPlayer(); j++) {
				if (configuration.get("Serveurs." + j + ".batterie").equals(i)) {
					if(configuration.getInt("Serveurs." + j + ".energie") <= 2000) {
						if(configuration.getInt("Batterie." + i + ".energie") >= 5) {
							configuration.set("Serveurs." + j + ".energie", configuration.getInt("Serveurs." + j + ".energie") + 5);
							configuration.set("Batterie." + i + ".energie", configuration.getInt("Batterie." + i + ".energie") - 5);
						}
					}
				}
			}
			
			if (configuration.get("PC.batterie").equals(i)) {
				if(configuration.getInt("PC.energie") <= 5000) {
					if(configuration.getInt("Batterie." + i + ".energie") >= 5) {
						configuration.set("PC.energie", configuration.getInt("PC.energie") + 5);
						configuration.set("Batterie." + i + ".energie", configuration.getInt("Batterie." + i + ".energie") - 5);
					}
				}
			}
				
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		if(!player.isOnline()) {
			cancel();
		}
	}
}