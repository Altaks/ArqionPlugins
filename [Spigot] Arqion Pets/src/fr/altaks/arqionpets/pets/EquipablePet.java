package fr.altaks.arqionpets.pets;

import org.bukkit.event.Listener;

public interface EquipablePet extends Listener {
	
	abstract void equip();
	
	
	abstract void desequip();

	abstract boolean isListener();
	
	static enum PetRarity {
		
		COMMUN("§2[Commun]"), 
		RARE("§9[Rare]"), 
		EPIC("§5[Épique]"), 
		LEGENDARY("§6[Légendaire]");
		
		private String label;
		
		private PetRarity(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
		
	}
	
}