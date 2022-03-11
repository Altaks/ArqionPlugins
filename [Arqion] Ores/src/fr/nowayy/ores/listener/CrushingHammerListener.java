package fr.nowayy.ores.listener;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.nowayy.ores.utils.OresDropRates;

public class CrushingHammerListener implements Listener {
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() == Material.COBBLESTONE) {
			Random random = new Random();
			float isDropping = random.nextFloat(1);
			// si ça drop quelque chose
			if(isDropping <= 0.5) {
				
				float droppedItem = random.nextFloat(1);
				
//				
//				 drop rates:
//				 	coal : 100%
//					iron nugget : 50%
//					redstone : 45%
//					lapis : 40%
//				 	gold nugget : 37.5%
//					quartz : 35%
//			 		diamond : 30%
//			 		emerald : 25%
//					aluminium nugget : 20%
//					copper nugget : 15%
//				 	cobalt nugget : 10%
//				 	platinium nugget : 5%
//				 	actinium nugget : 2%

				
				for(OresDropRates ores : OresDropRates.values()) {
					if(droppedItem <= ores.getDropRate()) {
						
					}
				}
				
				
			}
		}
	}

}
