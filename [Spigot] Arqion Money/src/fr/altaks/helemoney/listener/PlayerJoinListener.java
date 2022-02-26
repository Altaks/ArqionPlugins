package fr.altaks.helemoney.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.MoneyUtil;

public class PlayerJoinListener implements Listener {
	
	private Main main;
	
	public PlayerJoinListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			
			try {
				if(!(hasPlayedBefore(player.getUniqueId()))) {
					insertPlayerInBDD(player.getUniqueId());
				}
			} catch (SQLException e) {
				player.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue lors de votre ajout en base de donnée, ceci va nuire a votre expérience de jeu. Veuillez prévenir le staff afin qu'il puisse régler ce problème au plus vite");
				e.printStackTrace();
			}
			
		});
		
	}

	public boolean hasPlayedBefore(UUID id) throws SQLException {
		
		final Connection connection = main.getMoneyUtil().getDBConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT money FROM player_money WHERE userid=?"); // (userid, money, bank_money, bank_tier)
		
		statement.setString(1, id.toString());
		
		final ResultSet result = statement.executeQuery();
		if(result.next()) {
			return true;
		}
		return false;
	}
	
	public void insertPlayerInBDD(UUID id) throws SQLException {
		
		final Connection connection = main.getMoneyUtil().getDBConnection();
		final PreparedStatement statement = connection.prepareStatement("INSERT INTO player_money VALUES (?,?,?,?)"); // (userid, money, bank_money, bank_tier)
		
		statement.setString(1, id.toString());
		statement.setDouble(2, 0.0d);
		statement.setDouble(3, 0.0d);
		statement.setInt(4, 1);
		
		statement.executeUpdate();
	}
}
