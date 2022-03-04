package fr.nowayy.ores;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nowayy.ores.commands.GiveOresCommand;
import fr.nowayy.ores.listener.CobbleGeneratorListener;
import fr.nowayy.ores.listener.CompressorListener;
import fr.nowayy.ores.listener.CrushingHammerListener;
import fr.nowayy.ores.listener.RockBreakerListener;

public class Main extends JavaPlugin {
	
	public static final String PREFIX = "§7[§6ArqionOres§7]§r";
	
	public HashMap<UUID, HashMap<Location, Integer>> cobbleGeneratorUpgrades = new HashMap<UUID, HashMap<Location, Integer>>();
	
	@Override
	public void onEnable() {
		Crafts.loadAll();
		registerEvents();
	}
	
	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new CompressorListener(this), this);
		Bukkit.getPluginManager().registerEvents(new CrushingHammerListener(), this);
		Bukkit.getPluginManager().registerEvents(new RockBreakerListener(), this);
		Bukkit.getPluginManager().registerEvents(new CobbleGeneratorListener(), this);
		getCommand("giveitem").setExecutor(new GiveOresCommand());
	}

}
