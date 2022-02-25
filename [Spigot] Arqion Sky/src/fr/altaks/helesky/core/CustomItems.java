package fr.altaks.helesky.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.altaks.helesky.utils.ItemManager;
import fr.altaks.helesky.utils.LoreUtil;

public class CustomItems {
	
	public static final ItemStack witherSpawnEgg = new ItemManager.ItemBuilder(Material.WITHER_SKELETON_SPAWN_EGG, 1, "§dWither d'amélioration").setLore(LoreUtil.getBaseLevelingWitherData()).build();
	

}
