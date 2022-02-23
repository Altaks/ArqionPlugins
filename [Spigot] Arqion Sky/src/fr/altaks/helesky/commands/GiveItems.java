package fr.altaks.helesky.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helesky.core.CustomItems;
import fr.altaks.helesky.core.HeleCommand;
import fr.altaks.helesky.core.levelup.LevelingItems;

public class GiveItems implements HeleCommand {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("giveitems") && sender instanceof Player && sender.hasPermission("all")) {
			
			Player player = (Player) sender;
			player.getInventory().addItem(CustomItems.witherSpawnEgg);
			
			player.getInventory().addItem(LevelingItems.HELEN.getSymbol());
			return true;
		}
		return false;
	}

	@Override
	public String getCommand() {
		return "giveitems";
	}

	@Override
	public List<String> aliases() {
		return new ArrayList<>();
	}

}
