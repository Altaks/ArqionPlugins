package fr.nowayy.helecore.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class FurnaceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("furnace") && sender instanceof Player && args.length == 0) {
			Player user = (Player) sender;
			Inventory inv = Bukkit.createInventory(null, InventoryType.FURNACE);
			user.openInventory(inv);
			return true;
		}
		return false;
	}

}
