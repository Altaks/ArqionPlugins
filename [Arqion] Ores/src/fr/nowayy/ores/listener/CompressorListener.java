package fr.nowayy.ores.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.nowayy.ores.utils.ItemManager;
import fr.nowayy.ores.utils.ItemManager.PrebuiltItems;
import fr.nowayy.ores.utils.Items;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.TileEntitySkull;

public class CompressorListener implements Listener {
	PrebuiltItems im = new ItemManager.PrebuiltItems();
	Items items = new Items();
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onSkullClick(PlayerInteractEvent event) {
        
        if(!event.hasBlock()) return;
        if(event.getPlayer().getItemInHand() != null && event.getClickedBlock() == items.compressor) event.setCancelled(true);
        if(event.getClickedBlock().getType() != Material.PLAYER_HEAD) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        Block skullBlock = event.getClickedBlock();
        
        TileEntitySkull tileSkull = (TileEntitySkull)((CraftWorld)skullBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(skullBlock.getX(), skullBlock.getY(), skullBlock.getZ()));
        if(tileSkull.gameProfile == null) return;
        
        if(tileSkull.gameProfile.getProperties().get("textures").iterator().next().getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQ4MzM3ZjdlZGUxNWMzYjJmOGRjNmE2M2JkOTI4NzRjZGY3NGVjODYyYjQxMThjN2UzNTU1OWNlOGI0ZCJ9fX0=")) {

        	Inventory compressorInv = Bukkit.createInventory(null, 9 * 4, "§8Compressor");
    		
    		for(int x = 0; x < compressorInv.getSize(); x++) compressorInv.setItem(x, PrebuiltItems.inventoryFillingGlassPane);
    		for(int y : new int[] {13, 14, 15, 22, 23, 24}) compressorInv.setItem(y, new ItemManager.ItemBuilder(Material.WHITE_STAINED_GLASS, 1).build());
    		for(int z : new int[] {10, 11, 19, 20}) compressorInv.setItem(z, null);
    		
    		event.getPlayer().openInventory(compressorInv);
        } 
    }
	
	
	@EventHandler
	public void onInteract(InventoryClickEvent event) {
		Inventory clickedInv = event.getClickedInventory();
		
		if(event.getView().getTitle().equals("§8Compressor")) {
			if(event.getView().getTopInventory().equals(clickedInv)) {
				if(!(event.getSlot() == 10 || event.getSlot() == 11 || event.getSlot() == 19 || event.getSlot() == 20)) {
					event.setCancelled(true);
					return;
				}
				
				
					
				event.getWhoClicked().sendMessage("ok");
			}
		}
	}
		
}
