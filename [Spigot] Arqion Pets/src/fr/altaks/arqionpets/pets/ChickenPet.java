package fr.altaks.arqionpets.pets;

import org.bukkit.entity.Player;

import fr.altaks.arqionpets.Main;

public class ChickenPet implements EquipablePet {

	private Main main;
	private Player player;
	private int taskId;
	private boolean isEquiped = false;
	private PetRarity rarity;
	
	public ChickenPet(Main main, Player player, PetRarity rarity) {
		this.main = main;
		this.player = player;
		this.rarity = rarity;
	}
	
	@Override
	public void equip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desequip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isListener() {
		// TODO Auto-generated method stub
		return false;
	}

}
