package fr.altaks.heleshop.manager;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.altaks.heleshop.api.MoneyUtil;
import fr.altaks.heleshop.utils.ItemBuilder;

public class ShopItem {
	
	public static final ItemStack barrier = new ItemBuilder(Material.BARRIER, 1, (short)0, "§cQuitter").build(), glasspane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1, (short)0, " ").build(), returnArrow = new ItemBuilder(Material.ARROW,1, (short)0, "§cRetour en arrière").build();
	
	private ItemStack loredItem, realItem;
	private double sellingPrice, buyingPrice;
	
	public ShopItem(ItemStack item, double selling_price, double buying_price) {
		this.buyingPrice = buying_price;
		this.sellingPrice = selling_price;
		this.realItem = item;
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(
				Arrays.asList(
						"§r\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF",
						"§aPrix de vente : " + MoneyUtil.formatAmount(selling_price),
						"§cPrix d'achat   : " + MoneyUtil.formatAmount(buying_price),
						"§r\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF",
						"§7Pour acheter : Clic gauche (x1) | Maj. + Clic gauche (x64)",
						"§7Pour vendre  : Clic droit    (x1) | Maj. + Clic droit    (x64)",
						"§r\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF\u25CB\u25CF"
				)
		);
		item.setItemMeta(meta);
		this.loredItem = item;
	}

	public ItemStack getRealItem() {
		return realItem;
	}

	public ItemStack getLoredItem() {
		return loredItem;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public double getBuyingPrice() {
		return buyingPrice;
	}

}
