package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class Wrench {

	public String getName() {
		return "§6Wrench";
	}
	
	public Material Matos() {
		return Material.TRIPWIRE_HOOK;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dCassez vos machines avec moi,",
				"§dN'aura jamais été aussi simple !",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:Wrench}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
