package fr.altaks.helenia.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.altaks.helenia.Main;

public class ChatGuardListener implements Listener {
	
	private Main main;
	
	public ChatGuardListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		if(main.getMutingTimestamps().containsKey(player.getUniqueId())) {
			if(System.currentTimeMillis() > main.getMutingTimestamps().get(player.getUniqueId())) {
				// demute bdd et server
				
				try {
					unmuteFromBDD(player.getUniqueId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				main.getMutingTimestamps().remove(player.getUniqueId());
			} else {
				event.setCancelled(true);
				player.sendMessage(Main.PREFIX + " Vous êtes muet pour le moment, vous ne pouvez pas parler.");
			}
		} 
	}
	
	public void unmuteFromBDD(UUID uuid) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET muted=? WHERE userid=?");
		
		statement.setBoolean(1, false);
		statement.setString(2, uuid.toString());
		
		statement.executeUpdate();
	}

}
