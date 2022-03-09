package fr.altaks.arqionpets.pets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
	
	public abstract boolean playerHasPet(UUID id);
	
	public abstract PetRarity getRarityFromPlayerPet(Player player);
	
	public abstract void enablePetForPlayer(Player player);
	
	public abstract void disablePetForPlayer(Player player);
	
	public abstract void addPetForPlayer(Player player, PetRarity rarity);
	
	public abstract void removePetForPlayer(Player player);
	
	public static enum PetRarity {
		
		COMMON("§2[Commun]", 2, "Commun", 0), 
		RARE("§9[Rare]", 3, "Rare", 15*60),
		EPIC("§5[Épique]", 5, "Épique", 20*60),
		LEGENDARY("§6[Légendaire]", 10, "Légendaire", 25*60);
		
		private String id, rarityLore, name;
		private int potionMultiplier, antiAfkDelay;
		
		private PetRarity(String label, int potionMultiplier, String name, int antiAfkDelay) {
			this.id = this.toString().toLowerCase();
			this.potionMultiplier = potionMultiplier;
			this.rarityLore = label;
			this.name = name;
			this.antiAfkDelay = antiAfkDelay;
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
		
		public static List<PetRarity> getAllUnderRarities(PetRarity rarity){
			switch (rarity) {
			
				case LEGENDARY: return Arrays.asList(PetRarity.EPIC, PetRarity.RARE, PetRarity.COMMON);
				case EPIC: return Arrays.asList(PetRarity.RARE, PetRarity.COMMON);
				case RARE: return Arrays.asList(PetRarity.COMMON);
				
				default: return new ArrayList<PetRarity>();
			}
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

		public String getName() {
			return name;
		}

		public int getAntiAfkDelay() {
			return antiAfkDelay;
		}
		
	}
}