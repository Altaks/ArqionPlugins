package fr.nowayy.helecore.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;

public class CreateKitCommand implements CommandExecutor {
	
	private File kits_file;
	private YamlConfiguration kits_yml;
	
	private Main main;
	public CreateKitCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		this.kits_file = new File(main.getDataFolder() + "/Kits/kit_" + args[0].toString() + ".yml");
		this.kits_yml = YamlConfiguration.loadConfiguration(kits_file);
	
		if (cmd.getName().equalsIgnoreCase("createkit") && sender instanceof Player) {
			
			if (args.length == 0) sender.sendMessage(Main.prefix + "§cMauvaise utilisation de la commande ! (/createkit <nom du kit>)");
			
			Player p = (Player) sender;
			
			p.sendMessage(Main.prefix + "§aVous pouvez désormais créer le kit §5" + args[0].toString() + " §aPour confirmer la création du kit, faites §5/save " + args[0].toString());
			return true;
		
		
		} else if (cmd.getName().equalsIgnoreCase("save") && sender instanceof Player) {
			if (args.length == 0) sender.sendMessage(Main.prefix + "§cMauvaise utilisation de la commande ! (/save <nom du kit>)");
			
			if(kits_file.exists()) {
				sender.sendMessage(Main.prefix + "§4Ce kit existe déjà");
				return false;
			}
			
			Player p = (Player) sender;
			
			main.registerKit(p, kits_yml, kits_file);

			p.sendMessage(Main.prefix + "§cLe kit §5" + args[0] + "§c a été sauvegardé !");
			
			
			try {
				kits_yml.save(kits_file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
				
		}
		
		
		return false;
	}
	
}
