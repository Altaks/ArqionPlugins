package fr.matmatgamer.helebitcoins.utils.items.HeleOres.ingots;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class ArdiumIngot {

	public String getName() {
		return "§bArdium Ingot";
	}
	
	public Material Matos() {
		return Material.DIAMOND;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerOres,
				"               §7Aluminium",
				"                §6Copper",
				"                 §5Cobalt",
				"        §d§l->    §bArdium",
				"                 §9Helen",
				Main.ItemMarkerOres
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:ArdiumIngot}").setName(getName()).addEnchant(Enchantment.DURABILITY, 5).hideEnchants().setLore(getLore()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
