package fr.altaks.helenia.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.altaks.helenia.Main;

public class ServerGuardListener implements Listener {

	private Main main;
	
	public ServerGuardListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
	}
	
	@EventHandler
	public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
		
		PlayerInfos infos;
		
		try {
			infos = new PlayerInfos(main, event.getName(), event.getUniqueId(), event.getAddress().toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		if(infos.isbanned) {
			
			if(infos.tempban == null) {
				
				// refuser avec un temps de ban d�finitif
				event.disallow(Result.KICK_BANNED, Main.PREFIX + " §rVous êtes banni du serveur définitivement");
				
			} else {
				
				// refuser en affichant jusqu'� quand il est ban
				if(Instant.now().toEpochMilli() > infos.tempban.toInstant().toEpochMilli()) {
					// il n'est plus banni, il faut d�ban bdd
					event.allow();
					// d�ban bdd
					try {
						unbanFromBDD(event.getUniqueId());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
				} else {
					// s'il est encore banni
					
					Date date = Date.from(Instant.ofEpochMilli(infos.tempban.getTime()));
					DateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.FRANCE);
					
					event.disallow(Result.KICK_BANNED,"\n"+ Main.PREFIX + " §rVous êtes banni du serveur jusqu'à : " + format.format(date));
				}
				
			}
	
		} else if(infos.ismuted){
			
			if(infos.tempmute == null) {
				
				// mute d�finitif
				this.main.getMutingTimestamps().put(event.getUniqueId(), Long.MAX_VALUE);
				
			} else {
				
				// mute temporaire
				this.main.getMutingTimestamps().put(event.getUniqueId(), infos.tempmute.toInstant().toEpochMilli());
				
			}
			
		}
		
	}
	
	public void unbanFromBDD(UUID id) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET banned=? WHERE userid=?");
		statement.setBoolean(0, false);
		statement.setString(1, id.toString());
		
		statement.executeUpdate();
	}
	
	public static class PlayerInfos {
		
		private boolean isbanned, ismuted;
		private Timestamp tempmute, tempban;
		
		public boolean isIsbanned() {
			return isbanned;
		}

		public boolean isIsmuted() {
			return ismuted;
		}

		public Timestamp getTempmute() {
			return tempmute;
		}

		public Timestamp getTempban() {
			return tempban;
		}

		public PlayerInfos(Main main, String pseudo, UUID id, String ip) throws SQLException {
			
			final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
			final PreparedStatement statement = connection.prepareStatement("SELECT banned, muted, tempmute_timestamp, tempban_timestamp FROM playerinfos WHERE userid=?");
			statement.setString(1, id.toString());
			
			final ResultSet result = statement.executeQuery();
			
			if(result.first()) {
				
				isbanned = result.getBoolean("banned");
				ismuted = result.getBoolean("muted");
				
				tempmute = result.getTimestamp("tempmute_timestamp");
				tempban = result.getTimestamp("tempban_timestamp");
				
			} else {
				// ajouter le joueur
				addPlayerToBDD(main, id, pseudo, ip);
				
				isbanned = false;
				ismuted = false;
				
				tempmute = Timestamp.from(Instant.ofEpochSecond(0l));
				tempban = Timestamp.from(Instant.ofEpochSecond(0l));
			}
			
		}
		
		private void addPlayerToBDD(Main main, UUID id, String ip, String pseudo) throws SQLException {
			final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
			final PreparedStatement statement = connection.prepareStatement("INSERT INTO playerinfos VALUES (?,?,?,?,?,?,?,?,?)");
			
			statement.setString(1, id.toString()); // playerid
			
			statement.setBoolean(2, false); // muted
			statement.setBoolean(3, false); // banned
			
			statement.setString(4, "Aucun bannissement à ce jour..."); // lastbanreason
			
			statement.setTimestamp(5, Timestamp.from(Instant.ofEpochSecond(0l))); // tempban timestamp
			statement.setTimestamp(6, Timestamp.from(Instant.ofEpochSecond(0l))); // temp ip ban timestamp
			statement.setTimestamp(7, Timestamp.from(Instant.ofEpochSecond(0l))); // temp mute timestamp
			
			statement.setString(8, ip); // adresse ip
			
			statement.setString(9, pseudo);
			
			statement.executeUpdate();
		}
		
	}

}
