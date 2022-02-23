package fr.altaks.helenia.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.altaks.helenia.Main;

public class Unmute implements CommandExecutor {

	private Main main;
	
	public Unmute(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length > 0) {
			
			String targetname = args[0];
			OfflinePlayer target = Bukkit.getOfflinePlayer(targetname);
			
			if(target == null) {
				sender.sendMessage(Main.NOTFOUND + " " + targetname + " n'est jamais venu(e) sur le serveur");
				return false;
			}
			
			try {
				unmutePlayer(target.getUniqueId());
				main.getMutingTimestamps().remove(target.getUniqueId());
				sender.sendMessage(Main.PREFIX + " " + targetname + " n'est maintenant plus muet/te");
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	public void unmutePlayer(UUID id) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET muted=? WHERE userid=?");
		
		statement.setBoolean(1, false);
		statement.setString(2, id.toString());
		
		statement.executeUpdate();
	}

}
