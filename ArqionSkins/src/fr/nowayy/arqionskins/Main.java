package fr.nowayy.arqionskins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_15_R1.PlayerConnection;

public class Main extends JavaPlugin implements Listener {
		
	@Override
	public void onEnable(){
	
		Bukkit.getPluginManager().registerEvents(this, this);
		
	}

	
	@EventHandler
	public void onPlayerPreLoginEvent(PlayerJoinEvent event){
				
		Player player = event.getPlayer();
		
		CraftPlayer craftPlayer = ((CraftPlayer) player);
		
	    GameProfile profile = craftPlayer.getHandle().getProfile();
	    PlayerConnection connection = craftPlayer.getHandle().playerConnection;
	    
	    String[] skinData = queryUserSkin(player.getName());
	    
	    connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle()));
	    
	    profile.getProperties().removeAll("textures");
	    profile.getProperties().put("textures", new Property("textures", skinData[0], skinData[1]));
	    
	    connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle()));	   	
	   
	}
	
	
	public static void main(String[] args) {

		String[] value = queryUserSkin("Altaks");
		System.out.println(value[0] + " " + value[1]);
		
	}
	
	private static String queryServerID(String pseudo){
		
		
		try {
			
			URL obj = new URL("https://api.mojang.com/users/profiles/minecraft/" + pseudo);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JsonObject playerIdJsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
			
			return playerIdJsonObject.get("id").getAsString();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static String[] queryUserSkin(String pseudo){
		
		String playerID = queryServerID(pseudo);
		
		try {
			
			URL obj = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + playerID + "?unsigned=false");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			JsonObject playerJsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
			
			return new String[]{
				playerJsonObject.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString(),
				playerJsonObject.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("signature").getAsString()
			};
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return new String[]{};		
	}
}
