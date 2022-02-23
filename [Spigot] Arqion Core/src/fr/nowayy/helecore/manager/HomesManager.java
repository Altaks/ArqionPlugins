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
public class HomesManager {

	private Main main;
	private File homefile;
	private FileConfiguration yml;
	private OfflinePlayer player;
	
	public HomesManager(Main main, OfflinePlayer player) {
		this.main = main;
		this.player = player;
		{
			homefile = new File(main.getDataFolder() + File.separator + "homes/"+ player.getUniqueId() + ".yml");
			if (!homefile.exists()) {
				try {
					homefile.createNewFile();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			yml = YamlConfiguration.loadConfiguration(homefile);
		}
		
	}
	
	public void saveYml() {
		try {
			yml.save(homefile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setHome(Location location, String name) {
		String path = name + ".";
		yml.set(path + "world", 	location.getWorld().getName());
		
		yml.set(path + "x", 		location.getX());
		yml.set(path + "y", 		location.getY());
		yml.set(path + "z", 		location.getZ());
		
		yml.set(path + "pitch",		location.getPitch());
		yml.set(path+"yaw", 		location.getYaw());
		saveYml();
	}
	
	public void delHome(String name) {
		yml.set(name, null);
		saveYml();
	}
	
	public boolean isHomed(String homeName) {
		return yml.isSet(homeName);
	}
	
	public Location getHome(String name) {
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
	
	public String[] getHomesList() {
		return yml.getKeys(false).toArray(new String[yml.getKeys(false).size()]);
	}
	
	public String[] getHomeListFromPlayer(OfflinePlayer p) {
		return (String[]) YamlConfiguration.loadConfiguration(new File(main.getDataFolder() + File.separator + homefile + p.getUniqueId() + ".yml")).getKeys(false).toArray();		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
