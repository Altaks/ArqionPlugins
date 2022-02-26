package fr.altaks.helenia.bungeecommands.warns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Unwarn extends Command {

	private Main main;
	
	public Unwarn(String name, Main main) {
		super("unwarn","helenia.unwarn");
		this.main = main;
	}

	// /unwarn <player>
	
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(args.length > 0) {
			
			String playername = args[0];
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playername);
			
			try {
				unwarnPlayer(player.getUniqueId());
				sender.sendMessage(new TextComponent(Main.PREFIX + " " + playername + " a été unwarn"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}

	}
	
	public void unwarnPlayer(UUID playerid) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("DELETE FROM warns WHERE playerid=? ORDER BY `timestamp` DESC LIMIT 1");
		
		statement.setString(1, playerid.toString());
		
		statement.executeUpdate();
	}

}
