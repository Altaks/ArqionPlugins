package fr.altaks.arqionpets.listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.commands.SpawnPet.PetPlayerCouple;
import fr.altaks.arqionpets.pets.BatPet;
import fr.altaks.arqionpets.pets.ChickenPet;
import fr.altaks.arqionpets.pets.EdragPet;
import fr.altaks.arqionpets.pets.EquipablePet;
import fr.altaks.arqionpets.pets.EquipablePet.PetRarity;
import fr.altaks.arqionpets.pets.ParrotPet;
import fr.altaks.arqionpets.pets.PhantomPet;
import fr.altaks.arqionpets.pets.PigPet;
import fr.altaks.arqionpets.pets.SilverfishPet;
import fr.altaks.arqionpets.pets.SlimePet;
import fr.altaks.arqionpets.utils.ItemManager;

public class PetMenuInteractionListener implements Listener {
	
	private Main main;
	
	public PetMenuInteractionListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerInteractMenu(InventoryClickEvent event) {
		
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		if(!event.getClickedInventory().equals(event.getView().getTopInventory())) return;
		if(!event.getView().getTitle().equalsIgnoreCase("§8Pets \u00BB")) return;
		if(event.getCurrentItem() == null) return;
		if(ItemManager.PrebuiltItems.inventoryFillingGlassPane.isSimilar(event.getCurrentItem())) {
			event.setCancelled(true);
			return;
		}
		
		String petname = event.getCurrentItem().getItemMeta().getDisplayName();
		
		Player player = (Player)event.getWhoClicked();
		event.setCancelled(true);
		
		for(ItemStack item : Arrays.asList(PluginItems.bat_pet, PluginItems.silverfish_pet, PluginItems.parrot_pet, PluginItems.phantom_pet, PluginItems.slime_pet, PluginItems.pig_pet, PluginItems.ender_drag_pet, PluginItems.chicken_pet)) {
			if(petname.equals(item.getItemMeta().getDisplayName())) {
				// si non équipé : 
				if(main.getHasPetEquiped().contains(player)) {
					// déséquiper le pet
					main.getHasPetEquiped().remove(player);

					main.getPets().get(player).desequip();
					if(main.getPets().get(player).isListener()) HandlerList.unregisterAll(main.getPets().get(player));
					main.getPets().remove(player);
					
					player.getOpenInventory().getTopInventory().setItem(13, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
					return;
				} else {
					// faire spawn le pet
					ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(1.5d, -0.4d, 1.5d).setDirection(new Vector()), EntityType.ARMOR_STAND);
					
					armorstand.setGravity(false);
					armorstand.setCustomName("§e\u00BB "+ petname +" §e\u00AB");
					armorstand.setCustomNameVisible(true);
					armorstand.setBasePlate(false);
					armorstand.setInvulnerable(true);
					armorstand.setCollidable(false);
					armorstand.setVisible(false);
					armorstand.setHelmet(item);
					main.getPetsArmorstand().addLast(new PetPlayerCouple(player, armorstand));
					main.getHasPetEquiped().add(player);
					
					EquipablePet petClass = getPetFromHead(petname, player, PetRarity.LEGENDARY);
					main.getPets().put(player, petClass);
					if(main.getPets().get(player).isListener()) Bukkit.getPluginManager().registerEvents(petClass, main);
					main.getPets().get(player).equip();
					
					player.getOpenInventory().getTopInventory().setItem(13, new ItemManager.ItemBuilder(Material.LIME_DYE, 1, "§cPet équipé").build());
					return;
				}
				
			}
		}
	}
	
	public PetRarity getRarityFromLore(ItemStack head) {
		
		String lore = head.getItemMeta().getLore().get(0);
		for(PetRarity rarity : PetRarity.values()) {
			if(rarity.getLabel().equals(lore)) return rarity;
		}
		return PetRarity.COMMUN;
		
	}
	
	public EquipablePet getPetFromHead(String headName, Player player, PetRarity rarity) {
		
		if(headName.equals(PluginItems.bat_pet.getItemMeta().getDisplayName())) {
			return new BatPet(main, player, rarity);
		} else if(headName.equals(PluginItems.silverfish_pet.getItemMeta().getDisplayName())) {
			return new SilverfishPet(main, player, rarity);
		} else if(headName.equals(PluginItems.chicken_pet.getItemMeta().getDisplayName())) {
			return new ChickenPet(main, player, rarity);
		} else if(headName.equals(PluginItems.ender_drag_pet.getItemMeta().getDisplayName())) {
			return new EdragPet(main, player, rarity);
		} else if(headName.equals(PluginItems.parrot_pet.getItemMeta().getDisplayName())) {
			return new ParrotPet(main, player, rarity);
		} else if(headName.equals(PluginItems.phantom_pet.getItemMeta().getDisplayName())) {
			return new PhantomPet(main, player, rarity);
		} else if(headName.equals(PluginItems.pig_pet.getItemMeta().getDisplayName())) {
			return new PigPet(main, player, rarity);
		} else if(headName.equals(PluginItems.slime_pet.getItemMeta().getDisplayName())) {
			return new SlimePet(main, player, rarity);
		} else {
			return new PigPet(main, player, rarity); // default case
		}
		
	}

}
