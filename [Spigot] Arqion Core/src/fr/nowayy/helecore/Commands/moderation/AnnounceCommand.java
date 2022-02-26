package fr.nowayy.helecore.Commands.moderation;

import java.util.Arrays;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.nowayy.helecore.Main;

public class AnnounceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("announce") && args.length > 0) {
			
			StringJoiner joiner = new StringJoiner(" ");
			Arrays.asList(args).forEach(part -> joiner.add(part));
			Bukkit.getOnlinePlayers().forEach(player ->{
				player.sendMessage(Main.Announce_Prefix +"§3"+ joiner.toString());
			});
			return true;
		}
		
		return false;
	}

	

}
