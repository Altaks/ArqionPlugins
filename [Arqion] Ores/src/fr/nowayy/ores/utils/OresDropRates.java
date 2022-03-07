package fr.nowayy.ores.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum OresDropRates {
	
	COAL(Material.COAL, 100),
	IRON_NUGGET(Material.IRON_NUGGET, 50),
	REDSTONE(Material.REDSTONE, 45),
	LAPIS(Material.LAPIS_LAZULI, 40),
	GOLD_NUGGET(Material.GOLD_NUGGET, 37.5),
	QUARTZ(Material.QUARTZ, 35),
	DIAMOND(Material.DIAMOND, 30),
	EMERALD(Material.EMERALD, 25),
	ALUMINIUM_NUGGET(),
	COPPER_NUGGET(),
	COBALT_NUGGET(),
	PlATINIUM_NUGGET(),
	ACTINIUM_NUGGET();
	
	
//	 drop rates:
//	 	coal : 100%
//		iron nugget : 50%
//		redstone : 45%
//		lapis : 40%
//	 	gold nugget : 37.5%
//		quartz : 35%
//		diamond : 30%
//		emerald : 25%
//		aluminium nugget : 20%
//		copper nugget : 15%
//	 	cobalt nugget : 10%
//	 	platinium nugget : 5%
//	 	actinium nugget : 2%

	private Material material;
	private double dropRate;
	
	public OresDropRates(Material material, double dropRate) {
		this.material = material;
		this.dropRate = dropRate;
	}
	
	public ItemStack getItem() {
		return this.material;
	}
	
	

}
