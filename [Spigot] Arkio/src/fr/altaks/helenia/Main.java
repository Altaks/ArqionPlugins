package fr.altaks.helenia;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.helenia.bdd.DatabaseManager;
import fr.altaks.helenia.commands.Mute;
import fr.altaks.helenia.commands.Tempmute;
import fr.altaks.helenia.commands.Unmute;
import fr.altaks.helenia.listener.ChatGuardListener;
import fr.altaks.helenia.listener.ServerGuardListener;

public class Main extends JavaPlugin {
	
	private HashMap<UUID, Long> mutingTimestamps = new HashMap<UUID, Long>();
	
	public static final String PREFIX = "§7[§cArkio§7] \u00BB", NOTFOUND = "§7[§6Système§7] \u00BB ";
	
	private DatabaseManager dbManager = new DatabaseManager();
	
	@Override
	public void onEnable() {
		PluginManager manager = Bukkit.getPluginManager();
		
		manager.registerEvents(new ServerGuardListener(this), this);
		manager.registerEvents(new ChatGuardListener(this), this);
		
		getCommand("mute").setExecutor(new Mute(this));
		getCommand("unmute").setExecutor(new Unmute(this));
		getCommand("tempmute").setExecutor(new Tempmute(this));
	}
	
	@Override
	public void onDisable() {
		dbManager.close();
	}
	
	public DatabaseManager getDatabaseManager() {
		return this.dbManager;
	}

	public HashMap<UUID, Long> getMutingTimestamps() {
		return mutingTimestamps;
	}

}
