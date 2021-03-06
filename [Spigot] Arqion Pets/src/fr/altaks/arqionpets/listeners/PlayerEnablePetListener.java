package fr.altaks.arqionpets.listeners;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.pets.EquipablePet;
import fr.altaks.arqionpets.pets.EquipablePet.PetRarity;

public class PlayerEnablePetListener implements Listener {
	
	private Main main;
	
	public PlayerEnablePetListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerEnablePet(PlayerInteractEvent event) {
		
		if(!event.hasItem()) return;
		if(event.getItem().getType() != Material.PLAYER_HEAD) return;
		
		// on récup la tete
		ItemStack head = event.getItem();
		// on boucle sur les entries de la map et des pets
		for(Entry<String, EquipablePet> mapEntry : main.getPets_from_name().entrySet()) {
			// si c'est un des pets alors:
				// on recup la rareté du pet en fonction du lore
				// on retire l'item de la main du joueur
				// on ajoute le pet pour le joueur
				// on prévient le joueur que le pet est disponible
			if(head.getItemMeta().getDisplayName().equals(mapEntry.getKey())) {
				
				PetRarity rarity = PetRarity.fromRarityLore(head.getItemMeta().getLore().get(0));
				
				EquipablePet pet = mapEntry.getValue();
				
				if(pet.playerHasPet(event.getPlayer().getUniqueId())) {
					if(!PetRarity.getAllUnderRarities(rarity).contains(pet.getRarityFromPlayerPet(event.getPlayer())) || rarity == PetRarity.COMMON) {
						// on dispose d'une rareté plus haute ou équivalente
						event.getPlayer().sendMessage(Main.PREFIX + "§cVous disposez déjà du pet " + pet.getHeadName() + "§c en rareté " + pet.getRarityFromPlayerPet(event.getPlayer()).getName());
						event.setCancelled(true);
						return;
					}
				}
				
				pet.addPetForPlayer(event.getPlayer(), rarity);
				event.getPlayer().sendMessage(Main.PREFIX + "§cVous disposez maintenant du pet " + pet.getHeadName() + "§6 en rareté " + rarity.getName());
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						
						if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {
							// on retire sur la main principale
							event.getPlayer().getInventory().setItemInMainHand(null);
						} else {
							// on retire sur la main secondaire
							event.getPlayer().getInventory().setItemInOffHand(null);
						}
						
					}
				}.runTaskLater(main, 1);
				
				return;
			}
		}
	}

}
