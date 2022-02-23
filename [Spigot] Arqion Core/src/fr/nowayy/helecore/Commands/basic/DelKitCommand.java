package fr.nowayy.helecore.Commands.basic;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;

public class DelKitCommand implements CommandExecutor {

	private File kits_file;

	private Main main;
	public DelKitCommand(Main main) {
		this.main = main;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("delkit") || sender instanceof Player) {
			
			this.kits_file = new File(main.getDataFolder() + "/Kits/kit_" + args[0].toString() +".yml");
			
			if (kits_file.exists()) {
			
				kits_file.delete();
				sender.sendMessage(Main.prefix + "§aLe kit §5" + args[0].toString() + " §aa été supprimé.");
			
				return true;
			} else {
				sender.sendMessage(Main.Error_Prefix + "§cLe kit §5" + args[0].toString() + " §cn'existe pas !");
				return false;
			}
			
		}
		return false;
	}

}
