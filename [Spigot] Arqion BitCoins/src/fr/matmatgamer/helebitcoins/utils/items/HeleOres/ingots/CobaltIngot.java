package fr.matmatgamer.helebitcoins.utils.items.HeleOres.ingots;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class CobaltIngot {

	public String getName() {
		return "§5Cobalt Ingot";
	}
	
	public Material Matos() {
		return Material.PRISMARINE_SHARD;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerOres,
				"               §7Aluminium",
				"                §6Copper",
				"        §d§l->    §5Cobalt",
				"                §bArdium",
				"                 §9Helen",
				Main.ItemMarkerOres
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:CobaltIngot}").setName(getName()).addEnchant(Enchantment.DURABILITY, 5).hideEnchants().setLore(getLore()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
