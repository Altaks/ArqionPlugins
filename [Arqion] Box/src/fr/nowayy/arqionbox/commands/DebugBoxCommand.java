package fr.nowayy.arqionbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nowayy.arqionbox.core.BoxItems.BoxItems;

public class DebugBoxCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("debugbox")){
			if(sender.isOp()) {
				((Player) sender).getInventory().addItem(BoxItems.spawnerBoxKey, BoxItems.ironBoxKey, BoxItems.diamondBoxKey, BoxItems.donatorBoxKey, BoxItems.emeraldBoxKey, BoxItems.goldBoxKey);
				return true;
			}
		}
		return false;
	}

}
