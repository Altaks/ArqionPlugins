package fr.nowayy.helecore.Commands.basic;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.helecore.Main;

public class KitCommand implements CommandExecutor {

	private File kits_file;
	private YamlConfiguration kits_yml;

	private Main main;
	public KitCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
			if (cmd.getName().equalsIgnoreCase("kit") && sender instanceof Player) {
				if (args.length == 0) {
					sender.sendMessage(Main.prefix + "§cMauvaise uilisation de la commande (/kit <kit souhaité>)!");
					return false;
				}
				
				this.kits_file = new File(main.getDataFolder() + "/Kits/kit_" + args[0].toString() + ".yml");
				this.kits_yml = YamlConfiguration.loadConfiguration(kits_file);
				
				if (!kits_file.exists()) {
						sender.sendMessage(Main.prefix + "§6Ce kit n'existe pas !");
						return false;
					}
				
				if (args.length == 1) {
					
					Player p = (Player) sender; 
					try {
						ItemStack[] kit = main.getKit(kits_yml);
						p.getInventory().addItem(kit);	
						kits_yml.save(kits_file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
			}
		}
		return false;
	}
	


}