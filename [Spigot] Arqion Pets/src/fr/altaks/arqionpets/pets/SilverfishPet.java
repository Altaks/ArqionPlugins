package fr.altaks.arqionpets.pets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.altaks.arqionpets.Main;

public class SilverfishPet implements EquipablePet {

	private Player player;
	private PetRarity rarity;
	private int taskId;
	private Main main;
	
	public SilverfishPet(Main main, Player player, PetRarity rarity) {
		this.player = player;
		this.rarity = rarity;
		this.main = main;
	}
	
	@Override
	public void equip() {

		BukkitTask runnable = Bukkit.getServer().getScheduler().runTaskTimer(main, () -> {
		
			switch (rarity) {
				case COMMUN:
				case RARE:
					
					if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1_000_000, 1, false, false), false);
					}
					
					break;
				case EPIC:
					
					if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1_000_000, 2, false, false), false);
					}
					
					if(!player.hasPotionEffect(PotionEffectType.SPEED)) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1_000_000, 1, false, false), false);
					}
					
					break;
				case LEGENDARY:
					
					if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1_000_000, 3, false, false), false);
					}
					
					if(!player.hasPotionEffect(PotionEffectType.SPEED)) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1_000_000, 2, false, false), false);
					}
					
					break;

			default:
				break;
			}
			
		}, 0, 5*20l);
		
		taskId = runnable.getTaskId();
		
	}

	@Override
	public void desequip() {

		Bukkit.getScheduler().cancelTask(taskId);
		
		switch (rarity) {
			case LEGENDARY:
			case EPIC:
				if(player.hasPotionEffect(PotionEffectType.SPEED)) player.removePotionEffect(PotionEffectType.SPEED);
			case COMMUN:
			case RARE:
				if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				break;
		default:
			break;
		}
		
	}

	@Override
	public boolean isListener() {
		// TODO Auto-generated method stub
		return false;
	}

}
