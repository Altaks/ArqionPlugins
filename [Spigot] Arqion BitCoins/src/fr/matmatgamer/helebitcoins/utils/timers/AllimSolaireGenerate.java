package fr.matmatgamer.helebitcoins.utils.timers;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.items.AllimSolaire;	

public class AllimSolaireGenerate extends BukkitRunnable {
	
	private Main main;
	private Player player;
	
	public AllimSolaireGenerate(Player player, Main main) {
		this.main = main;
		this.player = player;
	}
	
	@Override
	public void run() {
		
		AllimSolaire AllimSolaire = new AllimSolaire();
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		World world = Bukkit.getWorld("world");
		
		if (world.getTime() < 13000 && !world.isThundering() && !world.hasStorm()) { 
		
			for (int j = 0; j < AllimSolaire.LimitePerPlayer(); j++) {
				String key = "AllimSolaire." + j;
				if(configuration.get(key + ".placed").equals("True")) {
					int energie = configuration.getInt(key + ".energie");
					if(energie <=995) {
	
						energie+=5;
							
						configuration.set(key + ".energie", energie);
						
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
