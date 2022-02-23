package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class Wires {

	public String getName() {
		return "§6Cables";
	}
	
	public Material Matos() {
		return Material.REDSTONE;
	}
	
	public String[] getLore( ) {

		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dCraftez vos machines avec moi,",
				"§dReliez vos machines avec moi",
				"§dFaites vivre vos machines avec moi",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:Wires}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ItemStack getItemX4() {
		try {
			return new ItemBuilder(Matos(), 4).setNBT("{ID:Wires}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
