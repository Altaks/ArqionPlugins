package fr.altaks.arqionpets.pets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import fr.altaks.arqionpets.Main;

public class ParrotPet implements EquipablePet {

	private Player player;
	private PetRarity rarity;
	private Main main;
	private boolean isEquiped = false;
	
	public ParrotPet(Main main, Player player, PetRarity rarity) {
		this.player = player;
		this.rarity = rarity;
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerDrinkPotionEvent(PlayerItemConsumeEvent event) {
		
		if(!event.getPlayer().equals(player)) return;
		if(this.isEquiped && event.getItem().getType() == Material.POTION) {
			
			PotionMeta potMeta = (PotionMeta) event.getItem().getItemMeta();
			
			for(PotionEffect effect : potMeta.getCustomEffects()) {
				event.getPlayer().removePotionEffect(effect.getType());
				switch (rarity) {
					case COMMUN:
						event.getPlayer().addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration() * 2, effect.getAmplifier()));
						break;
					case RARE:
						event.getPlayer().addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration() * 3, effect.getAmplifier()));
						break;
					case EPIC:
						event.getPlayer().addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration() * 5, effect.getAmplifier()));
						break;
					case LEGENDARY:
						event.getPlayer().addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration() * 10, effect.getAmplifier()));
						break;
				default:
					break;
				}
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerHandleCookie(PlayerItemHeldEvent event) {
		
		if(!event.getPlayer().equals(player)) return;
		if(event.getPlayer().getInventory().getItem(event.getNewSlot()).getType() == Material.COOKIE) {
			
			desequip();
			main.getHasPetEquiped().remove(player);
			
			// TODO : Faire perdre le pet
			
			
		}
		
	}
	
	@EventHandler
	public void onPlayerTakeHit(EntityDamageByEntityEvent event) {

		if(!event.getEntity().equals(player)) return;
		if(!(event.getDamager() instanceof LivingEntity)) return;
		
		LivingEntity attacker = (LivingEntity) event.getDamager();
		
		switch (this.rarity) {
			case RARE:
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
					attacker.damage(2);
				}, 20l);
				
				break;
			case EPIC:
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
					attacker.damage(3.5);
				}, 20l);
				
				
				break;
			case LEGENDARY:
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
					attacker.damage(5);
				}, 20l);
				
				
				break;

		default:
			break;
		}
		
	}
	
	@Override
	public void equip() {
		this.isEquiped = true;
		
		
	}
	
	@Override
	public void desequip() {
		this.isEquiped = false;
		
	}

	@Override
	public boolean isListener() {
		// TODO Auto-generated method stub
		return true;
	}

	

}
