package fr.altaks.assaut;

import fr.altaks.assaut.util.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;
import fr.altaks.assaut.managers.GameManager;

public class Main extends JavaPlugin {

    public static final String PREFIX = "§7[§aAssaut§7] \u00BB§r ";
    public static boolean debugMode = false;
    private GameManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if(getConfig().isSet("debug-mode")) debugMode = getConfig().getBoolean("debug-mode");

        try {
            manager = new GameManager(this);
        } catch (ItemManager.ItemBuildingError e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(manager, this);
    }

    @Override
    public void onDisable() {

    }
}
