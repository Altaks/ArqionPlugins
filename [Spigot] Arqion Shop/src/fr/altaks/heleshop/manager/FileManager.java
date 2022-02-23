package fr.altaks.heleshop.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.v1_15_R1.MojangsonParser;

public class FileManager {
	
	/*
	 * itemid:
	 *   item:
	 *     material:
	 *     amount:
	 *     nbt:
	 *   selling-price:
	 *   buying-price:
	 *   menu: 
	 */
	
	public static HashMap<ShopMenu, List<ShopItem>> read(FileConfiguration yml) throws CommandSyntaxException, Exception {
		HashMap<ShopMenu, List<ShopItem>> menus = new HashMap<ShopMenu, List<ShopItem>>();
		for(ShopMenu menu : ShopMenu.values()) menus.put(menu, new ArrayList<ShopItem>());
		
		
		for(String itempath : yml.getKeys(false)) {
			// pour chaque objet
			
			String type = yml.getString(itempath+".item.material");
			int amount = yml.getInt(itempath+".item.amount");
			String nbt = yml.getString(itempath + ".item.nbt");
			
			Material material = Material.AIR;
			for(Material mat : Material.values()) {
				if(mat.getKey().toString().equalsIgnoreCase(type)) {
					material = mat;
				}
			}
			
			ItemStack item = new ItemStack(material, amount);
			
			if(!(nbt.equalsIgnoreCase("") || nbt.equalsIgnoreCase("{}"))) {
				
				net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
				nmsItem.setTag(MojangsonParser.parse(nbt));
				item = CraftItemStack.asBukkitCopy(nmsItem);
				
			}
			
			double selling_price = yml.getDouble(itempath+".selling-price"), buying_price = yml.getDouble(itempath+".buying-price");
			
			String shopMenuStr = yml.getString(itempath+".menu");
			menus.get(ShopMenu.getFromId(shopMenuStr)).add(new ShopItem(item, selling_price, buying_price));
		}
		
		return menus;
	}

}
