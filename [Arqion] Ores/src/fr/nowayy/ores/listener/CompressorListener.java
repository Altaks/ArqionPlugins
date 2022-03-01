package fr.nowayy.ores.listener;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.nowayy.ores.Main;
import fr.nowayy.ores.utils.ItemManager;
import fr.nowayy.ores.utils.ItemManager.PrebuiltItems;
import fr.nowayy.ores.utils.Items;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.TileEntitySkull;

/**
 * @author NoWay_y
 *
 */
public class CompressorListener implements Listener {
	Items items = new Items();
	
	private Main main;
	
	public CompressorListener(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onSkullClick(PlayerInteractEvent event) {
		if(event.getPlayer().isSneaking()) return;
        if(!event.hasBlock()) return;
        if(event.getPlayer().getItemInHand() != null && event.getClickedBlock() == items.compressor) event.setCancelled(true);
        if(event.getClickedBlock().getType() != Material.PLAYER_HEAD) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        Block skullBlock = event.getClickedBlock();
        
        TileEntitySkull tileSkull = (TileEntitySkull)((CraftWorld)skullBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(skullBlock.getX(), skullBlock.getY(), skullBlock.getZ()));
        if(tileSkull.gameProfile == null) return;
        
        if(tileSkull.gameProfile.getProperties().get("textures").iterator().next().getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQ4MzM3ZjdlZGUxNWMzYjJmOGRjNmE2M2JkOTI4NzRjZGY3NGVjODYyYjQxMThjN2UzNTU1OWNlOGI0ZCJ9fX0=")) {
        	event.setCancelled(true);
        	Inventory compressorInv = Bukkit.createInventory(null, 9 * 4, "§8Compressor");
    		
    		for(int x = 0; x < compressorInv.getSize(); x++) compressorInv.setItem(x, PrebuiltItems.inventoryFillingGlassPane);
    		for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) compressorInv.setItem(x, new ItemManager.ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "§cCompression non lancée").build());
    		for(int z : new int[] {0, 9, 18, 27}) compressorInv.setItem(z, null);
    		compressorInv.setItem(25, new ItemManager.ItemBuilder(Material.BARRIER, 1, " ").build());
    		
    		event.getPlayer().openInventory(compressorInv);
        } 
    }
	
	

	@EventHandler
	public void onInteract(InventoryClickEvent event) {
		if(event.getView().getTitle().equals("§8Compressor")) {
			Inventory 	clickedInv = event.getClickedInventory(),
						inv = event.getWhoClicked().getInventory(),
						topInv = event.getView().getTopInventory(); 
			
			ItemStack current = event.getCurrentItem();
			
			if(event.getSlot() == 25 && topInv.getItem(25).getType() != Material.BARRIER) {
				event.setCancelled(true);
				inv.addItem(topInv.getItem(25));
				for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) topInv.setItem(x, new ItemManager.ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "§cCompression non lancée").build());
				topInv.setItem(25, new ItemManager.ItemBuilder(Material.BARRIER, 1, " ").build());
				
				return;
			}
			event.setCancelled(true);
			if(topInv.getItem(11).getType() != Material.WHITE_STAINED_GLASS) return;
			
			if(current != null) if(current.getType().toString().contains("STAINED_GLASS")) { event.setCancelled(true); return; }
			if(current == null) return;
		
		
			if(current.getAmount() == 64) {
				
				if(Arrays.asList(Material.COBBLESTONE, Material.FEATHER, Material.SLIME_BLOCK).contains(current.getType())) {
					
					clickedInv.removeItem(current);
					if(clickedInv.equals(event.getView().getBottomInventory())) {
						topInv.addItem(current);
					} else inv.addItem(current);
					
					if(isSimilar(topInv, 0, 9, 18, 27)) { // tu peux mettre le nb de slots que tu veux
						
						new BukkitRunnable() {
							private int counter = 0;
							ItemStack progressGlass = new ItemManager.ItemBuilder(Material.RED_STAINED_GLASS, 1, generateProgressBar(0, 50)).build();
							
							@Override
							public void run() {
								
								
								if(counter == 21) return;
								
								if(counter == 5)  progressGlass = new ItemManager.ItemBuilder(Material.ORANGE_STAINED_GLASS, 1, generateProgressBar(25, 50)).build();
								if(counter == 10) progressGlass = new ItemManager.ItemBuilder(Material.GREEN_STAINED_GLASS, 1, generateProgressBar(50, 50)).build();
								if(counter == 15) progressGlass = new ItemManager.ItemBuilder(Material.LIME_STAINED_GLASS, 1, generateProgressBar(75, 50)).build();
								
								if(counter == 20) {
									progressGlass = new ItemManager.ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS, 1, generateProgressBar(100, 50)).build();
									
									if(topInv.getItem(0).getType() == Material.COBBLESTONE) topInv.setItem(25, items.compressCobble);
									if(topInv.getItem(0).getType() == Material.FEATHER) topInv.setItem(25, items.compressFeather);
									if(topInv.getItem(0).getType() == Material.SLIME_BLOCK) topInv.setItem(25, items.compressSlimeBlock);
									
									for(int x : new int[] {0, 9, 18, 27}) topInv.setItem(x, null);
								
									
								}
								
								for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) topInv.setItem(x, progressGlass);
								counter++;
							}
						}.runTaskTimer(main, 0, 20);
						
					}
					
					
					
				}
					
				
			}
		}		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		event.setDropItems(false);
		
		Block skullBlock = event.getBlock();
	        
        TileEntitySkull tileSkull = (TileEntitySkull)((CraftWorld)skullBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(skullBlock.getX(), skullBlock.getY(), skullBlock.getZ()));
        if(tileSkull.gameProfile == null) return;
        
        if(tileSkull.gameProfile.getProperties().get("textures").iterator().next().getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQ4MzM3ZjdlZGUxNWMzYjJmOGRjNmE2M2JkOTI4NzRjZGY3NGVjODYyYjQxMThjN2UzNTU1OWNlOGI0ZCJ9fX0=")) {
        	event.getPlayer().getInventory().addItem(items.compressor);
        }
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event) {
		if(event.getView().getTitle().equals("§8Compressor")) {
			for(int x : new int[] {0, 9, 18, 27}) {
				if(event.getInventory().getItem(x) != null)
					event.getPlayer().getInventory().addItem(event.getInventory().getItem(x));
			}
		}
	}
	
	public static String generateProgressBar(int progress, int bar_lenght){
        progress = bar_lenght * progress / 100;

        //    100      46
        //     pg       x

        // x = bar_lenght * progress / 100


        StringBuilder barBuilder = new StringBuilder();
        barBuilder.append("§a");
        for(int i = 0; i < progress; i++) barBuilder.append("|");
        barBuilder.append("§7");
        for(int i = 0; i < bar_lenght - progress; i++) barBuilder.append("|");

        return barBuilder.toString();
	}

	/**
	Permet de déterminer si tous les items situés aux slots indiqués sont similaires
	@param inv : l'inventaire en question
	@param slots : la liste des slots
	@return si tous les items sont similaires
	*/
	public static boolean isSimilar(Inventory inv, int...slots) { // tableau sans limite de taille, c'est un argument sans limite
		for(int i = 1; i < slots.length; i++){
			if(inv.getItem(slots[i]) == null) return false;
			if(!inv.getItem(slots[i]).equals(inv.getItem(slots[i-1]))) return false; 
		}

		return true;
	}
	
	
		
}
