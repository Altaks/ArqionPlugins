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

public class Clearwarn extends Command {

	private Main main;
	
	public Clearwarn(String name, Main main) {
		super("clearwarn","helenia.clearwarn");
		this.main = main;
	}

	// /clearwarn <player>
	
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(args.length > 0) {
			
			String playername = args[0];
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playername);
			
			try {
				clearwarnPlayer(player.getUniqueId());
				sender.sendMessage(new TextComponent(Main.PREFIX + " " + playername + " a été clearwarn"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}

	}
	
	private void clearwarnPlayer(UUID id) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("DELETE FROM warns WHERE playerid=?");
		
		statement.setString(1, id.toString());
		
		statement.executeUpdate();
		
	}

}
