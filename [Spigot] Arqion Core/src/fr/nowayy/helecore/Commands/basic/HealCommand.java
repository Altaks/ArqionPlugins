package fr.nowayy.helecore.Commands.basic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("heal") && sender instanceof Player) {
			Player target = null;
			
			if (args.length == 1) {
				try {
					target = Bukkit.getPlayer(args[0]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
			} else target = (Player) sender;
			
			if (target.getHealth() != 20) {
				target.setHealth(20);
				target.sendMessage(Main.prefix + Messages.Heal_Yourself + "");
				if (sender.getName() != target.getName()) {
					sender.sendMessage(Main.prefix + Messages.Heal_Other + "".replace("{@target}", target.getDisplayName()));
				}
			} else {
				sender.sendMessage(Main.Error_Prefix + Messages.Heal_ErrorFullHealth + "".replace("{@target}", target.getDisplayName()));
			}
			return true;
			
		}
		return false;
	}

}