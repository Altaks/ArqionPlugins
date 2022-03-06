package fr.nowayy.ores.listener;

import java.io.File;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.nowayy.ores.Main;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.TileEntitySkull;

public class CobbleGeneratorListener implements Listener {

	private Main main;
	public CobbleGeneratorListener(Main main) {
		this.main = main;
	}
	
	private File upgradesFile = new File(main.getDataFolder() + File.separator + "cobble_generator_upgrades.yml");
	private YamlConfiguration config = YamlConfiguration.loadConfiguration(upgradesFile);

	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Block skullBlock = event.getBlock();
	    
	    TileEntitySkull tileSkull = (TileEntitySkull)((CraftWorld)skullBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(skullBlock.getX(), skullBlock.getY(), skullBlock.getZ()));
	    if(tileSkull.gameProfile == null) return;
	    
	    if(tileSkull.gameProfile.getProperties().get("textures").iterator().next().getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGVmZGVlYjEyODJlMWQxM2Y1NmYwNjc0Nzc0OGE0NjM0MTM3Yzg2NjQyODA4NzAyMzdjOTc3ZDA0NGExODNiNiJ9fX0=")) {
	    	
	    	main.cobbleGeneratorUpgrades.put(event.getBlock().getLocation(), 0);
	    	event.getPlayer().sendMessage("Vous avez posé un Cobble Generator");
	    }
	}
	
	@EventHandler
	public void onSkullInteract(PlayerInteractEvent event) {
	
		Block skullBlock = event.getClickedBlock();
	    
	    TileEntitySkull tileSkull = (TileEntitySkull)((CraftWorld)skullBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(skullBlock.getX(), skullBlock.getY(), skullBlock.getZ()));
	    if(tileSkull.gameProfile == null) return;
	    
	    if(tileSkull.gameProfile.getProperties().get("textures").iterator().next().getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGVmZGVlYjEyODJlMWQxM2Y1NmYwNjc0Nzc0OGE0NjM0MTM3Yzg2NjQyODA4NzAyMzdjOTc3ZDA0NGExODNiNiJ9fX0=")) {
	    	
	    	
	    	
	    }
	}
	
}
