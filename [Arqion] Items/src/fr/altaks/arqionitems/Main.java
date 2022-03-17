package fr.altaks.arqionitems;

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
		for(SpecialItem item : items) if(item.hasListener()) Bukkit.getServer().getPluginManager().registerEvents(item, this)

		
	}
	
	
}
