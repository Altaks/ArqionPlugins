package fr.altaks.arqionpets.task;

import org.bukkit.scheduler.BukkitRunnable;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.commands.SpawnPet.PetPlayerCouple;

public class PetLocationUpdateTask extends BukkitRunnable {

	private Main main;
	
	public PetLocationUpdateTask(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		
		if(main.getPetsArmorstand().size() < 1) return;
		
		PetPlayerCouple couple = main.getPetsArmorstand().getFirst();
		if(couple.getPlayer().isOnline() && main.getHasPetEquiped().contains(couple.getPlayer())) {
			couple.getStand().teleport(couple.getPlayer().getLocation().add(1.5d,  -0.4d,  1.5d));
			main.getPetsArmorstand().addLast(main.getPetsArmorstand().pollFirst());
		} else {
			couple.getStand().remove();
			main.getHasPetEquiped().remove(couple.getPlayer());
			main.getPetsArmorstand().remove(couple);
		}
			
	}

}
