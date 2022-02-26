package fr.altaks.helemoney.commands;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.MoneyUtil;
import fr.altaks.helemoney.util.ItemBuilder;

public class Bank implements CommandExecutor {
	
	private ItemStack returnBarrier = new ItemBuilder(Material.BARRIER, 1, (short)0, "§cQuitter le menu").build(), glasspane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, (short)0, " ").build();
	private Main main;
	
	public Bank(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bank") && sender instanceof Player) {
			
			Player player = (Player)sender;
			try {
				player.openInventory(getBankUpgradeInv(player.getUniqueId()));
				return true;
			} catch (SQLException e) {
				sender.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue, veuillez prévenir le staff afin qu'il puisse régler ce problème au plus vite");
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	public Inventory getBankUpgradeInv(UUID id) throws SQLException {
		
		final ItemBuilder bank1 = new ItemBuilder(Material.GOLD_INGOT, 1, (short)0, "§aBanque tier 1")		.setLore("§eBanque de niveau 1","§6Prix : §b0§",		"§r§6Capacité Max: §b250k€").addItemFlag(ItemFlag.HIDE_ENCHANTS);
		final ItemBuilder bank2 = new ItemBuilder(Material.GOLD_BLOCK, 1, (short)0, "§aBanque tier 2")		.setLore("§eBanque de niveau 2","§6Prix : §b175k§",		"§r§6Capacité Max: §b250k€").addItemFlag(ItemFlag.HIDE_ENCHANTS);
		final ItemBuilder bank3 = new ItemBuilder(Material.DIAMOND, 1, (short)0, "§aBanque tier 3")			.setLore("§eBanque de niveau 3","§6Prix : §b500k§",		"§r§6Capacité Max: §b2M€").addItemFlag(ItemFlag.HIDE_ENCHANTS);
		final ItemBuilder bank4 = new ItemBuilder(Material.DIAMOND_BLOCK, 1, (short)0, "§aBanque tier 4")		.setLore("§eBanque de niveau 4","§6Prix : §b1M§",		"§r§6Capacité Max: §b4M€").addItemFlag(ItemFlag.HIDE_ENCHANTS);
		final ItemBuilder bank5 = new ItemBuilder(Material.EMERALD, 1, (short)0, "§aBanque tier 5")			.setLore("§eBanque de niveau 5","§6Prix : §b2.5M§",		"§r§6Capacité Max: §b9M€").addItemFlag(ItemFlag.HIDE_ENCHANTS);
		final ItemBuilder bank6 = new ItemBuilder(Material.EMERALD_BLOCK, 1, (short)0, "§aBanque tier 6")		.setLore("§eBanque de niveau 6","§6Prix : §b5.75M§",	"§r§6Capacité Max: §b15M€").addItemFlag(ItemFlag.HIDE_ENCHANTS);
		
		Inventory inv = Bukkit.createInventory(null, 6*9, "§cAmélioration de banque \u00BB");
		
		switch(main.getMoneyUtil().getBankTier(id)) {
			case 6:
				bank6.addEnchant(Enchantment.DURABILITY, 1);
			case 5:
				bank5.addEnchant(Enchantment.DURABILITY, 1);
			case 4:
				bank4.addEnchant(Enchantment.DURABILITY, 1);
			case 3:
				bank3.addEnchant(Enchantment.DURABILITY, 1);
			case 2:
				bank2.addEnchant(Enchantment.DURABILITY, 1);
			case 1:
				bank1.addEnchant(Enchantment.DURABILITY, 1);
				break;
			default: 
				break;
		}
		
		inv.setItem(13, bank1.build());
		inv.setItem(20, bank2.build());
		inv.setItem(24, bank3.build());
		inv.setItem(28, bank4.build());
		inv.setItem(34, bank5.build());
		inv.setItem(40, bank6.build());
		
		
		for(int slot = 0; slot < 54; slot++) {
			if(inv.getItem(slot) != null) {
				if(inv.getItem(slot).getType() == Material.AIR) inv.setItem(slot, glasspane);
			} else {
				inv.setItem(slot, glasspane);
			}
		}
		
		inv.setItem(53, returnBarrier);
		return inv;
	}

}
