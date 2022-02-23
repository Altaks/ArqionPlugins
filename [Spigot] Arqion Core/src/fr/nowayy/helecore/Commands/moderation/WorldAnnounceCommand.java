package fr.nowayy.helecore.Commands.moderation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class WorldAnnounceCommand implements TabExecutor {

	@Override
	public  List<String> onTabComplete( CommandSender sender, Command cmd, String label,  String[] args) {
		
		List<String> list = new ArrayList<String>();
		if (args.length <= 1) {
			Bukkit.getWorlds().forEach(world -> {
				list.add(world.getName());
			});
			return list;
		}
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("worldannounce") && args.length >= 2) {
			
			String worldName = args[0];
			World world = null;
			
			try {
				world = Bukkit.getWorld(worldName);
			} catch (NullPointerException e) {
				sender.sendMessage(Main.Error_Prefix + Messages.Error_WorldDontExists.toString().replace("{@WorldName}", worldName));
				return true;
			}
			
			StringJoiner joiner = new StringJoiner(" ");
			Arrays.asList(args).forEach(part -> {
				if (part != args[0]) joiner.add(part);
			});
			
			world.getPlayers().forEach(player -> {
				player.sendMessage(Main.Announce_Prefix + " §9" + joiner.toString());
			});
			return true;
		}

		return false;
	}

	

}
