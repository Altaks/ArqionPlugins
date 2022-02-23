package fr.nowayy.helecore.Commands.basic;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.nowayy.helecore.Main;

public class SpawnCommand implements CommandExecutor {
	private Plugin pl = Main.getPlugin(Main.class);

	public SpawnCommand(Main main) {
		this.pl = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					Player p = (Player) sender;
					
					File set_spawn = new File(pl.getDataFolder(), "setSpawn.yml");
					YamlConfiguration config = YamlConfiguration.loadConfiguration(set_spawn);
					ConfigurationSection spawn_configSection = config.getConfigurationSection("SpawnSet.");
					
					World world = Bukkit.getWorld(spawn_configSection.getString("world"));
					double x = spawn_configSection.getDouble("x");
					double y = spawn_configSection.getDouble("y");
					double z = spawn_configSection.getDouble("z");
					float pitch = (float) spawn_configSection.getDouble("pitch");
					float yaw = (float) spawn_configSection.getDouble("yaw");
					
					Location spawn = new Location(world, x, y, z, pitch, yaw);
					p.teleport(spawn);
					p.sendMessage("§r[§8Hele§cCore§r] §aVous avez été téléporté au spawn du serveur !");
				} else sender.sendMessage("§r[§8Hele§cCore§r] §cAfin de vous téléporter au spawn, exétutez la commande '/spawn'");
				

			}
			
			
			return true;
		}
		return false;
	}
	
	

}
