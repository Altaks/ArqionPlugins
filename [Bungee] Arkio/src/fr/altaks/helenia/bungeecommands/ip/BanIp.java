package fr.altaks.helenia.bungeecommands.ip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BanIp extends Command {
	
	private Main main;
	
	public BanIp(String name, Main main) {
		super("banip","helenia.banip");
		this.main = main;
	}

	//banip pseudo
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(args.length >= 2) {
			
			String pseudo = args[0];
			
			StringJoiner reason = new StringJoiner(" ");
			
			for(String s : args) {
				if(s != args[0]) reason.add(s);
			}
			
			try {
				String ip = getIp(pseudo);
				banip(ip, reason.toString());
				sender.sendMessage(new TextComponent(Main.PREFIX + "§6Adresse IP §e" + ip + "§6 bannie du serveur §r(" + pseudo + ")"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

	}
	
	public String getIp(String pseudo) throws SQLException {

		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT ip FROM playerinfos WHERE playername=?");
		
		statement.setString(1, pseudo);
		final ResultSet result = statement.executeQuery();
		result.first();
		
		return result.getString("ip");
	}
	
	public void banip(String ip, String reason) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET banned=?, lastbanreason=? WHERE ip=?");
		
		statement.setBoolean(1, true);
		statement.setString(2, reason);
		
		statement.setString(3, ip);
		
		statement.executeUpdate();
		
	}

}
