package fr.nowayy.helecore.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class FlyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("fly") && sender instanceof Player) {
			
			Player target = null;
			
			if (args.length == 1) {
				try {
					target = Bukkit.getPlayer(args[0]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
			} else target =  (Player) sender;
			
			if (!target.getAllowFlight()) {
				target.setAllowFlight(true);
				target.sendMessage(Main.prefix + Messages.Fly_ChangeStateToOnYourself);
				if (target.getName() != sender.getName()) {
					sender.sendMessage(Main.prefix + Messages.Fly_ChangeStateToOnOther.toString().replace("{@target}", target.getDisplayName()));
				}
			} else {
				target.setAllowFlight(false);
				target.sendMessage(Main.prefix + Messages.Fly_ChangeStateToOffYourself.toString().replace("{@target}", target.getDisplayName()));
				if (target.getName() != sender.getName()) {
					sender.sendMessage(Main.prefix + Messages.Fly_ChangeStateToOffOther);
				}
			}
			return true;
		}
		return false;
	}
}