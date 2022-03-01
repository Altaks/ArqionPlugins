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
	PrebuiltItems im = new ItemManager.PrebuiltItems();
	Items items = new Items();
	
	private Main main;
	
	public CompressorListener(Main main) {
		this.main = main;
	}
	
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
    		for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) compressorInv.setItem(x, new ItemManager.ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "§cCompression non lancée").build());
    		for(int z : new int[] {0, 9, 18, 27}) compressorInv.setItem(z, null);
    		compressorInv.setItem(25, new ItemManager.ItemBuilder(Material.BARRIER, 1, " ").build());
    		
    		event.getPlayer().openInventory(compressorInv);
        } 
    }
	
	

	@EventHandler
	public void onInteract(InventoryClickEvent event) {
		
		Inventory clickedInv = event.getClickedInventory();
		Inventory inv = event.getWhoClicked().getInventory();
		ItemStack current = event.getCurrentItem();
		
		if(current != null) if(current.getType().toString().contains("STAINED_GLASS")) { event.setCancelled(true); return; }
		if(current == null) return;
		
		
		
		if(event.getView().getTitle().equals("§8Compressor")) {
			event.setCancelled(true);
			if(current.getAmount() == 64) {
				
				if(current.getType() == Material.COBBLESTONE || current.getType() == Material.FEATHER || current.getType() == Material.SLIME_BLOCK) {
					
					clickedInv.removeItem(current);
					if(clickedInv.equals(event.getView().getBottomInventory())) {
						event.getView().getTopInventory().addItem(current);
					} else inv.addItem(current);
				}
					
				
			}
		}
		
		
		
		
		
		
		
		
		
//		Inventory clickedInv = event.getClickedInventory();
//		ItemStack current = event.getCurrentItem();
//		List<Integer> clickedIndexes = new LinkedList<Integer>();
//		for(int x : new int[] {0, 9, 18, 27}) clickedIndexes.add(x);
//		
//		if(event.getView().getTitle().equals("§8Compressor")) {
//			if(event.getView().getTopInventory().equals(clickedInv)) {
//				if(event.getSlot() == 25 && clickedInv.getItem(25) == null) { event.setCancelled(true); return; }
//				if(current != null) if(current.getType().toString().contains("STAINED_GLASS")) { event.setCancelled(true); return; }
//				if(current != null) return;
//				if(event.getWhoClicked().getItemOnCursor() == null) return;
//				clickedIndexes.remove(Integer.valueOf(event.getSlot()));
//				// verif si c'est des stacks
//				// si oui:
//				if(event.getCursor().getAmount() == 64) {
//					System.out.println(clickedIndexes);
//					// si les 4 slots sont full
//					// verif le material et si c'est le même pour les 4
//					System.out.println("ok1");
//					if(clickedInv.getItem(clickedIndexes.get(0)) == clickedInv.getItem(clickedIndexes.get(1))) {
//						System.out.println("1st");
//						if(clickedInv.getItem(clickedIndexes.get(1)) == clickedInv.getItem(clickedIndexes.get(2))) {
//							System.out.println("2nd");
//							if(clickedInv.getItem(clickedIndexes.get(2)) == event.getCursor()) {
//								System.out.println("ok");
//								if(clickedInv.getItem(clickedIndexes.get(0)).getType() == Material.COBBLESTONE || 
//										clickedInv.getItem(clickedIndexes.get(0)).getType() == Material.FEATHER || 
//										clickedInv.getItem(clickedIndexes.get(0)).getType() == Material.SLIME_BLOCK) {
//									
//									new BukkitRunnable() {
//										private int counter = 0;
//										private int max = 20;
//										
//										@Override
//										public void run() {
//											if(counter == max) cancel();
//											
//											// counter = 1 -> 1/20 = 0.05
//											// counter = 2 -> 2/20 = 0.1
//											// 3 -> 0.15
//											// 4 -> 0.2
//											// 5 -> 0.25
//											// ...
//
//											int progress = counter/max*100;
//											ItemStack progressGlass = new ItemManager.ItemBuilder(Material.RED_STAINED_GLASS, 1, generateProgressBar(progress, 50)).build();
//											
//											// mettre à jour les verres en fonction de la progression
//											if(progress == 25) {progressGlass.setType(Material.ORANGE_STAINED_GLASS); System.out.println("orange");}
//											if(progress == 5) {progressGlass.setType(Material.GREEN_STAINED_GLASS); System.out.println("vert");}
//											if(progress == 75) {progressGlass.setType(Material.LIME_STAINED_GLASS); System.out.println("lime");}
//											if(progress == 100) {
//												
//												if(clickedInv.getItem(0).getType() == Material.COBBLESTONE) clickedInv.setItem(25, items.compressCobble);
//												progressGlass.setType(Material.LIGHT_BLUE_STAINED_GLASS);
//												if(clickedInv.getItem(0).getType() == Material.FEATHER) clickedInv.setItem(25, items.compressFeather);
//												if(clickedInv.getItem(0).getType() == Material.SLIME_BLOCK) clickedInv.setItem(25, items.compressSlimeBlock);
//												if(clickedInv.getItem(0).equals(items.pet_dust)) clickedInv.setItem(25, items.compressPetDust);
//												
//											}
//										
//											for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) clickedInv.setItem(x, progressGlass);
//											System.out.println(counter);
//											counter++;
//										}
//									}.runTaskTimer(main, 0, 20);
//										
//										
//										
//								}
//							}
//						}
//					}
//				}
//			}
//		
//		}
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
		
}
