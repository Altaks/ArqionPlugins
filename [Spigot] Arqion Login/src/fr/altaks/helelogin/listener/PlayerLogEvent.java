package fr.altaks.helelogin.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.altaks.helelogin.Main;

public class PlayerLogEvent implements Listener {
	
	private Main main;
	
	public PlayerLogEvent(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		
		String playername = event.getPlayer().getName();

		BukkitTask task = new BukkitRunnable() {
			
			int n = 0;
						
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(Bukkit.getOfflinePlayer(playername) == null || !Bukkit.getOfflinePlayer(playername).isOnline()) cancel();
				
				if(n > 30) {
					Bukkit.getScheduler().runTask(main, () -> event.getPlayer().kickPlayer(Main.PREFIX + "§cVous deviez impérativement vous connecter à un compte jeune padawan !"));
					cancel();
				}
				
				try {
					if(playerInDB(playername)) {
						event.getPlayer().sendMessage(Main.PREFIX + "§6Veuillez vous connecter sur votre compte ! §7/login <mot-de-passe>");
					} else {
						event.getPlayer().sendMessage(Main.PREFIX + "§6Veuillez enregistrer votre compte ! §7/register <mot-de-passe> <mot-de-passe>");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				n++;
			}
		}.runTaskTimerAsynchronously(main, 0, 5 * 20);
		
		main.getSpamid().put(event.getPlayer().getUniqueId(), task.getTaskId());
	}
	
	public boolean playerInDB(String playername) throws SQLException {
		
		final Connection connection = main.getDbManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT password FROM logincredentials WHERE playername=?");
		
		statement.setString(1, playername);
		
		final ResultSet set = statement.executeQuery();
		
		return set.next();
	}

}
