package fr.altaks.arqionpets.pets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.altaks.arqionpets.Main;

public class SlimePet implements EquipablePet {

	private PetRarity rarity;
	private Player player;
	private boolean isEquiped = false;
	private Main main;
	private int taskId;
	
	public SlimePet(Main main, Player player, PetRarity rarity) {
		this.player = player;
		this.rarity = rarity;
		this.main = main;
	}
	
	@Override
	public void equip() {
		
		BukkitTask task = null;
		
		switch (rarity) {
			case COMMUN:
			case RARE:
				
				task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					
					if(!this.player.hasPotionEffect(PotionEffectType.JUMP)) {
						this.player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1_000_000, 1), isEquiped);
					}
					
				}, 0, 5*20l);
				
				break;
			case EPIC:
			case LEGENDARY:
				
				task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					
					if(!this.player.hasPotionEffect(PotionEffectType.JUMP)) {
						this.player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1_000_000, 2), isEquiped);
					}
					
				}, 0, 5*20l);
				
				break;

		default:
			break;
		}
		
		this.taskId = task.getTaskId();
		
		
	}

	@Override
	public void desequip() {
		
		isEquiped = false;
		Bukkit.getScheduler().cancelTask(this.taskId);
		
		if(this.player.hasPotionEffect(PotionEffectType.JUMP)) {
			this.player.removePotionEffect(PotionEffectType.JUMP);
		}
		
	}

	@Override
	public boolean isListener() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
