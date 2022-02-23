package fr.nowayy.helecore.Commands.moderation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class EnchantCommand implements TabExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("enchant") && args.length == 1 && sender instanceof Player) {
			List<String> propositions = new ArrayList<String>();
	        for(Enchantment ench : Enchantment.values()) {
	            propositions.add(ench.getName().toLowerCase()
	            		.replace("damage_all", "sharpness")
	            		.replace("dig_speed", "efficiency")
	            		.replace("damage_arthropods", "smite")
	            		.replace("loot_bonus_blocks", "fortune")
	            		.replace("loot_bonus_mobs", "looting")
	            		.replace("arrow_knockback", "punch")
	            		.replace("arrow_fire", "flame")
	            		.replace("arrow_infinite", "infinity")
	            		.replace("durability", "unbreaking")
	            		.replace("protection_environmental", "protection")
	            		.replace("protection_explosions", "blast_protection")
	            		.replace("protection_fall", "feather_falling")
	            		.replace("protection_fire", "fire_protection")
	            		.replace("arrow_damage", "power"));
	        }
	        return propositions;
		}
		return null;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("enchant") && sender instanceof Player) {
			if (args.length == 2) {
				Player user = (Player) sender;
				int level = Integer.parseInt(args[1]);
				Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(args[0].toLowerCase()));
				try {
					ItemStack target = user.getInventory().getItemInMainHand();
					target.addUnsafeEnchantment(ench, level);
				} catch(Exception e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_EmptyHandToEnchant);
					return true;
				}
				return true;
				
				
			} else return true; 
			
			
			
			
		}
		return false;
	}

	

}
