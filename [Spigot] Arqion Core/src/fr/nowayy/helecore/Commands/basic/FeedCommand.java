package fr.nowayy.helecore.Commands.basic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class FeedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("feed") && sender instanceof Player) {
			
			Player target = null;
			
			if (args.length == 1) {
				try {
					target = Bukkit.getPlayer(args[0]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound + "".replace("{@target}", args[0]));
					return true;
				}
			} else target = (Player) sender;
			
			if (target.getFoodLevel() != 20) {
				target.setFoodLevel(20);
				target.sendMessage(Main.prefix + Messages.Feed_Yourself);
				if (sender.getName() != target.getName()) {
					sender.sendMessage(Main.prefix + Messages.Feed_Other + "".replace("{@target}", target.getDisplayName()));
				}
			} else {
				sender.sendMessage(Main.Error_Prefix + Messages.Feed_ErrorFullFood + "".replace("{@target}", target.getDisplayName()));
			}
		}
		return false;
	}

}