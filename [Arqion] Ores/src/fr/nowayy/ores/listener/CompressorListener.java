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
import org.bukkit.scheduler.BukkitRunnable;

import fr.nowayy.ores.Main;
import fr.nowayy.ores.utils.ItemManager;
import fr.nowayy.ores.utils.ItemManager.PrebuiltItems;
import fr.nowayy.ores.utils.Items;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.TileEntitySkull;

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
    		for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) compressorInv.setItem(x, new ItemManager.ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "").build());
    		for(int z : new int[] {0, 9, 18, 27, 25}) compressorInv.setItem(z, null);
    		
    		event.getPlayer().openInventory(compressorInv);
        } 
    }
	
	
	@EventHandler
	public void onInteract(InventoryClickEvent event) {
		Inventory clickedInv = event.getClickedInventory();
		
		if(event.getView().getTitle().equals("§8Compressor")) {
			if(event.getView().getTopInventory().equals(clickedInv)) {
				if(event.getSlot() == 25 && clickedInv.getItem(25) == null) { event.setCancelled(true); return; }
				if(event.getCurrentItem() != null) if(event.getCurrentItem().getType().toString().contains("STAINED_GLASS")) { event.setCancelled(true); return; }
				
				// si les 4 slots sont full
				// verif le material et si c'est le même pour les 4
				System.out.println("aaaaaaaaa");
				for(int i : new int[] {0, 9, 18, 27}) {
					if(clickedInv.getItem(i) == null) return;
				}
				if(clickedInv.getItem(0).equals(clickedInv.getItem(9))) {
					if(clickedInv.getItem(9).equals(clickedInv.getItem(18))) {
						if(clickedInv.getItem(18).equals(clickedInv.getItem(27))) {
							System.out.println("ok");
							if(clickedInv.getItem(0).getType() == Material.COBBLESTONE || 
									clickedInv.getItem(0).getType() == Material.FEATHER || 
									clickedInv.getItem(0).getType() == Material.SLIME_BLOCK) {
								// verif si c'est des stacks
								// si oui:
								if(clickedInv.getItem(0).getAmount() == 64) {
									event.getWhoClicked().sendMessage("ok");
									new BukkitRunnable() {
										private int counter = 0;
										
										@Override
										public void run() {
											if(counter == 20) cancel();
											
											int progress = counter/20*100;
											ItemStack progressGlass = new ItemManager.ItemBuilder(Material.RED_STAINED_GLASS, 1, generateProgressBar(progress, 50)).build();
											
											// mettre à jour les verres en fonction de la progression
											if(progress >= 25) progressGlass.setType(Material.ORANGE_STAINED_GLASS);
											if(progress >= 50) progressGlass.setType(Material.GREEN_STAINED_GLASS);
											if(progress >= 75) progressGlass.setType(Material.LIME_STAINED_GLASS);
											if(progress == 100) {
												progressGlass.setType(Material.LIGHT_BLUE_STAINED_GLASS);
												
												if(clickedInv.getItem(0).getType() == Material.COBBLESTONE) clickedInv.setItem(25, items.compressCobble);
												if(clickedInv.getItem(0).getType() == Material.FEATHER) clickedInv.setItem(25, items.compressFeather);
												if(clickedInv.getItem(0).getType() == Material.SLIME_BLOCK) clickedInv.setItem(25, items.compressSlimeBlock);
												if(clickedInv.getItem(0).equals(items.pet_dust)) clickedInv.setItem(25, items.compressPetDust);
												
											}
											for(int x : new int[] {11, 12, 13, 14, 20, 21, 22, 23}) clickedInv.setItem(x, progressGlass);
											System.out.println(counter);
											counter++;
										}
									}.runTaskTimer(main, 0, 20);
									
									
									
								}
							}
						}
					}
				}
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
		
}
