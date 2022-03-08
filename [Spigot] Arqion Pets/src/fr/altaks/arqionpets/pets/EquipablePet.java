package fr.altaks.arqionpets.pets;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface EquipablePet extends Listener {
	
	public abstract String getPetFileName();
		
	/**
	 * Mettre en place le BukkitRunnable correspondant au fonctionnement du pet
	 */
	public abstract void init();
	
	public abstract String getHeadName();
	
	public abstract boolean isListener();
	
	public abstract void enablePetForPlayer(Player player);
	
	public abstract void disablePetForPlayer(Player player);
	
	public abstract void addPetForPlayer(Player player, PetRarity rarity);
	
	public abstract void removePetForPlayer(Player player);
	
	public static enum PetRarity {
		
		COMMON("§2[Commun]", 2), 
		RARE("§9[Rare]", 3),
		EPIC("§5[Épique]", 5),
		LEGENDARY("§6[Légendaire]", 10);
		
		private String id, rarityLore;
		private int potionMultiplier;
		
		private PetRarity(String label, int potionMultiplier) {
			this.id = this.toString().toLowerCase();
			this.potionMultiplier = potionMultiplier;
			this.rarityLore = label;
		}
		
		public static PetRarity fromString(String value) {
			for(PetRarity rarity : PetRarity.values()) if(rarity.id.equals(value)) return rarity;
			return PetRarity.COMMON;
		}
		
		public static PetRarity fromRarityLore(String value) {
			for(PetRarity rarity : PetRarity.values()) if(rarity.rarityLore.equals(value)) return rarity;
			return PetRarity.COMMON;
		}
		
		public static List<String> getAllPossibleLores(){
			return Arrays.asList(COMMON.rarityLore, RARE.rarityLore, EPIC.rarityLore, LEGENDARY.rarityLore);
		}

		public String getId() {
			return id;
		}

		public String getRarityLore() {
			return rarityLore;
		}
		
		public int getPotionMultiplier() {
			return potionMultiplier;
		}
		
	}
}