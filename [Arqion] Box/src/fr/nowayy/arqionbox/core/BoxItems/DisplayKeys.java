package fr.nowayy.arqionbox.core.BoxItems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.arqionbox.api.MoneyUtil;
import fr.nowayy.arqionbox.core.BoxType;
import fr.nowayy.arqionbox.util.ItemBuilder;

public class DisplayKeys {
	
	public static final ItemStack 	displaydonatorBoxKey = new ItemBuilder(Material.NETHER_STAR, 1, "§8Donator Key")
											.addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS)
											.setLore("§r§6Prix : §e" + MoneyUtil.formatAmount(BoxType.DONATOR.getPrice())).build(),
									
									displayspawnerBoxKey = new ItemBuilder(Material.PANDA_SPAWN_EGG, 1, "§5Spawner Key")
											.addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS)
											.setLore("§r§6Prix : §e" + MoneyUtil.formatAmount(BoxType.SPAWNER.getPrice()), "").build(),
											
									displayemeraldBoxKey = new ItemBuilder(Material.EMERALD, 1, "§aEmerald Key")
											.setLore("§r§6Prix : §e" + MoneyUtil.formatAmount(BoxType.EMERALD.getPrice()), "")
											.addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
											
									displaydiamondBoxKey = new ItemBuilder(Material.DIAMOND, 1, "§bDiamond Key")
											.setLore("§r§6Prix : §e" + MoneyUtil.formatAmount(BoxType.DIAMOND.getPrice()), "")
											.addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
											
									displaygoldBoxKey = new ItemBuilder(Material.GOLD_NUGGET, 1, "§6Golden Key")
											.setLore("§r§6Prix : §e" + MoneyUtil.formatAmount(BoxType.GOLD.getPrice()), "")
											.addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
											
									displayironBoxKey = new ItemBuilder(Material.IRON_NUGGET, 1, "§7Iron Key")
											.setLore("§r§6Prix : §e" + MoneyUtil.formatAmount(BoxType.IRON.getPrice()), "")
											.addUnsafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();

}
