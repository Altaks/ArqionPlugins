package fr.altaks.heleshop.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.altaks.heleshop.manager.ShopItem;
import fr.altaks.heleshop.utils.ItemBuilder;

public class Shop implements CommandExecutor {
	
	private final ItemStack 	tools = new ItemBuilder(Material.IRON_SWORD,1,(short)0,"§rArmes/Outils").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build(),
								food = new ItemBuilder(Material.APPLE, 1, (short)0, "§rNourritures").build(), 
								potions = new ItemBuilder(Material.BREWING_STAND, 1, (short)0, "§rPotions").build(),
								loots = new ItemBuilder(Material.BONE, 1, (short)0, "§rButins").build(),
								terrain_blocks = new ItemBuilder(Material.DIRT, 1, (short)0, "§rBlocs de terrain").build(),
								building_blocks = new ItemBuilder(Material.BRICKS, 1, (short)0, "§rBlocs de construction").build(),
								seeds = new ItemBuilder(Material.WHEAT_SEEDS, 1, (short)0, "§rGraines/Pousses").build();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("shop") && sender instanceof Player) {
			
			Player player = (Player)sender;
			
			Inventory inv = Bukkit.createInventory(null, 5*9, "§eBoutique \u00BB");
			
			inv.setItem(10, tools);
			inv.setItem(12, food);
			inv.setItem(14, potions);
			inv.setItem(16, loots);
			inv.setItem(29, terrain_blocks);
			inv.setItem(31, building_blocks);
			inv.setItem(33, seeds);
			
			inv.setItem(44, ShopItem.barrier);
			
			for(int slot = 0; slot < 45; slot++) if(inv.getItem(slot) != null) {
				if(inv.getItem(slot).getType() == Material.AIR) {
					inv.setItem(slot, ShopItem.glasspane);
				}
			} else inv.setItem(slot, ShopItem.glasspane);
			
			player.openInventory(inv);
			return true;
		}
		return false;
	}

}
