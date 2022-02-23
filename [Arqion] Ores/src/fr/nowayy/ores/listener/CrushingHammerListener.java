package fr.nowayy.ores.listener;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CrushingHammerListener implements Listener {
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() == Material.COBBLESTONE) {
			Random random = new Random();
			float rdmOre = random.nextInt(1);
			if(rdmOre <= 0.5) {
				
			}
		}
	}

}
