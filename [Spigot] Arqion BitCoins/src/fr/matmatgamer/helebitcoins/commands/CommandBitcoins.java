package fr.matmatgamer.helebitcoins.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.matmatgamer.helebitcoins.Main;

public class CommandBitcoins implements CommandExecutor {

	private Main main;
	
	public CommandBitcoins(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId() + ".yml");
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			player.sendMessage(Main.prefix + " §6Vous poss�dez actuellement §b" + configuration.get("Bitcoins") + "§6 BitCoins sur votre compte.");
		}
		
		return false;
	}

}
