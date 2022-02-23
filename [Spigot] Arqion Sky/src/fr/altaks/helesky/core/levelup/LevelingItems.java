package fr.altaks.helesky.core.levelup;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.altaks.helesky.utils.ItemManager;

public enum LevelingItems {
	
	EMERALD(275, SymbolItems.Emerald),
	ALUMINIUM(285, SymbolItems.Aluminium),
	COPPER(310, SymbolItems.Copper),
	COBALT(325, SymbolItems.Cobalt),
	ARDIUM(350, SymbolItems.Ardium),
	HELEN(400, SymbolItems.Helen),

	COAL(20, SymbolItems.Coal),
	LAPIS(100, SymbolItems.Lapis),
	REDSTONE(100, SymbolItems.Redstone),
	QUARTZ(150, SymbolItems.Quartz),
	IRON(185, SymbolItems.Iron),
	GOLD(225, SymbolItems.Gold),
	DIAMOND(260, SymbolItems.Diamond);
	
	private int xp;
	private ItemStack symbol;
	
	private LevelingItems(int xp, ItemStack symbol) {
		this.xp = xp;
		this.symbol = symbol;
	}

	public int getXp() {
		return xp;
	}

	public ItemStack getSymbol() {
		return symbol;
	}
	
	public static LevelingItems matchesItem(ItemStack itemToMatch) throws NullPointerException {
		
		for(LevelingItems item : values()) {
			if(item.getSymbol().getType() == itemToMatch.getType()) {
				if(SymbolItems.customItems.contains(item.getSymbol())) {
					// item custom
					// check si le nom est bon 
					    // renvoyer l'item
					    // sinon continue
					if(itemToMatch.getItemMeta().getDisplayName().equals(item.getSymbol().getItemMeta().getDisplayName())) {
						return item;
					} else continue;
				} else if(SymbolItems.normalItems.contains(item.getSymbol())){
					// item normal, pas de check du type
					return item;
				}
			}
		}
		
		return null;
		
	}

}

class SymbolItems {
	
	public static final ItemStack 
						Aluminium = new ItemManager.ItemBuilder(Material.IRON_INGOT, 1,  "ß7Aluminium").build(),
						Copper = new ItemManager.ItemBuilder(Material.GOLD_INGOT, 1,  "ß6Cuivre").build(),
						Cobalt = new ItemManager.ItemBuilder(Material.LAPIS_LAZULI, 1,  "ß9Cobalt").build(),
						Ardium = new ItemManager.ItemBuilder(Material.GOLD_INGOT, 1,  "ßcArdium").build(),
						Helen = new ItemManager.ItemBuilder(Material.REDSTONE, 1,  "ß5Helen").build();
	
	public static final ItemStack 
						Coal = new ItemManager.ItemBuilder(Material.COAL, 1,  "ßrCharbon").build(),
						Lapis = new ItemManager.ItemBuilder(Material.LAPIS_LAZULI, 1,  "ßrLapis-lazuli").build(),
						Redstone = new ItemManager.ItemBuilder(Material.REDSTONE, 1,  "ßrRedstone").build(),
						Quartz = new ItemManager.ItemBuilder(Material.QUARTZ, 1,  "ßrQuartz").build(),
						Iron = new ItemManager.ItemBuilder(Material.IRON_INGOT, 1,  "ßrFer").build(),
						Gold = new ItemManager.ItemBuilder(Material.GOLD_INGOT, 1,  "ßrOr").build(),
						Diamond = new ItemManager.ItemBuilder(Material.DIAMOND, 1,  "ßrDiamant").build(),
						Emerald = new ItemManager.ItemBuilder(Material.EMERALD, 1,  "ßr…meraude").build();
	
	public static List<ItemStack> customItems = Arrays.asList(Aluminium, Copper, Cobalt, Ardium, Helen), normalItems = Arrays.asList(Coal, Lapis, Redstone, Quartz, Iron, Gold, Diamond, Emerald);

	
}
