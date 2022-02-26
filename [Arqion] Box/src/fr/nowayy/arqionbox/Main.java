package fr.nowayy.arqionbox;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nowayy.arqionbox.api.MoneyUtil;
import fr.nowayy.arqionbox.commands.AdminBoxCommand;
import fr.nowayy.arqionbox.commands.BoxCommand;
import fr.nowayy.arqionbox.commands.DebugBoxCommand;
import fr.nowayy.arqionbox.commands.ItemDataLineCommand;
import fr.nowayy.arqionbox.commands.KeyCommand;
import fr.nowayy.arqionbox.core.BoxItem;
import fr.nowayy.arqionbox.core.BoxType;
import fr.nowayy.arqionbox.core.BoxItems.DisplayKeys;
import fr.nowayy.arqionbox.listeners.AdminBoxMenuListener;
import fr.nowayy.arqionbox.listeners.ArmorStandListener;
import fr.nowayy.arqionbox.listeners.BoxMenuListener;
import fr.nowayy.arqionbox.listeners.KeyShopListener;
import fr.nowayy.arqionbox.util.ItemBuilder;

public class Main extends JavaPlugin {
	
	public static boolean debugMode = false;
	public static final String PREFIX = "§7[§cArqion§rBox§7] \u00BB§r ";
	
	private MoneyUtil moneyUtil;
	private static HashMap<BoxType, List<BoxItem>> boxDrops = new HashMap<BoxType, List<BoxItem>>();
	
    public MoneyUtil getMoneyUtil() {
        return moneyUtil;
    }
    
    @Override
    public void onEnable() {
		
    	moneyUtil = new MoneyUtil(this);
    	
    	registerEvents();
    	
    	saveDefaultConfig();
    	if(getConfig().isSet("debug-mode")) debugMode = getConfig().getBoolean("debug-mode");
    	loadBoxDrops();
    
    	
    }
    
    public void registerEvents() {
    	getCommand("box").setExecutor(new BoxCommand(this));
    	getCommand("debugbox").setExecutor(new DebugBoxCommand());
    	getCommand("adminbox").setExecutor(new AdminBoxCommand(this));
    	getCommand("key").setExecutor(new KeyCommand(this));
    	getCommand("itemdataline").setExecutor(new ItemDataLineCommand());
    	
    	Bukkit.getServer().getPluginManager().registerEvents(new ArmorStandListener(this), this);  
    	Bukkit.getServer().getPluginManager().registerEvents(new KeyShopListener(this), this); 
    	Bukkit.getServer().getPluginManager().registerEvents(new BoxMenuListener(this), this);
    	Bukkit.getServer().getPluginManager().registerEvents(new AdminBoxMenuListener(this), this);
    	
    }
    
    
    
    public static List<BoxItem> getBoxDrops(BoxType type){
		return boxDrops.get(type);
	}
	
	public void loadBoxDrops(){
		for(BoxType boxtype : BoxType.values()) {
			boxDrops.put(boxtype, ItemBuilder.readItemsFromFile(YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + boxtype.getFileName()))));
		}
	}
	
	public void loadBoxDrops(BoxType boxtype){
		boxDrops.remove(boxtype);
		boxDrops.put(boxtype, ItemBuilder.readItemsFromFile(YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + boxtype.getFileName()))));
	}
	
	public void saveBoxDrops(BoxType boxtype, List<BoxItem> boxitems){
		
		FileConfiguration yml = YamlConfiguration.loadConfiguration(new File(getDataFolder() + File.separator + boxtype.getFileName()));
		
		for(String path : yml.getKeys(false)) yml.set(path, null);
		
		int n = 0;
		for(BoxItem boxitem : boxitems){
			
			// r�cup l'item dans la main
			ItemStack item = boxitem.getItem();
			
			// nom : material / amount / nbt 
			String material = item.getType().getKey().toString();
			String amount = String.valueOf(item.getAmount());
			String nbt = CraftItemStack.asNMSCopy(item).hasTag() ? CraftItemStack.asNMSCopy(item).getTag().toString() : "{}";
			String dropRate = new BigDecimal(boxitem.getDropRate()).toPlainString();
			
			
			yml.set("item-"+n, material + "/" + amount + "/" + nbt + "/" + dropRate);
			
			n++;
		}
		
		try {
			yml.save(new File(getDataFolder() + File.separator + boxtype.getFileName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public HashMap<BoxType, List<BoxItem>> getBoxDrops(){
		return boxDrops;
	}
	
	private static HashMap<BoxType, ItemStack> displayKeys = new HashMap<BoxType, ItemStack>(); 
	static {
	
		displayKeys.put(BoxType.SPAWNER, DisplayKeys.displayspawnerBoxKey);
		displayKeys.put(BoxType.DONATOR, DisplayKeys.displaydonatorBoxKey);
		displayKeys.put(BoxType.EMERALD, DisplayKeys.displayemeraldBoxKey);
		displayKeys.put(BoxType.DIAMOND, DisplayKeys.displaydiamondBoxKey);
		displayKeys.put(BoxType.GOLD, DisplayKeys.displaygoldBoxKey);
		displayKeys.put(BoxType.IRON, DisplayKeys.displayironBoxKey);
	
	}
	
	public ItemStack getDisplayKey(BoxType type){
		return displayKeys.get(type);
	}
	
	
}