package fr.matmatgamer.helebitcoins;


import org.bukkit.plugin.java.JavaPlugin;

import fr.matmatgamer.helebitcoins.commands.CommandAbitcoins;
import fr.matmatgamer.helebitcoins.commands.CommandAbtc;
import fr.matmatgamer.helebitcoins.commands.CommandBitcoins;
import fr.matmatgamer.helebitcoins.commands.CommandCancelCablage;
import fr.matmatgamer.helebitcoins.listeners.Abitcoins;
import fr.matmatgamer.helebitcoins.listeners.AllimEssence;
import fr.matmatgamer.helebitcoins.listeners.BitcoinsDestroyMachine;
import fr.matmatgamer.helebitcoins.listeners.BitcoinsPlaceMachine;
import fr.matmatgamer.helebitcoins.listeners.Cablage;
import fr.matmatgamer.helebitcoins.listeners.Join;
import fr.matmatgamer.helebitcoins.listeners.PCInterface;
import fr.matmatgamer.helebitcoins.listeners.TabletteListener;
import fr.matmatgamer.helebitcoins.utils.Crafts;

public class Main extends JavaPlugin {

	public static final String prefix = "§c[§6BitCoins§c] ";
	public static final String DebugPrefix = "[HeleBitcoins] ";
	public static final String ItemMarkerBTC = "§b=§9-§b=§9-§b=§9-§b=§9-§6§l BitCoins §9-§b=§9-§b=§9-§b=§9-§b=";
	public static final String ItemMarkerOres = "§b=§9-§b=§9-§b=§9-§b=§9-§6§l HeleMinerais §9-§b=§9-§b=§9-§b=§9-§b=";
	
	@Override
	public void onEnable() {
		System.out.println(Main.DebugPrefix + "Plugin allumé avec succès.");
		registerEvents();
		
		Crafts Crafts = new Crafts(this);
		
		Crafts.LoadAll();
	}

	@Override
	public void onDisable() {
		System.out.println(Main.DebugPrefix + "Plugin éteint avec succès.");
	}
	
	public void registerEvents() {
		getCommand("abitcoins").setExecutor(new CommandAbitcoins());
		getCommand("bitcoins").setExecutor(new CommandBitcoins(this));
		getCommand("abtc").setExecutor(new CommandAbtc(this));
		getCommand("cancelCablage").setExecutor(new CommandCancelCablage(this));
		
		getServer().getPluginManager().registerEvents(new BitcoinsPlaceMachine(this), this);
		getServer().getPluginManager().registerEvents(new BitcoinsDestroyMachine(this), this);
		getServer().getPluginManager().registerEvents(new AllimEssence(this), this);
		getServer().getPluginManager().registerEvents(new Cablage(this), this);
		getServer().getPluginManager().registerEvents(new PCInterface(this), this);
		getServer().getPluginManager().registerEvents(new TabletteListener(), this);
		getServer().getPluginManager().registerEvents(new Abitcoins(), this);
		getServer().getPluginManager().registerEvents(new Join(this), this);
		
	}

}
