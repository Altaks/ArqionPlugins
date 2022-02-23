package fr.nowayy.helecore.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.nowayy.helecore.Main;

public class SetSpawnCommand implements CommandExecutor {
	
	private Plugin pl = Main.getPlugin(Main.class);
	
	public SetSpawnCommand(Main main) {
		this.pl = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (sender instanceof Player) {
				
				Player p = (Player) sender;
				
				File set_spawn = new File(pl.getDataFolder(), "setSpawn.yml");
				YamlConfiguration config = YamlConfiguration.loadConfiguration(set_spawn);
				
				config.set("SpawnSet.world", p.getLocation().getWorld().getName());
				config.set("SpawnSet.x", p.getLocation().getX());
				config.set("SpawnSet.y", p.getLocation().getY());
				config.set("SpawnSet.z", p.getLocation().getZ());
				config.set("SpawnSet.pitch", p.getLocation().getPitch());
				config.set("SpawnSet.yaw", p.getLocation().getYaw());
				
				try {
					config.save(set_spawn);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				p.sendMessage("§r[§8Hele§cCore§r] §aLe point de spawn a été défini sur votre position.");
				
			} else sender.sendMessage("§r[§8Hele§cCore§r] §cCette commande ne peut être exécutée que par un joueur !");
			
		}
		
		
		return false;
	}

}