package fr.nowayy.arqionbox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.util.TextComponentUtil;

public class ItemDataLineCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("itemdataline") && sender instanceof Player) {
			
			// récup l'item dans la main
			ItemStack item = ((Player)sender).getInventory().getItemInMainHand();
			
			// nom : material / amount / nbt 
			String material = item.getType().getKey().toString();
			String amount = String.valueOf(item.getAmount());
			String nbt = CraftItemStack.asNMSCopy(item).hasTag() ? CraftItemStack.asNMSCopy(item).getTag().toString() : "{}";
			
			
			TextComponentUtil util = new TextComponentUtil(material + "/" + amount + "/" + nbt);
			util.setClick_suggestCommand(material + "/" + amount + "/" + nbt);
			((Player)sender).spigot().sendMessage(util.build());
			
			return true;
			
		}
		
		return false;
	}

}
