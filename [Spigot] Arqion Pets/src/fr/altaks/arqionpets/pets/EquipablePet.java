package fr.altaks.arqionpets.pets;

import org.bukkit.event.Listener;

public interface EquipablePet extends Listener {
	
	public abstract String getPetFileName();
	
	public abstract int getMenuSlot();
	
	/**
	 * Mettre en place le BukkitRunnable correspondant au fonctionnement du pet
	 */
	public abstract void init();
	
	public abstract String getHeadName();
	
	public abstract boolean isListener();
	
	public static enum PetRarity {
		
		COMMON("§2[Commun]"), 
		RARE("§9[Rare]"),
		EPIC("§5[Épique]"),
		LEGENDARY("§6[Légendaire]");
		
		private String id, label;
		
		private PetRarity(String label) {
			this.id = this.toString().toLowerCase();
			this.label = label;
		}
		
		public static PetRarity fromString(String value) {
			for(PetRarity rarity : PetRarity.values()) if(rarity.id.equals(value)) return rarity;
			return PetRarity.COMMON;
		}
		
		public static PetRarity fromLabel(String value) {
			for(PetRarity rarity : PetRarity.values()) if(rarity.label.equals(value)) return rarity;
			return PetRarity.COMMON;
		}

		public String getId() {
			return id;
		}

		public String getLabel() {
			return label;
		}
		
	}
}