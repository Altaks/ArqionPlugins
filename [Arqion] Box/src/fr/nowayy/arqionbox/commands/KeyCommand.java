package fr.nowayy.arqionbox.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.core.BoxItems.BoxItems;
import fr.nowayy.arqionbox.core.BoxItems.DisplayKeys;
import fr.nowayy.arqionbox.util.ItemBuilder;

public class KeyCommand implements CommandExecutor {
	
	private String delimiter = "§7---------- §bArqionbox §7----------";
	private Main main;
	
	public KeyCommand(Main main) {
		this.main = main;	
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("key") && sender instanceof Player) {
			
			Inventory inv = Bukkit.createInventory(null, 5 * 9, "�§ArqionBox - §cClés");
			
			Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
				
				for (int i = 0; i < inv.getSize(); i++) {
					if(inv.getItem(i) != null) {
						if(inv.getItem(i).getType() == Material.AIR) {
							inv.setItem(i, BoxItems.invFiller);
						}
					} else inv.setItem(i, BoxItems.invFiller);
				}
				
				ItemStack paper = new ItemBuilder(Material.PAPER, 1, "§Informations").setLore(delimiter, "","§cCes clés vous servent", "§ca ouvrir les box au /warp box","", delimiter).build();
				
				inv.setItem(10, DisplayKeys.displayironBoxKey);
				inv.setItem(16, DisplayKeys.displaygoldBoxKey);
				inv.setItem(20, DisplayKeys.displaydiamondBoxKey);
				inv.setItem(24, DisplayKeys.displayemeraldBoxKey);
				inv.setItem(30, DisplayKeys.displayspawnerBoxKey);
				inv.setItem(32, DisplayKeys.displaydonatorBoxKey);
				inv.setItem(13, paper);
				
			});
			
			((Player)sender).openInventory(inv);
			return true;
		}
		
		return false;
	}

}
