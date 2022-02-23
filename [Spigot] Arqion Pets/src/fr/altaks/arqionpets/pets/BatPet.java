package fr.altaks.arqionpets.pets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.altaks.arqionpets.Main;

public class BatPet implements EquipablePet {

	private Player player;
	private Main main;
	private boolean hasEffect = false;
	private int taskId;
	private PetRarity rarity;
	
	public BatPet(Main main, Player player, PetRarity rarity) {
		this.player = player;
		this.main = main;
		this.rarity = rarity;
	}	
	
	@Override
	public void equip() {
		
		BukkitTask runnable = Bukkit.getServer().getScheduler().runTaskTimer(main, () -> {
			
			if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1_000_000, 1, false, false, true), true);
			}

			long playerTime = player.getPlayerTime() % 24000;
			switch (rarity) {
				
			
				case RARE:
					
					if(!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						if((playerTime < 450 || playerTime > 12541)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1_000_000, 2, false, false, true), true);
							hasEffect = true;
						}
					} else {
						if((playerTime > 450 && playerTime < 12541) && hasEffect) {
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							hasEffect = false;
						}
					}
					
					break;
				case EPIC:
					
					if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING) || !player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						if((playerTime < 450 || playerTime > 12541)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1_000_000, 1, false, false, true), true);
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1_000_000, 2, false, false, true), true);
							hasEffect = true;
						}
					} else {
						if((playerTime > 450 && playerTime < 12541) && hasEffect) {
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							hasEffect = false;
						}
					}
					
					break;
				case LEGENDARY:
					
					if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING) || !player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) || !player.hasPotionEffect(PotionEffectType.SPEED)) {
						if((playerTime < 450 || playerTime > 12541)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1_000_000, 1, false, false, true), true);
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1_000_000, 2, false, false, true), true);
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1_000_000, 1, false, false, true), true);
							hasEffect = true;
						}
					} else {
						if((playerTime > 450 && playerTime < 12541) && hasEffect) {
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							player.removePotionEffect(PotionEffectType.SPEED);
							hasEffect = false;
						}
					}
					
					break;
				default: break;
					
					
			}
			
		}, 0, 5*20l);
		
		taskId = runnable.getTaskId();
		
	}

	@Override
	public void desequip() {
		
		Bukkit.getScheduler().cancelTask(taskId);
		
		if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) 		player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		if(player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) 	player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		if(player.hasPotionEffect(PotionEffectType.SPEED)) 				player.removePotionEffect(PotionEffectType.SPEED);
		if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		
	}

	@Override
	public boolean isListener() {
		return false;
	}

}
