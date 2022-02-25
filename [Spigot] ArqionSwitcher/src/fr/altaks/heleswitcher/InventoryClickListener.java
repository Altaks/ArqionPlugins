package fr.altaks.heleswitcher;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class InventoryClickListener implements Listener {
	
	private Main main;
	
	public InventoryClickListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		
		if (event.getView().getTitle().equals("§8Serveurs")) {
		
			final ItemStack itemStack = event.getCurrentItem();
			
			for(Servers serv : Servers.values()) {
				if(itemStack.equals(serv.getSymbol())) {
					
					event.setCancelled(true);
					
					final ByteArrayDataOutput out = ByteStreams.newDataOutput();
					
					out.writeUTF("Connect");
					out.writeUTF(serv.getId());
					
					player.sendPluginMessage(main, "BungeeCord", out.toByteArray());
				}
			}
			
			
		}
		
	}

}
