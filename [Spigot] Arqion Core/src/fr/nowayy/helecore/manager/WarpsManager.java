package fr.nowayy.helecore.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.nowayy.helecore.Main;

@SuppressWarnings("unused")
public class WarpsManager {

	private Main main;
	private File warpfile;
	private FileConfiguration yml;
	private OfflinePlayer player;
	
	public WarpsManager(Main main, OfflinePlayer player) {
		this.main = main;
		this.player = player;
		{
			warpfile = new File(main.getDataFolder() + File.separator + "warps.yml");
			if (!warpfile.exists()) {
				try {
					warpfile.createNewFile();
				} catch(IOException e) {
					System.out.println("[HeleCore] Création du fichier \"warps.yml\"");
				}
			}
			yml = YamlConfiguration.loadConfiguration(warpfile);
		}
		
	}
	
	public void saveYml() {
		try {
			yml.save(warpfile);
		} catch (IOException e) {
			System.out.println("[HeleError] Une erreur est survenue lors de la sauvegarde du fichier \"warps.yml\".");
		}
		
	}
	
	public void setWarp(Location location, String name) {
		String path = name + ".";
		yml.set(path + "world", 	location.getWorld().getName());
		
		yml.set(path + "x", 		location.getX());
		yml.set(path + "y", 		location.getY());
		yml.set(path + "z", 		location.getZ());
		
		yml.set(path + "pitch",		location.getPitch());
		yml.set(path+"yaw", 		location.getYaw());
		saveYml();
	}
	
	public void delWarp(String name) {
		yml.set(name, null);
		saveYml();
	}
	
	public boolean isWarped(String warpName) {
		return yml.isSet(warpName);
	}
	
	public Location getWarp(String name) {
		if (yml.isSet(name)) {
			double x, y, z;
			float pitch, yaw;
			String path = name + ".";
			World world = null;
			
			try {
				world = Bukkit.getWorld(yml.getString(path + "world"));
				
				x = yml.getDouble(path + "x");
				y = yml.getDouble(path + "y");
				z = yml.getDouble(path + "z");
				
				pitch = (float) yml.getDouble(path + "pitch");
				yaw = (float) yml.getDouble(path + "yaw");
				return new Location(world, x, y, z, yaw, pitch);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String[] getWarpsList() {
		return yml.getKeys(false).toArray(new String[yml.getKeys(false).size()]);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
