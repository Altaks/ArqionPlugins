package fr.altaks.helenia.bungeecommands.ip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Unbanip extends Command {

	private Main main;
	
	public Unbanip(String name, Main main) {
		super("unbanip","helenia.unbanip");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(args.length > 0) {
			
			String pseudo = args[0];
			
			try {
				String ip = getIp(pseudo);
				unbanIp(ip);
				sender.sendMessage(new TextComponent(Main.PREFIX + " §6L'adresse ip : §e" + ip + "§6 a bien été débannie §r("+pseudo+")"));
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
	
	public void unbanIp(String ip) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE SET banned=? WHERE ip=?");
		
		statement.setBoolean(1, false);
		statement.setString(2, ip);
		
		statement.executeUpdate();
	}

}
