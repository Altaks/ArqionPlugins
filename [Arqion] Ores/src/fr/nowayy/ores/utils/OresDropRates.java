package fr.nowayy.ores.utils;

import org.bukkit.inventory.ItemStack;

import fr.nowayy.ores.utils.ItemManager.PrebuiltItems;

public enum OresDropRates {
	
	COAL(PrebuiltItems.coal, 100),
	IRON_NUGGET(PrebuiltItems.iron_nugget, 50),
	REDSTONE(PrebuiltItems.redstone, 45),
	LAPIS(PrebuiltItems.lapis, 40),
	GOLD_NUGGET(PrebuiltItems.gold_nugget, 37.5),
	QUARTZ(PrebuiltItems.quartz, 35),
	DIAMOND(PrebuiltItems.diamond, 30),
	EMERALD(PrebuiltItems.emerald, 25),
	ALUMINIUM_NUGGET(PrebuiltItems.aluminium_nugget, 20),
	COPPER_NUGGET(PrebuiltItems.copper_nugget, 15),
	COBALT_NUGGET(PrebuiltItems.cobalt_nugget, 10),
	PLATINIUM_NUGGET(PrebuiltItems.platinium_nugget, 5),
	ACTINIUM_NUGGET(PrebuiltItems.actinium_nugget, 2);
	
	
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

	private ItemStack material;
	private double dropRate;
	
	private OresDropRates(ItemStack material, double dropRate) {
		this.material = material;
		this.dropRate = dropRate;
	}
	
	public ItemStack getItem() {
		return this.material;
	}
	
	public double getDropRate() {
		return this.dropRate;
	}
	
	

}
