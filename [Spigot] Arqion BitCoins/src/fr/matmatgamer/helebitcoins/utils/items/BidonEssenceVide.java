package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class BidonEssenceVide {

	public String getName() {
		return "§6Bidon D'Essence §8*VIDE*";
	}
	
	public Material Matos() {
		return Material.BUCKET;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dRemplissez moi !",
				"§dOu je vous serais inutile.",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:BidonEssenceVide}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
