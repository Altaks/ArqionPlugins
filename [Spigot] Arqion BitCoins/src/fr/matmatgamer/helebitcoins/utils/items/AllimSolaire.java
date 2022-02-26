package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class AllimSolaire {

	public String getName() {
		return "§6Alimentation Solaire";
	}
	
	public Material Matos() {
		return Material.DAYLIGHT_DETECTOR;
	}
	
	public int LimitePerPlayer() {
		return 10;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dLe jour, je suis utile.",
				"§dLa nuit, je suis inutile.",
				"§dCependant, je vous serais important.",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:AllimSolaire}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
