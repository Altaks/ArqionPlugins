package fr.altaks.heleloginbungee;

import fr.altaks.heleloginbungee.bdd.DatabaseManager;
import fr.altaks.heleloginbungee.cmds.LogPassChange;
import fr.altaks.heleloginbungee.cmds.LogPassSee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin {
	
	private DatabaseManager dbManager;
	
	public static final String PREFIX = "§7[§6HeleLogin§7] \u00BB ";

	
	@Override
	public void onEnable() {
		dbManager = new DatabaseManager();
		
		PluginManager manager = ProxyServer.getInstance().getPluginManager();
		
		manager.registerCommand(this, new LogPassChange("logpasschange",this));
		manager.registerCommand(this, new LogPassSee("logpasssee",this));
	}
	
	@Override
	public void onDisable() {
		dbManager.close();
	}

	public DatabaseManager getDbManager() {
		return dbManager;
	}

}
