package fr.altaks.arqionpets.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.altaks.arqionpets.PetCraft;
import fr.altaks.arqionpets.PetCrafts;
import fr.altaks.arqionpets.pets.EquipablePet.PetRarity;
import fr.altaks.arqionpets.utils.ItemManager;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.TileEntitySkull;

public class PetInfuserInteractionListener implements Listener {
	
	private static List<Integer> gridSlots = Arrays.asList( 10, 11, 12, 19, 20, 21, 28, 29, 30, 25, 41 );
	
	private static List<InventoryAction> placingAction = Arrays.asList(
			InventoryAction.PLACE_ALL,
			InventoryAction.PLACE_ONE,
			InventoryAction.PLACE_SOME
	);
	
	private static List<InventoryAction> pickupAction = Arrays.asList(
			InventoryAction.PICKUP_ALL,
			InventoryAction.PICKUP_HALF,
			InventoryAction.PICKUP_ONE,
			InventoryAction.PICKUP_SOME
	);
		
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
			for(int i : gridSlots) inv.setItem(i, null);
			
			event.getPlayer().openInventory(inv);
			event.setCancelled(true);
		} 
	}
	
	// rotten flesh : 41 et output : 25
	
	@EventHandler
	public void onPetCraftPrepare(InventoryClickEvent event) {
		if(event.getClickedInventory() == null) return;
		if(!event.getClickedInventory().equals(event.getView().getTopInventory())) return;
		if(!(event.getWhoClicked() instanceof Player)) return;
		if(event.getView().getTitle() != "§8Pet Infuser \u00BB") return;
		if(event.getCurrentItem() != null) {
			if(event.getCurrentItem().equals(ItemManager.PrebuiltItems.inventoryFillingGlassPane)) {
				event.setCancelled(true);
				return;
			}
		}
		
		
		
		// si c'est un pick : -
			// si c'est dans le slot 25 et que l'item n'est pas null-
				// annuler le clic-
				// donner l'output au joueur-
				// clear la grid-
		// si c'est un place :-
			// si c'est dans le slot 25 on annule-
			// si dans le slot 41 on a pas 64 rotten : return- 
			// si on a toutes les rotten-  
				// on cherche la recipe
				// si la recipe n'est pas null on affiche un output

		if(pickupAction.contains(event.getAction())){

			if(event.getSlot() == 25 && event.getClickedInventory().getItem(25) != null){

				event.setCancelled(true);
				
				ItemStack petItem = event.getClickedInventory().getItem(25).clone();
				float random = new Random().nextFloat() * 100;
				
				PetRarity rarity = PetRarity.COMMON;
				
				if(random < 2.5) {
					rarity = PetRarity.LEGENDARY;
				} else if(random < 10) {
					rarity = PetRarity.EPIC;
				} else if(random < 25) {
					rarity = PetRarity.RARE;
				}
				
				ItemMeta meta = petItem.getItemMeta();
				meta.setLore(Arrays.asList(rarity.getRarityLore()));
				petItem.setItemMeta(meta);
				
				event.getWhoClicked().getInventory().addItem(petItem);
				
				
				for(int slot : gridSlots) event.getClickedInventory().setItem(slot, null);
				((Player)event.getWhoClicked()).updateInventory();
				return;
			}

		} else if(placingAction.contains(event.getAction())){
			
			if(!gridSlots.contains(event.getSlot())) {
				event.setCancelled(true);
				return;
			}

			if(event.getSlot() == 25) {
				event.setCancelled(true);
				((Player)event.getWhoClicked()).updateInventory();
				return;
			}

			if(event.getClickedInventory().getItem(41) != null){
				
				ItemStack fleshStack = event.getClickedInventory().getItem(41);
				if(fleshStack.getType() != Material.ROTTEN_FLESH || fleshStack.getAmount() != 64) return;

				// on cherche la recipe
				PetCraft craft = null;
				for(PetCraft search : PetCrafts.all_pets_crafts){
					if(search.matchRecipe(event.getClickedInventory())){
						craft = search;
						break;
					}
				}
				if(craft != null){
					// si le craft n'est pas null alors on montre l'output
					event.getClickedInventory().setItem(25, craft.getOutput());
					((Player)event.getWhoClicked()).updateInventory();
					return;
				}
			}

		}

	}

}
