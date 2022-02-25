package fr.altaks.helesky;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Wither;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.helesky.api.MoneyUtil;
import fr.altaks.helesky.commands.AdminSky;
import fr.altaks.helesky.commands.DebugIsland;
import fr.altaks.helesky.commands.GiveItems;
import fr.altaks.helesky.commands.IslandCommand;
import fr.altaks.helesky.core.HeleCommand;
import fr.altaks.helesky.core.islandcore.Island;
import fr.altaks.helesky.core.levelup.LevelingWither;
import fr.altaks.helesky.listener.Menu1Listener;
import fr.altaks.helesky.listener.Menu2Listener;
import fr.altaks.helesky.listener.Menu3Listener;
import fr.altaks.helesky.listener.Menu4Listener;
import fr.altaks.helesky.listener.Menu5Listener;
import fr.altaks.helesky.listener.Menu6Listener;
import fr.altaks.helesky.listener.Menu7Listener;
import fr.altaks.helesky.listener.PlayerBlockInteractionHandler;
import fr.altaks.helesky.listener.PlayerInteractEntityHandler;
import fr.altaks.helesky.listener.WitherInteractListener;
import fr.altaks.helesky.manager.IslandManager;
import fr.altaks.helesky.test.TestClass;

public class Main extends JavaPlugin {
	
	public static final String PREFIX = "�7[�6HeleSky�7]�r \u00BB ", ERROR_PREFIX = "§7[§6HeleSky§7]§c \u00BB ";
	protected static boolean debugMode = false;
	public static final Logger LOGGER = Bukkit.getLogger();
	
	private HeleCommand[] commands = {
			new IslandCommand(this), new AdminSky(this), new DebugIsland(this), new GiveItems()
	};
	
	private MoneyUtil moneyManager;
	private IslandManager islandManager;
	
	public static Location spawn;
	public static int maxPlayerPerIsland = 1;
	
	/**
	 * islandDirectory -> Directory of island data
	 * uuidlinkfile -> File that links players uuid to island id then to their files
	 * schematicsDirectory -> Dossier des schematics
	 */
	private File schematicsDirectory, islandDirectory;
	private File uuidLinkFile, miscDataFile;
	
	private FileConfiguration uuidlinkYml, miscDataYml;
	
	private final HashMap<Integer, Island> islandsFromId = new HashMap<Integer, Island>();
	private final HashMap<LevelingWither, Integer> witherFromIslandId = new HashMap<LevelingWither, Integer>();
	
	private final HashMap<UUID, Integer> islandIDFromUUID = new HashMap<UUID, Integer>();
	
	private final HashMap<UUID, Island> pendingInvites = new HashMap<UUID, Island>();
	private final List<UUID> pendingDeletions = new ArrayList<UUID>();
	
	@Override
	public void onEnable() {
		
		spawn = new Location(Bukkit.getWorld("world"),0,100,0);
		
		saveDefaultConfig();
		if(getConfig().isSet("debug-mode")) debugMode = getConfig().getBoolean("debug-mode");
		if(getConfig().isSet("max-players-per-island")) maxPlayerPerIsland = getConfig().getInt("max-players-per-island");
		
		checkAndLoadFiles();
		moneyManager = new MoneyUtil(this);
		
		// préparation du système de génération des îles
		int lastX = 1_500, lastZ = 1_500, lastId = 0;
		if(miscDataYml.isSet("lastX") && miscDataYml.isSet("lastY") && miscDataYml.isSet("lastId")) {
		
			lastX = miscDataYml.getInt("lastX");
			lastZ = miscDataYml.getInt("lastZ");
			
			lastId = miscDataYml.getInt("lastId");
		
		} else {
			
			miscDataYml.set("lastX", lastX);
			miscDataYml.set("lastZ", lastZ);
			miscDataYml.set("lastId", lastId);
			
			saveMiscDataYml();
		}
		this.islandManager = new IslandManager(this, lastX, lastZ, lastId);
		
		// load islands
		loadIslands();
		System.out.println(Main.PREFIX + "Iles chargées [" + this.islandsFromId.size() + " iles]");
		loadUUIDLinkData();
		System.out.println(Main.PREFIX + "Données des joueurs chargées [" + this.islandIDFromUUID.size() + " joueurs]");
		
		// Load des wither depuis les fichiers depuis la ligne witherdata
		loadWitherData();
		System.out.println(Main.PREFIX + "Données des withers charg�es [" + this.witherFromIslandId.size() + " joueurs]");
		
		// chargement des commandes
		for(HeleCommand command : commands) {
			getCommand(command.getCommand()).setExecutor(command);
			getCommand(command.getCommand()).setTabCompleter(command);
			if(command instanceof Listener) Bukkit.getServer().getPluginManager().registerEvents((Listener) command, this);
		}
		
		// chargement des listeners
		Listener[] listeners = {
				new Menu1Listener(this), 
				new Menu2Listener(this), 
				new Menu3Listener(this), 
				new Menu4Listener(this), 
				new Menu5Listener(this), 
				new Menu5Listener(this), 
				new Menu6Listener(),
				new Menu7Listener(this),
				new PlayerBlockInteractionHandler(this),
				new PlayerInteractEntityHandler(this),
				new WitherInteractListener(this)
		};
		Arrays.asList(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
		
		if(debugMode) {
			this.islandsFromId.values().forEach(island -> System.out.println(island.getName() + " " + island.getOwnerId().toString() + " "));
		}
		getCommand("testcommand").setExecutor(new TestClass());
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
			List<LevelingWither> withersToRemove = new ArrayList<>();
			
			this.witherFromIslandId.keySet().forEach(levelingWither -> {
				Wither wither = (Wither) Bukkit.getEntity(levelingWither.getWitherId());
				if(wither == null) {
					// retirer le wither des data
					withersToRemove.add(levelingWither);
				} else {
					wither.getBossBar().removeAll();
				}
			});
			
			withersToRemove.forEach(wither -> {
				File file = new File(islandDirectory + File.separator + this.getWitherIsland(wither).getId() + ".yml");
				try {
					// �criture du data du wither
					FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
					yml.set("witherData", null);
					
					// sauvegarde
					yml.save(file);
					
				} catch (IOException e) {
					LOGGER.warning(Main.ERROR_PREFIX + "§cUne erreur est survenue lors de la suppresion du wither de l'île §6#" + this.getWitherIsland(wither).getId());
					e.printStackTrace();
				}
				
				this.witherFromIslandId.remove(wither);
			});
		}, 0, 20 * 5);
	}
	
	public void loadUUIDLinkData() {
		for(String uuid : this.uuidlinkYml.getKeys(false)) {
			this.islandIDFromUUID.put(UUID.fromString(uuid), this.uuidlinkYml.getInt(uuid));
		}
	}
	
	public void loadIslands() {
		for(File file : islandDirectory.listFiles()) {
			if(!file.isDirectory()) {
				if(file.getName().endsWith(".yml")) {
					FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
					this.islandsFromId.put(Integer.parseInt(file.getName().replace(".yml", "").replace("\u00A0", "")), Island.fromString(yml.getString("data")));
				}
			}
		}
	}
	
	public void loadWitherData() {
		for(File file : islandDirectory.listFiles()) {
			if(!file.isDirectory()) {
				if(file.getName().endsWith(".yml")) {
					FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
					if(yml.isSet("witherData")) {
						this.witherFromIslandId.put(LevelingWither.fromString(yml.getString("witherData")), Integer.parseInt(file.getName().replace(".yml", "").replace("\u00A0", "")));
					}
				}
			}
		}
	}
	
	public void saveUUIDLinkYml() {
		try {
			uuidlinkYml.save(uuidLinkFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveMiscDataYml() {
		try {
			miscDataYml.save(miscDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void checkAndLoadFiles() {
		
		if(!getDataFolder().exists()) getDataFolder().mkdirs();
		
		schematicsDirectory = new File(getDataFolder() + File.separator + "schematics");
		islandDirectory = new File(getDataFolder() + File.separator + "islandsData");
		
		for(File directory : new File[]{schematicsDirectory, islandDirectory}) if(!directory.exists()) directory.mkdirs();
		
		uuidLinkFile = new File(getDataFolder() + File.separator + "playerIslandsID.yml");
		miscDataFile = new File(getDataFolder() + File.separator + "miscData.yml");
		
		for(File file : new File[] {uuidLinkFile}) {
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		uuidlinkYml = YamlConfiguration.loadConfiguration(uuidLinkFile);
		miscDataYml = YamlConfiguration.loadConfiguration(miscDataFile);
	}

	@Override
	public void onDisable() {
		moneyManager.finish();
		
		saveAllIslands();
		System.out.println(Main.PREFIX + "Iles sauvegardées [" + this.islandsFromId.size() + " iles]");
		saveIslandManagerData();
		System.out.println(Main.PREFIX + "Data du gestionnaire des îles sauvegardé");
		saveUUIDLinkData();
		System.out.println(Main.PREFIX + "Data des joueurs sauvegardé [" + this.islandIDFromUUID.size() + " joueurs]");

		// check de l'�tat des withers
		List<LevelingWither> withersToRemove = new ArrayList<>();
		
		this.witherFromIslandId.keySet().forEach(levelingWither -> {
			Wither wither = (Wither) Bukkit.getEntity(levelingWither.getWitherId());
			if(wither == null) {
				// retirer le wither des data
				withersToRemove.add(levelingWither);
			} else {
				wither.getBossBar().removeAll();
			}
		});
		
		withersToRemove.forEach(wither -> {
			File file = new File(islandDirectory + File.separator + this.getWitherIsland(wither).getId() + ".yml");
			try {
				// �criture du data du wither
				FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
				yml.set("witherData", null);
				
				// sauvegarde
				yml.save(file);
				
			} catch (IOException e) {
				LOGGER.warning(Main.ERROR_PREFIX + "§cUne erreur est survenue lors de la suppresion du wither de l'île §6#" + this.getWitherIsland(wither).getId());
				e.printStackTrace();
			}
			
			this.witherFromIslandId.remove(wither);
		});
		
		// Save le data des wither dans la ligne : witherdata
		saveWithersData();
		
		saveMiscDataYml();
		saveUUIDLinkYml();
		System.out.println(Main.PREFIX + "Fichiers enregistrés");
		
		
	}
	
	public void saveWithersData() {
		for(Entry<LevelingWither, Integer> entry : this.witherFromIslandId.entrySet()) {
			File file = new File(islandDirectory + File.separator + entry.getValue() + ".yml");
			
			try {
				// �criture du data du wither
				FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
				yml.set("witherData", entry.getKey().toString());
				
				// sauvegarde
				yml.save(file);
				
			} catch (IOException e) {
				LOGGER.warning(Main.ERROR_PREFIX + "§cUne erreur est survenue lors de la sauvegarde de du wither de l'île §6#" +entry.getValue());
				e.printStackTrace();
			}
		}
	}
	
	public void saveUUIDLinkData() {
		
		for(Entry<UUID, Integer> entry : this.islandIDFromUUID.entrySet()) {
			this.uuidlinkYml.set(entry.getKey().toString(), entry.getValue());
		}
		
	}
	
	public void saveIslandManagerData() {
		
		miscDataYml.set("lastX", this.islandManager.getLastX());
		miscDataYml.set("lastZ", this.islandManager.getLastZ());
		miscDataYml.set("lastId", this.islandManager.getLastId());
		
		saveMiscDataYml();
	}
	
	public Island getPlayerIsland(UUID player) throws NullPointerException {
		return this.getIslandsFromId().get(this.getIslandIDFromUUID().get(player));
	}
	
	public Island getWitherIsland(LevelingWither levelingWither) {
		return this.getIslandsFromId().get(this.getIslandIdFromWither().get(levelingWither));
	}
	
	public boolean hasIsland(UUID player) {
		return this.getIslandIDFromUUID().containsKey(player);
	}
	
	public void saveAllIslands() {
		for(Entry<Integer, Island> entry : this.islandsFromId.entrySet()) {
			File file = new File(islandDirectory + File.separator + entry.getKey() + ".yml");
			
			try {
				// check de l'existence du fichier
				if(!file.exists()) {
					file.createNewFile();
				}
				
				// �criture de l'ile
				FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
				yml.set("data", entry.getValue().toString());
				
				// sauvegarde
				yml.save(file);
				
			} catch (IOException e) {
				LOGGER.warning(Main.ERROR_PREFIX + "§cUne erreur est survenue lors de la sauvegarde de l'île §6#" +entry.getKey() + "§c du joueur §6" + entry.getValue().getOwnerId());
				e.printStackTrace();
			}
		}
	}

	public MoneyUtil getMoneyManager() {
		return moneyManager;
	}

	public File getSchematicsDirectory() {
		return schematicsDirectory;
	}

	public File getIslandDirectory() {
		return islandDirectory;
	}

	public File getUuidlinkfile() {
		return uuidLinkFile;
	}

	public FileConfiguration getUuidlinkYml() {
		return uuidlinkYml;
	}

	public HashMap<Integer, Island> getIslandsFromId() {
		return islandsFromId;
	}

	public HashMap<UUID, Integer> getIslandIDFromUUID() {
		return islandIDFromUUID;
	}

	public HashMap<UUID, Island> getPendingInvites() {
		return pendingInvites;
	}

	public List<UUID> getPendingDeletions() {
		return pendingDeletions;
	}

	public IslandManager getIslandManager() {
		return islandManager;
	}

	public HashMap<LevelingWither, Integer> getIslandIdFromWither() {
		return witherFromIslandId;
	}

	public boolean hasWither(int id) {
		return this.witherFromIslandId.containsValue(id);
	}
}
