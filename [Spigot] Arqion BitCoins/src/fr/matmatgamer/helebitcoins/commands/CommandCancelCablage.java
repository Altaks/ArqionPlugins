package fr.matmatgamer.helebitcoins.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.matmatgamer.helebitcoins.Main;

public class CommandCancelCablage implements CommandExecutor {

	private Main main;
	
	public CommandCancelCablage(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if((sender instanceof Player) && cmd.getName().equalsIgnoreCase("CancelCablage")) {
		
		Player player = (Player) sender;
		
		File file = new File(main.getDataFolder(), "Players/" + player.getUniqueId().toString() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if(configuration.get("CablageEnCour").equals("False")) {
			player.sendMessage(Main.prefix + "§cVous n'avez pas de cablage en cours");
			return false;
		}
		
		for (String str : new String[] {"CablageEnCour.loc", "CablageEnCour.type", "CablageenCour.machineNum"}) {
			configuration.set(str, null);
		}
		configuration.set("CablageEnCour", false);
		
		
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player.sendMessage(Main.prefix + "§cCablage annulé !");
		return true;
	}
		return false;
	}

}
