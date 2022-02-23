package fr.nowayy.helecore.Commands.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class GamemodeCommand implements TabExecutor {
	
	@SuppressWarnings("deprecation")
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
			if(cmd.getName().equalsIgnoreCase("gamemode") && args.length <= 1) {
				Arrays.asList(GameMode.values()).forEach(gm -> { 
					list.add(String.valueOf(gm.getValue()));
					list.add(gm.name().toLowerCase());
				});
				return list;
			}		
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("gameMode")) {
			
			Player target = null;
			
			if (args.length > 1) {
				
				try{
					target = Bukkit.getPlayer(args[1]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
				
			} else if (sender instanceof Player && args.length < 2) {
				target = (Player) sender;
			} else {
				sender.sendMessage(Main.Error_Prefix + "");
			}
			
			if (target == null || args.length < 1) return false;
			String sgm = args[0];
			
			GameMode chosen_gM = GameMode.SURVIVAL;
			
			for (GameMode gm : GameMode.values()) {
				if (sgm.equalsIgnoreCase(gm.name()) || sgm.equalsIgnoreCase(String.valueOf(gm.getValue()))) {
					chosen_gM = gm;
					break;
				}
			}
			
			target.setGameMode(chosen_gM);
			if (((Player) sender).equals(target)) {
				
			} else {
				sender.sendMessage(!((Player) sender).equals(target)
								? Main.prefix + "Vous êtes maintenant en GameMode" + chosen_gM.toString().toLowerCase()
								: Main.prefix + "Le mode de jeu de " + target.getDisplayName() + "est maintenant : " + chosen_gM.toString().toLowerCase());
			}
			return true;
			
		}
		
		return false;
	}

}