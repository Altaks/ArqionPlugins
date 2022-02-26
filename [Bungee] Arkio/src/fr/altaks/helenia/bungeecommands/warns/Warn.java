package fr.altaks.helenia.bungeecommands.warns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.StringJoiner;
import java.util.UUID;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Warn extends Command {

	private Main main;
	
	public Warn(String name, Main main) {
		super("warn","helenia.warn");
		this.main = main;
	}

	// /warn <player> <reason>
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(args.length >= 2 && sender instanceof ProxiedPlayer) {
			
			String playername = args[0];
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playername);
			
			if(player == null) {
				sender.sendMessage(new TextComponent(Main.PREFIX + " §e" + playername + "§c n'est pas présent"));
				return;
			}
			
			StringJoiner joiner = new StringJoiner(" ");
			for(String arg : args) {
				if(arg != args[0]) {
					joiner.add(arg);
				}
			}
			
			String reason = joiner.toString();
			
			UUID moderatorid = ((ProxiedPlayer)sender).getUniqueId(), playerid = player.getUniqueId();
			String moderatorname = ((ProxiedPlayer)sender).getName();
			
			Timestamp now = Timestamp.from(Instant.now());
			
			String playerserver = player.getServer().getInfo().getName();
			
			try {
				warnPlayer(moderatorid, playerid, moderatorname, playername, reason, now, playerserver);
				sender.sendMessage(new TextComponent(Main.PREFIX + "§e" + playername + " §6a été warn pour : §e" + reason));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
			
		}

	}
	
	public void warnPlayer(UUID moderatorid, UUID playerid, String moderatorname, String playername, String reason, Timestamp now, String playerserver) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("INSERT INTO warns VALUES (?,?,?,?,?,?,?)");
		
		statement.setString(1, moderatorid.toString());
		statement.setString(2, playerid.toString());
		
		statement.setString(3, moderatorname);
		statement.setString(4, playername);
		
		statement.setString(5, reason);
		statement.setTimestamp(6, now);
		statement.setString(7, playerserver);
		
		statement.executeUpdate();
	}

}
