package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class PC {

	public String getName() {
		return "§6Ordinateur De Controle";
	}
	
	public Material Matos() {
		return Material.DISPENSER;
	}
	
	public int LimitePerPlayer() {
		return 1;
	}
	
	public String[] getLore( ) {
		
		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dGérez vos machines avec moi,",
				"§dGérez vos bitcoins avec moi,",
				"§dAvec moi, faites votre business !",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:PcBitcoins}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
