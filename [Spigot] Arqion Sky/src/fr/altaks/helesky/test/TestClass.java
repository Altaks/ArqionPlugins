package fr.altaks.helesky.test;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestClass implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Location loc = ((Player)sender).getLocation();
			loc.getWorld().regenerateChunk(loc.getChunk().getX(), loc.getChunk().getZ());
		}
		return false;
	}

}
