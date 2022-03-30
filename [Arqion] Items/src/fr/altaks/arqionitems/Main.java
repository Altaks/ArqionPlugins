package fr.altaks.arqionitems;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static final String PREFIX = "§7[§aArqionItems§7]§a \u00BB §r";
	public static boolean debugMode = false;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		if(getConfig().isSet("debug-mode")) debugMode = getConfig().getBoolean("debug-mode");
		
		SpecialItem[] items = {

		};
		for(SpecialItem item : items) if(item.isListener()) Bukkit.getServer().getPluginManager().registerEvents((Listener) item, this);
		
		
		
	}
	
	
}
