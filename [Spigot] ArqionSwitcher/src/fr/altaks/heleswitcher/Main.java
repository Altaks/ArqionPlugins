package fr.altaks.heleswitcher;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static final ItemStack compass = ItemStackBuilder.buildItemStack(Material.COMPASS, "§6Serveurs", new ArrayList<String>(Collections.singletonList("Utilises moi pour accéder aux différents serveurs !")));
	
	@Override
	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}

}
