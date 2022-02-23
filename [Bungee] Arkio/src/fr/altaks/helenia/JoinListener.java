package fr.altaks.helenia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {
	
	private Main main;
	
	public JoinListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PostLoginEvent event) {
		
		ProxiedPlayer player = event.getPlayer();
				
		// check in db if the player exists already
		// if not then add it
		
		UUID id = player.getUniqueId();
		
		try {
			ResultSet dbResult = queryForPlayer(id);
			
			if(!dbResult.next()) {
				// Si le joueur n'est pas dans la bdd alors :
				addPlayerToDB(player);
			} else {
				// Si le joueur est dans la bdd alors : 
				
				// test joueur banni ou alors tempban timestamp < systemtime -> refus de connexion
				if(isCurrentlyBanned(player)) {
					event.getPlayer().disconnect(new TextComponent(Main.PREFIX + "§cVous êtes banni de ce serveur !"));
					return;
				} else {
					event.getPlayer().sendMessage(new TextComponent(Main.PREFIX + "§6Bienvenue à toi sur Helenium, cher\u00B7e " + player.getName()));
					return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private ResultSet queryForPlayer(UUID id) throws SQLException {
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT ip FROM playerinfos WHERE userid=?");
		
		statement.setString(1, id.toString());
		return statement.executeQuery();
	}
	
	private void addPlayerToDB(ProxiedPlayer player) throws SQLException {
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("INSERT INTO playerinfos VALUES (?,?,?,?,?,?,?,?,?)");
		
		statement.setString(1, player.getUniqueId().toString()); // playerid
		
		statement.setBoolean(2, false); // muted
		statement.setBoolean(3, false); // banned
		
		statement.setString(4, "Aucun bannissement à ce jour..."); // lastbanreason
		
		statement.setTimestamp(5, null); // tempban timestamp
		statement.setTimestamp(6, null); // temp ip ban timestamp
		statement.setTimestamp(7, null); // temp mute timestamp
		
		statement.setString(8, player.getSocketAddress().toString().split(":")[0].replace("/", "")); // adresse ip
		
		statement.setString(9, player.getName());
		
		statement.executeUpdate();
	}
	
	private boolean isCurrentlyBanned(ProxiedPlayer player) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT banned, tempipban_timestamp FROM playerinfos WHERE ip=?");
		
		statement.setString(1, player.getSocketAddress().toString().split(":")[0].replace("/", ""));
		
		final ResultSet result = statement.executeQuery();
		
		result.first();
		
		if(result.getBoolean("banned")) {
			if(result.getTimestamp("tempipban_timestamp") != null) {
				return result.getTimestamp("tempipban_timestamp").toInstant().toEpochMilli() < System.currentTimeMillis(); 
			}
			return true;
		}
		return false;
	}

}
