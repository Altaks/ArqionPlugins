package fr.nowayy.arqionbox.core.BoxItems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.util.ItemBuilder;

public class BoxItems {
	
	private static final Enchantment enchant = Enchantment.DURABILITY;
	
	public static final ItemStack 	spawnerBoxSymbol = new ItemBuilder(Material.SPAWNER, 1, "§8Spawner Box").addUnsafeEnchant(enchant, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									donatorBoxSymbol = new ItemBuilder(Material.BEACON, 1, "§5Donator Box").addUnsafeEnchant(enchant, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									emeraldBoxSymbol = new ItemBuilder(Material.EMERALD_BLOCK, 1, "§aEmerald Box").addUnsafeEnchant(enchant, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									diamondBoxSymbol = new ItemBuilder(Material.DIAMOND_BLOCK, 1, "§bDiamond Box").addUnsafeEnchant(enchant, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									goldBoxSymbol = new ItemBuilder(Material.GOLD_BLOCK, 1, "§6Golden Box").addUnsafeEnchant(enchant, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									ironBoxSymbol = new ItemBuilder(Material.IRON_BLOCK, 1, "§7Iron Box").addUnsafeEnchant(enchant, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
	
	public static final ItemStack 	donatorBoxKey = new ItemBuilder(Material.NETHER_STAR, 1, "§8Donator Key").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									spawnerBoxKey = new ItemBuilder(Material.PANDA_SPAWN_EGG, 1, "§5Spawner Key").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									emeraldBoxKey = new ItemBuilder(Material.EMERALD, 1, "§aEmerald Key").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									diamondBoxKey = new ItemBuilder(Material.DIAMOND, 1, "§bDiamond Key").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									goldBoxKey = new ItemBuilder(Material.GOLD_NUGGET, 1, "§6Golden Key").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
									ironBoxKey = new ItemBuilder(Material.IRON_NUGGET, 1, "§7Iron Key").addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
							
	public static final ItemStack invFiller = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, " ").build();
	

}