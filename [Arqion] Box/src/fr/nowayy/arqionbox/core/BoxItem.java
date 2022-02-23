package fr.nowayy.arqionbox.core;

import org.bukkit.inventory.ItemStack;

public class BoxItem {
	
	private ItemStack item;
	private float dropRate;
	
	public BoxItem(ItemStack item, float dropRate) {
		this.item = item;
		this.dropRate = dropRate;
	}

	public ItemStack getItem() {
		return this.item;
	}
	
	public float getDropRate() {
		return this.dropRate;
	}
	
	public void setDropRate(float dropRate) {
		this.dropRate = dropRate;
	}
}
