package fr.matmatgamer.helebitcoins.utils.timers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;
import fr.matmatgamer.helebitcoins.utils.items.Batterie;

public class BatterieReceived extends BukkitRunnable {
	
	private Main main;
	private Player player;
	
	public BatterieReceived(Player player, Main main) {
		this.main = main;
		this.player = player;
	}
	
	@Override
	public void run() {
		
		final File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		for (int i = 0; i < new Batterie().LimitePerPlayer(); i++) {
			for (int j = 0; j < new AllimSolaire().LimitePerPlayer(); j++) {
				if (configuration.get("AllimSolaire." + j + ".batterie").equals(i)) {
					if(configuration.getInt("AllimSolaire." + j + ".energie") >= 5) {
						if(configuration.getInt("Batterie." + i + ".energie") <= 9995) {
							configuration.set("AllimSolaire." + j + ".energie", configuration.getInt("AllimSolaire." + j + ".energie") - 5);
							configuration.set("Batterie." + i + ".energie", configuration.getInt("Batterie." + i + ".energie") + 5);
						}
					}
				}
			}
			
			for (int j = 0; j < new AllimEssence().LimitePerPlayer(); j++) {
				if (configuration.get("AllimEssence." + j + ".batterie").equals(i)) {
					if(configuration.getInt("AllimEssence." + j + ".energie") >= 10) {
						if(configuration.getInt("Batterie." + i + ".energie") <= 9990) {
							configuration.set("AllimEssence." + j + ".energie", configuration.getInt("AllimEssence." + j + ".energie") - 10);
							configuration.set("Batterie." + i + ".energie", configuration.getInt("Batterie." + i + ".energie") + 10);
						}
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