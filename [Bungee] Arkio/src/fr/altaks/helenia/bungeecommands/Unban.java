package fr.altaks.helenia.bungeecommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Unban extends Command {
	
	private Main main;

	public Unban(String name, Main main) {
		super("unban","helenia.unban");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(args.length > 0) {
			try {
				unbanPlayer(args[0]);
				sender.sendMessage(new TextComponent(Main.PREFIX + " " + args[0] + " à bien été débanni(e)"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void unbanPlayer(String name) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET banned=? WHERE playername=?");
		
		statement.setBoolean(1, false);
		statement.setString(2, name);
		
		statement.executeUpdate();
		
	}

}
