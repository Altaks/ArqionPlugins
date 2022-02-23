package fr.altaks.helesky.core.levelup;

import java.util.UUID;

public class LevelingWither {
	
	private int durability = 100;
	private UUID witherId;
	
	public LevelingWither(UUID witherId, int durability) {
		this.durability = durability;
		this.witherId = witherId;
	}
	
	public static LevelingWither fromString(String data) {
		String[] info = data.split("/");
		int durabilty = Integer.parseInt(info[0]);
		UUID uuid = UUID.fromString(info[1]);
		return new LevelingWither(uuid, durabilty);
	}
	
	@Override
	public String toString() {
		return "" + this.durability + "/" + this.witherId.toString();
	}

	public int getDurability() {
		return durability;
	}

	public UUID getWitherId() {
		return witherId;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

}
