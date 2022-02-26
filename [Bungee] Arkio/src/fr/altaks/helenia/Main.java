package fr.altaks.helenia;

import fr.altaks.helenia.bdd.DatabaseManager;
import fr.altaks.helenia.bungeecommands.Ban;
import fr.altaks.helenia.bungeecommands.Find;
import fr.altaks.helenia.bungeecommands.Modhelp;
import fr.altaks.helenia.bungeecommands.Tempban;
import fr.altaks.helenia.bungeecommands.Unban;
import fr.altaks.helenia.bungeecommands.warns.Clearwarn;
import fr.altaks.helenia.bungeecommands.warns.History;
import fr.altaks.helenia.bungeecommands.warns.Unwarn;
import fr.altaks.helenia.bungeecommands.warns.Warn;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin {
	
	public static final String PREFIX = "§7[§cArkio§7]§c \u00BB ";
	
	private DatabaseManager dbManager;
	
	@Override
	public void onEnable() {
		
		dbManager = new DatabaseManager();
		
		PluginManager manager = getProxy().getPluginManager();
		
		manager.registerCommand(this, new Find("find", this));
		manager.registerCommand(this, new Ban("ban", this));
		manager.registerCommand(this, new Unban("unban", this));
		manager.registerCommand(this, new Tempban("tempban", this));
		
		manager.registerCommand(this, new Warn("warn", this));
		manager.registerCommand(this, new Unwarn("unwarn",this));
		manager.registerCommand(this, new Clearwarn("clearwarn", this));
		
		manager.registerCommand(this, new History("history", this));
		manager.registerCommand(this, new Modhelp("modhelp"));
		
		manager.registerListener(this, new JoinListener(this));
		
		
	}
	
	@Override
	public void onDisable() {
		this.getDatabaseManager().close();
	}
	
	public DatabaseManager getDatabaseManager() {
		return this.dbManager;
	}

}
