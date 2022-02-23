package fr.altaks.arqionpets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.altaks.arqionpets.utils.ItemManager;

public class PluginItems {
	
	public static class CrosspluginItems {
		
		public static final ItemStack
		
		        aluminium_block =  	new ItemManager.ItemBuilder(Material.IRON_BLOCK, 1, "§8Bloc d'aluminium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                aluminium_ingot =  	new ItemManager.ItemBuilder(Material.IRON_INGOT, 1, "§8Lingot d'aluminium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                aluminium_nugget = 	new ItemManager.ItemBuilder(Material.IRON_NUGGET, 1, "§8Pépite d'aluminium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                
                copper_block = 		new ItemManager.ItemBuilder(Material.GOLD_BLOCK, 1, "§6Bloc de cuivre").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                copper_ingot = 		new ItemManager.ItemBuilder(Material.GOLD_INGOT, 1, "§6Lingot de cuivre").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                copper_nugget = 	new ItemManager.ItemBuilder(Material.GOLD_NUGGET, 1, "§6Pépite de cuivre").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                
                cobalt_block = 		new ItemManager.ItemBuilder(Material.PRISMARINE_BRICKS, 1, "§1Bloc de cobalt").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                cobalt_ingot = 		new ItemManager.ItemBuilder(Material.PRISMARINE_SHARD, 1, "§1Crystal de cobalt").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                cobalt_nugget = 	new ItemManager.ItemBuilder(Material.PRISMARINE_CRYSTALS, 1, "§1Morceaux de cobalt").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                
                platinium_block = 	new ItemManager.ItemBuilder(Material.DIAMOND_BLOCK, 1, "§bBloc de platine").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                platinium_ingot = 	new ItemManager.ItemBuilder(Material.DIAMOND, 1, "§bLingot de platine").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                platinium_nugget = 	new ItemManager.ItemBuilder(Material.BLUE_DYE, 1, "§bPoudre de platine").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                
                actinium_block = 	new ItemManager.ItemBuilder(Material.BEACON, 1, "§cBloc d'actinium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                actinium_ingot = 	new ItemManager.ItemBuilder(Material.NETHER_STAR, 1, "§cLingot d'actinium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
                actinium_nugget = 	new ItemManager.ItemBuilder(Material.GHAST_TEAR, 1, "§cPépite d'actinium").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
		
	}
	
	public static final ItemStack 
	
	cable = 				new ItemManager.ItemBuilder(Material.LEAD, 1, "§eCable").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
	wither_pet_converter = 	new ItemManager.ItemBuilder(Material.FIREWORK_STAR,1,"§eWither Pet Converter").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
	processor = 			new ItemManager.SkullBuilder(1, "§cProcesseur").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI0NWExYzNlOGQ3YzNkNTlkMDVlMzYzNGIwNGFmNGNiZjhkMTFiNzBlMmE0MGUyZTYzNjQzODZkYjQ5ZTczNyJ9fX0=").setLore("§r§oBip boup","§r§oBloup bioup").build(),
	alimentation = 			new ItemManager.SkullBuilder(1, "§cAlimentation").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2E3Y2RhOTAwNGZjMTk3ZDY2YWZiYzJiMDAzYTViOWVmMTNjZjQ2MDBiMWZjNzQ5MDA2NzU5MGYwNDcxODFlIn19fQ==").setLore("§r§oInfinite poweeeeeerrrr").build(),
	pet_infuser = 			new ItemManager.SkullBuilder(1, "§5Pet Infuser").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQxMjY1ZDU3ZDM3MDZjMGI0MjhmMWIyNDMwNDRkNTBkODQxNDkyNmFjYjM3NTJiOGNiOWY1Yjg1YmNkZDA5NiJ9fX0=").setLore("§r§oUn petit thé ?","§r§oAh non ça bouge ça !","§r§oC'est un animal ?","§r§oJe peux le garder ??").build(),

	
	pet_core = 				new ItemManager.ItemBuilder(Material.HEART_OF_THE_SEA, 1, "§5Cœur de pet").build(),
	pet_dust = 				new ItemManager.ItemBuilder(Material.GUNPOWDER, 1, "§ePoudre de pet").build(), 
	
	flying_ring = 			new ItemManager.ItemBuilder(Material.ELYTRA, 1, "§eTalisman de vol").build(), 
	fall_ring = 			new ItemManager.ItemBuilder(Material.GUNPOWDER, 1, "§eTalisman de chute").build(),
	
	enchanted_slime_block = new ItemManager.ItemBuilder(Material.SLIME_BLOCK, 1, "§eBlock de slime compressé").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(),
	enchanted_feather = 	new ItemManager.ItemBuilder(Material.FEATHER, 1, "§ePlume compressée").addNotSafeEnchant(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build(), 
	
	compressed_pet_dust = 	new ItemManager.ItemBuilder(Material.GUNPOWDER, 1, "§ePoudre de pet compressée").build(), 
	animal_fat = 			new ItemManager.ItemBuilder(Material.HONEY_BOTTLE, 1, "§eGraisse animale").build(), 
	
	
	bat_pet = 				new ItemManager.SkullBuilder(1, "§eI'm Batman").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyMGExMGRiMjIyZjY5YWMyMjE1ZDdkMTBkY2E0N2VlYWZhMjE1NTUzNzY0YTJiODFiYWZkNDc5ZTc5MzNkMSJ9fX0=").build(),
	silverfish_pet = 		new ItemManager.SkullBuilder(1, "§ePoisson d'argent").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==").build(),
	parrot_pet = 			new ItemManager.SkullBuilder(1, "§ePerroquet").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjBiZmE4NTBmNWRlNGIyOTgxY2NlNzhmNTJmYzJjYzdjZDdiNWM2MmNhZWZlZGRlYjljZjMxMWU4M2Q5MDk3In19fQ==").build(),
	phantom_pet = 			new ItemManager.SkullBuilder(1, "§ePhantom").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzZhMTkwYTUyMzczZjNiYTQwMGM3N2U5YzcxOTQxNWRmMDhmOTRiNDRkNTJmMzM5NGFmNGIxNDdkNDQ1OGEzYSJ9fX0=").build(),
	slime_pet = 			new ItemManager.SkullBuilder(1, "§eSlime").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1YWVlYzZiODQyYWRhODY2OWY4NDZkNjViYzQ5NzYyNTk3ODI0YWI5NDRmMjJmNDViZjNiYmI5NDFhYmU2YyJ9fX0=").build(),
	ender_drag_pet = 		new ItemManager.SkullBuilder(1, "§eDragounet").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZjZGFlNTg2YjUyNDAzYjkyYjE4NTdlZTQzMzFiYWM2MzZhZjA4YmFiOTJiYTU3NTBhNTRhODMzMzFhNjM1MyJ9fX0=").build(),
	pig_pet = 				new ItemManager.SkullBuilder(1, "§ePeppa pig").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTgzZWIwNzMxM2MwZDJlZjcyNmMyNjdmMmIxMjg1ZmRhYmQwMjg2NDYwMzEwMjNmYWExYmQ0YzFlNmYwMWRmOCJ9fX0=").build(),
	chicken_pet = 			new ItemManager.SkullBuilder(1, "§eCot Cot").setBase64Texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZmZWEyMGY4NTI1MDFkYzJkYzk4ODc3YjhkODY5ODBiZDE2YTliY2I2ZGYzNTgzYjNhMmIzMjU0YTgzNWY1YiJ9fX0=").build();

	
}
