package fr.altaks.arqionpets.listeners;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import fr.altaks.arqionpets.Main;
import fr.altaks.arqionpets.pets.EquipablePet.PetRarity;
import fr.altaks.arqionpets.utils.ItemManager;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.TileEntitySkull;

public class PetInfuserInteractionListener implements Listener {
	
	private Main main;
	
	public PetInfuserInteractionListener(Main main) {
		this.main = main;
	}
		
	@EventHandler(ignoreCancelled = true)
	public void onSkullClick(PlayerInteractEvent event) {
		
		if(!event.hasBlock()) return;
		if(event.getClickedBlock().getType() != Material.PLAYER_HEAD) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		Block skullBlock = event.getClickedBlock();
		
		TileEntitySkull tileSkull = (TileEntitySkull)((CraftWorld)skullBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(skullBlock.getX(),skullBlock.getY(),skullBlock.getZ()));
		if(tileSkull.gameProfile == null) return;
		if(tileSkull.gameProfile.getProperties().get("textures").iterator().next().getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQxMjY1ZDU3ZDM3MDZjMGI0MjhmMWIyNDMwNDRkNTBkODQxNDkyNmFjYjM3NTJiOGNiOWY1Yjg1YmNkZDA5NiJ9fX0=")) {
			
			Inventory inv = Bukkit.createInventory(null, 6 * 9, "§8Pet Infuser \u00BB");
			
			for(int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemManager.PrebuiltItems.inventoryFillingGlassPane);
			for(int i : Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30, 25, 41)) inv.setItem(i, null);
			
			event.getPlayer().openInventory(inv);
		} 
	}
	
	// rotten flesh : 25 et output : 41
	
	@EventHandler
	public void onPetCraftPrepare(InventoryClickEvent event) {
		if(event.getClickedInventory() == null) return;
		if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
		if(event.getClickedInventory().equals(event.getView().getTopInventory())) return;
		if(event.getView().getTitle() != "§8Pet Infuser \u00BB") return;
		if(event.getCurrentItem() == null) return;
		if(event.getCurrentItem().equals(ItemManager.PrebuiltItems.inventoryFillingGlassPane)) {
			event.setCancelled(true);
			return;
		}
		
		// on actualise la grid a chaque fois
		switch (event.getAction()) {
			case PLACE_ALL:
			case PLACE_ONE:
			case PLACE_SOME:
				if(event.getSlot() == 25) {
					// on annule, il faut rien placer dans l'output
					event.setCancelled(true);
					event.getWhoClicked().sendMessage(Main.PREFIX + "Vous ne pouvez rien placer dans le slot de sortie de l'infuseur de pets !");
					break;
				}
				// on actualise la grid
				
				break;
			
			case PICKUP_ALL:
			case PICKUP_ONE:
			case PICKUP_HALF:
			case PICKUP_SOME:
				if(event.getSlot() == 25) {
					// si c'est l'output on clear la grid
					for(int i : Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30)) event.getView().getTopInventory().setItem(i, null);
					((Player)event.getWhoClicked()).updateInventory();
					
					// changer le lore du pet droppé
					PetRarity rarity = PetRarity.COMMON;
					float dropRate = new Random().nextFloat() * 100;
					if(dropRate < 2.5) {
						rarity = PetRarity.LEGENDARY;
					} else if(dropRate < 10) {
						rarity = PetRarity.EPIC;
					} else if(dropRate < 25) rarity = PetRarity.RARE;
					
					ItemMeta meta = event.getCurrentItem().getItemMeta();
					meta.setLore(Arrays.asList(rarity.getRarityLore())); // mise du lore en fonction de la rarity
					break;
				}
				break;
		default:
			event.setCancelled(true);
			return;
		}
		
		
	}
	
	@EventHandler
	public void onPlayerEnablePet(PlayerInteractEvent event) {
		
		if(event.hasBlock()) return;
		if(!event.hasItem()) return;
		
		
		
	}

}
