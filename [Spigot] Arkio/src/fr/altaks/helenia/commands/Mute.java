package fr.altaks.helenia.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helenia.Main;

public class Mute implements CommandExecutor {
	
	private Main main;
	
	public Mute(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length >= 2) {
			
			String targetname = args[0];
			Player target = Bukkit.getPlayer(targetname);
			if(target == null) {
				sender.sendMessage(Main.PREFIX + "Ce joueur n'est pas sur le serveur");
				return true;
			}
			
			try {
				mutePlayerNow(target.getUniqueId());
				main.getMutingTimestamps().put(target.getUniqueId(), Long.MAX_VALUE);
				sender.sendMessage(Main.PREFIX + " §e" + targetname + "§6 est maintenant muet/muette");
			} catch (SQLException e) {
				e.printStackTrace();
				sender.sendMessage(Main.PREFIX + "§c Une erreur est survenue lors de la commande : SQLException.");
			}
			
			return true;
			
		}
		
		return false;
	}
	
	public void mutePlayerNow(UUID id) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET muted=?, tempmute_timestamp=? WHERE userid=?");
		
		statement.setBoolean(1, true);
		statement.setTimestamp(2, null);
		
		statement.setString(3, id.toString());
		
		statement.executeUpdate();
	}

}
