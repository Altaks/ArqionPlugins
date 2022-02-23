package fr.matmatgamer.helebitcoins.utils.timers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimEssence;

public class AllimEssenceGenerate extends BukkitRunnable {
	
	private Main main;
	private Player player;
	
	public AllimEssenceGenerate(Player player, Main main) {
		this.main = main;
		this.player = player;
	}
	
	@Override
	public void run() {
		
		AllimEssence AllimEssence = new AllimEssence();
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		for (int j = 0; j < AllimEssence.LimitePerPlayer(); j++) {
			String key = "AllimEssence." + j;
			if(configuration.get(key + ".placed").equals("True")) {
				int energie = configuration.getInt(key + ".energie");
				int essence = configuration.getInt(key + ".StockEssence");
				if(energie <=960 && essence>=25) {

					energie+=40;
					essence-=25;
						
					configuration.set(key + ".energie", energie);
					configuration.set(key + ".StockEssence", essence);
					
					try {
						configuration.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(!player.isOnline()) {
			cancel();
		}
	}
}
