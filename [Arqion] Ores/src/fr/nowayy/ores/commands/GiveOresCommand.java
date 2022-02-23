package fr.nowayy.ores.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.ores.utils.ItemManager;
import fr.nowayy.ores.utils.ItemManager.PrebuiltItems;
import fr.nowayy.ores.utils.Items;

public class GiveOresCommand implements TabExecutor {

	private static Items items = new Items();
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		
		
		new ItemManager.PrebuiltItems();
		if(cmd.getName().equalsIgnoreCase("giveitem") && sender instanceof Player) {
			
			switch(args[0]) {
			
			case "ores":
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.actinium_block).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.actinium_ingot).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.actinium_nugget).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.platinium_block).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.platinium_ingot).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.platinium_nugget).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.cobalt_block).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.cobalt_ingot).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.cobalt_nugget).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.copper_block).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.copper_ingot).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.copper_nugget).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.aluminium_block).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.aluminium_ingot).setAmount(64).build());
				((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(PrebuiltItems.aluminium_nugget).setAmount(64).build());
				break;
				
			case "items":
				for(ItemStack i : new ItemStack[] {items.hammerItem, items.rockBreaker, items.compressor,
														items.cobbleGen, items.cobbleGenT1, items.cobbleGenT2,
														items.cobbleGenT3, items.cobbleGenT4, items.cobbleGenT5,
														items.compressCobble, items.cable}) {
					((Player)sender).getInventory().addItem(new ItemManager.ItemBuilder(i).setAmount(64).build());
				}
				break;
				
			default:
				((Player)sender).sendMessage("adakor");
				break;
			}
		}
		
		return false;
	}

	

}
