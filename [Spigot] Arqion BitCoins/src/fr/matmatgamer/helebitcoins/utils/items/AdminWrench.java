package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class AdminWrench {

	public String getName() {
		return "§6Admin Wrench";
	}
	
	public Material Matos() {
		return Material.BLAZE_ROD;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dCassez toutes les machines avec moi,",
				"§dN'aura jamais été aussi simple !",
				"",
				"            §4§l§nItem Admin",
				"§cêtre en créatif pour utiliser cet item.",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:AdminWrench}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
