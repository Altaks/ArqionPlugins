package fr.altaks.heleshop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.heleshop.api.MoneyUtil;
import fr.altaks.heleshop.commands.Shop;
import fr.altaks.heleshop.listener.ShopClickListener;
import fr.altaks.heleshop.manager.FileManager;
import fr.altaks.heleshop.manager.ShopItem;
import fr.altaks.heleshop.manager.ShopMenu;

public class Main extends JavaPlugin {
	
	public static final String PREFIX = "§7[§cHeleShop§7]§r \u00BB ";
	private File shopConfigFile = new File(getDataFolder() + File.separator + "shopconfig.yml");
	private FileConfiguration shopConfigYml;
	private MoneyUtil moneyUtil;

	private HashMap<ShopMenu, List<ShopItem>> shopMenus = new HashMap<>();
	
	public static final Logger LOGGER = Bukkit.getLogger();
	
	@Override
	public void onEnable() {
		moneyUtil = new MoneyUtil(this);
		
		if(!getDataFolder().exists()) getDataFolder().mkdir();
		
		if(!shopConfigFile.exists()) {
			try {
				shopConfigFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		shopConfigYml = YamlConfiguration.loadConfiguration(shopConfigFile);
				
		try {
			shopMenus = FileManager.read(shopConfigYml);
		} catch (Exception e) {
			LOGGER.warning(PREFIX + "§cshopconfig.yml défecteux !");
			e.printStackTrace();
		}
		
		getCommand("shop").setExecutor(new Shop());
		Bukkit.getPluginManager().registerEvents(new ShopClickListener(this), this);
	}

	@Override
	public void onDisable() {
		moneyUtil.finish();
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public File getShopConfigFile() {
		return shopConfigFile;
	}

	public HashMap<ShopMenu, List<ShopItem>> getShopMenus() {
		return shopMenus;
	}

	public Logger getLogger() {
		return LOGGER;
	}
	
	public FileConfiguration getShopConfigYml() {
		return shopConfigYml;
	}
	
	public MoneyUtil getMoneyUtil() {
		return moneyUtil;
	}

}
