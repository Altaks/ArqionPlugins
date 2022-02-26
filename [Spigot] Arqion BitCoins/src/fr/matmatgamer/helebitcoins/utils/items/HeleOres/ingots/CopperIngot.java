package fr.matmatgamer.helebitcoins.utils.items.HeleOres.ingots;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class CopperIngot {

	public String getName() {
		return "§6Copper Ingot";
	}
	
	public Material Matos() {
		return Material.GOLD_INGOT;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerOres,
				"                §7Aluminium",
				"       §d§l->     §6Copper",
				"                 §5Cobalt",
				"                 §bArdium",
				"                  §9Helen",
				Main.ItemMarkerOres
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:CopperIngot}").setName(getName()).addEnchant(Enchantment.DURABILITY, 5).hideEnchants().setLore(getLore()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
