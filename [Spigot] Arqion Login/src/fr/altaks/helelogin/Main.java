package fr.altaks.helelogin;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.helelogin.bdd.DatabaseManager;
import fr.altaks.helelogin.listener.PlayerLogEvent;

public class Main extends JavaPlugin {
	
	private DatabaseManager dbManager;
	
	public static final String PREFIX = "§7[§6HeleLogin§7] \u00BB ";
	
	public HashMap<UUID, Integer> spamId;
	
	@Override
	public void onEnable() {
		spamId = new HashMap<UUID, Integer>();
		dbManager = new DatabaseManager();
		
		Bukkit.getPluginManager().registerEvents(new PlayerLogEvent(this), this);
		
		getCommand("register").setExecutor(new RegisterCommand(this));
		getCommand("login").setExecutor(new LoginCommand(this));
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
	}
	
	@Override
	public void onDisable() {
		dbManager.close();
	}

	public DatabaseManager getDbManager() {
		return dbManager;
	}

	public HashMap<UUID, Integer> getSpamid() {
		return spamId;
	}

}
