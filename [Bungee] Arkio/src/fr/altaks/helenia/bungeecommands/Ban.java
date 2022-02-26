package fr.altaks.helenia.bungeecommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringJoiner;
import java.util.UUID;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Ban extends Command {

	private Main main;
	
	public Ban(String name, Main main) {
		super("ban","helenia.ban");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(args.length >= 2) {
			
			String targetname = args[0];
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(targetname);
			if(target == null) {
				sender.sendMessage(new TextComponent(Main.PREFIX + "Ce joueur n'est pas sur le serveur"));
				return;
			}
			
			StringJoiner joiner = new StringJoiner(" ");
			for(String str : args) {
				if(str != args[0]) {
					joiner.add(str);
				}
			}
			String reason = joiner.toString();
			
			// update in db the ban state of the player for the reason / banned|tempbantempstamp -> current + lastbanreason -> reason du StringJoiner
			
			try {
				banPlayerNow(target.getUniqueId(), reason);
				target.disconnect(new TextComponent(Main.PREFIX + "§cVous avez étébanni par §6" + sender.getName() + " §c!"));
			} catch (SQLException e) {
				e.printStackTrace();
				sender.sendMessage(new TextComponent(Main.PREFIX + "§c Une erreur est survenue lors de la commande : SQLException."));
			}
		}
		
	}
	
	public void banPlayerNow(UUID id, String reason) throws SQLException {
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET banned=?, tempban_timestamp=?, lastbanreason=? WHERE userid=?");
		
		statement.setBoolean(1, true);
		statement.setTimestamp(2, null);
		statement.setString(3, reason);
		
		statement.setString(4, id.toString());
		
		statement.executeUpdate();
	}

}
