package fr.altaks.helesky.core.islandcore;

import java.util.HashMap;

public enum IslandTier {
	
	Tier1(1, 125, 250_000),
	Tier2(2, 150, 750_000),
	Tier3(3, 175, 2_000_000),
	Tier4(4, 200, 5_000_000);
	
	private int tier;
	private int sizeAdding;
	private double price;
	
	private static HashMap<Integer, IslandTier> rawTier = new HashMap<Integer, IslandTier>();
	
	private IslandTier(int tier, int sizeAdding, double price) {
		this.tier = tier;
		this.sizeAdding = sizeAdding;
		this.price = price;
	}
	
	static {
		for(IslandTier istier : values()) {
			rawTier.put(istier.tier, istier);
		}
	}

	public int getTier() {
		return tier;
	}

	public int getSizeAdding() {
		return sizeAdding;
	}

	public double getPrice() {
		return price;
	}

	public HashMap<Integer, IslandTier> getRawTier() {
		return rawTier;
	}

}
