package fr.nowayy.arqionbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxItems.BoxItems;

public class BoxCommand implements CommandExecutor {

	private Main main;
	
	public BoxCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("box") && sender instanceof Player) {
			
			Inventory inv = Bukkit.createInventory(null, 5 * 9, "§cArqionBox - §cBox");
			
			Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
				
				for (int i = 0; i < inv.getSize(); i++) {
					if(inv.getItem(i) != null) {
						if(inv.getItem(i).getType() == Material.AIR) {
							inv.setItem(i, BoxItems.invFiller);
						}
					} else inv.setItem(i, BoxItems.invFiller);
				}
				
				
				inv.setItem(20, BoxItems.ironBoxSymbol);
				inv.setItem(24, BoxItems.goldBoxSymbol);
				inv.setItem(30, BoxItems.diamondBoxSymbol);
				inv.setItem(32, BoxItems.emeraldBoxSymbol);
				inv.setItem(12, BoxItems.spawnerBoxSymbol);
				inv.setItem(14, BoxItems.donatorBoxSymbol);
				
			});
			
			((Player)sender).openInventory(inv);
			return true;
			
		}
		
		return false;
	}

}
