package fr.nowayy.helecore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import fr.nowayy.helecore.Commands.basic.CreateKitCommand;
import fr.nowayy.helecore.Commands.basic.DelKitCommand;
import fr.nowayy.helecore.Commands.basic.FeedCommand;
import fr.nowayy.helecore.Commands.basic.FlySpeedCommand;
import fr.nowayy.helecore.Commands.basic.GamemodeCommand;
import fr.nowayy.helecore.Commands.basic.HealCommand;
import fr.nowayy.helecore.Commands.basic.HomeCommand;
import fr.nowayy.helecore.Commands.basic.KitCommand;
import fr.nowayy.helecore.Commands.basic.SetSpawnCommand;
import fr.nowayy.helecore.Commands.basic.SpawnCommand;
import fr.nowayy.helecore.Commands.basic.SpeedCommand;
import fr.nowayy.helecore.Commands.basic.WalkSpeedCommand;
import fr.nowayy.helecore.Commands.moderation.AnnounceCommand;
import fr.nowayy.helecore.Commands.moderation.CraftCommand;
import fr.nowayy.helecore.Commands.moderation.EnchantCommand;
import fr.nowayy.helecore.Commands.moderation.FlyCommand;
import fr.nowayy.helecore.Commands.moderation.FurnaceCommand;
import fr.nowayy.helecore.Commands.moderation.GodModCommand;
import fr.nowayy.helecore.Commands.moderation.KillAllCommand;
import fr.nowayy.helecore.Commands.moderation.OpenEnderChestCommand;
import fr.nowayy.helecore.Commands.moderation.OpenInventoryCommand;
import fr.nowayy.helecore.Commands.moderation.PlayerInfoCommand;
import fr.nowayy.helecore.Commands.moderation.WarpsCommand;
import fr.nowayy.helecore.Commands.moderation.WorldAnnounceCommand;
import fr.nowayy.helecore.utils.Messages;
import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTTagCompound;

public class Main extends JavaPlugin {
	
	public static final String prefix = "§r[§8Hele§cCore§r] ";
	public static final String Error_Prefix = "§r[§8Hele§cError§r] ";
	public static final String Announce_Prefix = "§r[§8Hele§cAnnonce§r] ";
	
	@Override
	public void onEnable() {
		System.out.println(Messages.Enabling_Plugin);
		getConfig().options().copyDefaults(true);
	    saveConfig();
		registerEvents();
	}
	
	@Override
	public void onDisable() {
		System.out.println(Messages.Disabling_Plugin);
	}
	
	public void registerEvents() {
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
		getCommand("kit").setExecutor(new KitCommand(this));
		getCommand("createKit").setExecutor(new CreateKitCommand(this));
		getCommand("delKit").setExecutor(new DelKitCommand(this));
		getCommand("save").setExecutor(new CreateKitCommand(this));
		getCommand("gamemode").setExecutor(new GamemodeCommand());
		getCommand("flyspeed").setExecutor(new FlySpeedCommand());
		getCommand("walkspeed").setExecutor(new WalkSpeedCommand());
		getCommand("home").setExecutor(new HomeCommand(this));
		getCommand("sethome").setExecutor(new HomeCommand(this));
		getCommand("delhome").setExecutor(new HomeCommand(this));
		getCommand("homes").setExecutor(new HomeCommand(this));
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("announce").setExecutor(new AnnounceCommand());
		getCommand("godmod").setExecutor(new GodModCommand());
		getCommand("openec").setExecutor(new OpenEnderChestCommand());
		getCommand("openinv").setExecutor(new OpenInventoryCommand());
		getCommand("worldannounce").setExecutor(new WorldAnnounceCommand());
		getCommand("playerinfo").setExecutor(new PlayerInfoCommand());
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("feed").setExecutor(new FeedCommand());
		getCommand("speed").setExecutor(new SpeedCommand());
		getCommand("killall").setExecutor(new KillAllCommand(this));
		getCommand("enchant").setExecutor(new EnchantCommand());
		getCommand("craft").setExecutor(new CraftCommand());
		getCommand("furnace").setExecutor(new FurnaceCommand());
		getCommand("warps").setExecutor(new WarpsCommand(this));
		getCommand("warp").setExecutor(new WarpsCommand(this));
		getCommand("setwarp").setExecutor(new WarpsCommand(this));
		getCommand("delwarp").setExecutor(new WarpsCommand(this));
		
		getCommand("enchant").setTabCompleter(new EnchantCommand());
		getCommand("killall").setTabCompleter(new KillAllCommand(this));
		getCommand("home").setTabCompleter(new HomeCommand(this));
		getCommand("flyspeed").setTabCompleter(new FlySpeedCommand());
		getCommand("gamemode").setTabCompleter(new GamemodeCommand());
		getCommand("walkspeed").setTabCompleter(new WalkSpeedCommand());
		getCommand("worldannounce").setTabCompleter(new WorldAnnounceCommand());

	}
	
	public void registerKit(Player player, YamlConfiguration yml, File file){
	    
//	    for(int i = 0; i < contents.length; i++) {
//	        obtenir le type
//	    	
//	        String type = "minecraft:jungle_planks";
//	        int amount = contents[i].getAmount();
//
//	        String nbt = "{}";
//
//	        yml.set("id-"+i+"type", type);
//	        yml.set("id-"+i+"amount", amount);
//	        yml.set("id-"+i+"nbt", nbt);
//	    }
		int n = 0;
		for(ItemStack item : player.getInventory().getContents()) {
			
			if(item == null) {
				continue;
			}
			
			String type = item.getType().getKey().toString();
			int amount = item.getAmount();
			String nbt = "{}";
			
			try {
				nbt = CraftItemStack.asNMSCopy(item).getTag().toString();
			} catch (NullPointerException e) {}

			yml.set("id-"+n+".type", type);
			yml.set("id-"+n+".amount", amount);
			yml.set("id-"+n+".nbt", nbt);
			
			n++;
		}

	    try {
	        yml.save(file);
	    } catch(IOException e){
	        e.printStackTrace();
	    }

	  
	}

	  public ItemStack[] getKit(FileConfiguration yml) {
		  
		  List<ItemStack> items = new ArrayList<ItemStack>();

	      for(String s : yml.getKeys(false)) { // pour chaque id
	    	 
	    	if(s == null) continue;

	        String stringType = yml.getString(s + ".type");
	        int amount = yml.getInt(s + ".amount");
	        String strNbt = yml.getString(s + ".nbt");
	        
	        Material material = Material.DIRT;
	        for(Material m : Material.values()) {
	        	if(m.getKey().toString().equalsIgnoreCase(stringType)) {
	        		material = m;
	        	}
	        }

	        NBTTagCompound compound = new NBTTagCompound();
	        
	        try {
				compound = MojangsonParser.parse(strNbt);
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
			}
	        
	        ItemStack item = new ItemStack(material, amount);
	        
	        // nbt adding
	        
	        compound.setString("id", stringType);
	        compound.setInt("Count", amount);
	        
	        net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
	        nmsItem.setTag(compound);
	        item = CraftItemStack.asBukkitCopy(nmsItem);
	        
	        items.add(item);
	      }
	      
	     return items.toArray(new ItemStack[items.size()]);
	  }
	  }
	  
	 
	  


