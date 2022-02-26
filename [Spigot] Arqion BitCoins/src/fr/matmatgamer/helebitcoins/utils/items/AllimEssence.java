package fr.matmatgamer.helebitcoins.utils.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.ItemBuilder;


public class AllimEssence {

	public String getName() {
		return "§6Alimentation à pétrole";
	}
	
	public Material Matos() {
		return Material.BLAST_FURNACE;
	}
	
	public int LimitePerPlayer() {
		return 5;
	}
	
	public String[] getLore( ) {
		
		
		String[] lore = {
				Main.ItemMarkerBTC,
				"",
				"§dAlimentez moi,",
				"§dEt vous verrez votre production augmenter !",
				"",
				Main.ItemMarkerBTC
				};
		return lore;
	}
	
	public ItemStack getItem() {
		try {
			return new ItemBuilder(Matos()).setNBT("{ID:AllimEssence}").setName(getName()).setLore(getLore()).addEnchant(Enchantment.SILK_TOUCH, 5).hideEnchants().hideAttributes().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
