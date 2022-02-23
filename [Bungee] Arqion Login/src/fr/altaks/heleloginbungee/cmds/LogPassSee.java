package fr.altaks.heleloginbungee.cmds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.altaks.heleloginbungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class LogPassSee extends Command {

	private Main main;
	
	public LogPassSee(String name, Main main) {
		super(name);
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("admin.logpasssee") && args.length == 1) {
			
			String playername = args[0];
			try {
				String password = getPasswordFromPlayerName(playername);
				sender.sendMessage(new TextComponent(Main.PREFIX + "§6Le mot de passe du joueur §e" + playername + "§6 est : §e" + password +"§6."));
			} catch (NullPointerException e) {
				sender.sendMessage(new TextComponent(Main.PREFIX + "§cLe compte de ce joueur n'existe pas dans la base de données !"));
				e.printStackTrace();
			} catch (SQLException e) {
				sender.sendMessage(new TextComponent(Main.PREFIX + "§cUne erreur est survenue du côté de la base de donnée, l'erreur est affichée en console"));
				e.printStackTrace();
			}
			
		}

	}
	
	public String getPasswordFromPlayerName(String playername) throws SQLException, NullPointerException {
		
		final Connection connection = main.getDbManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT password FROM logincredentials WHERE playername=?");
		
		statement.setString(1, playername);
		
		final ResultSet result = statement.executeQuery();
		
		if(result.next()) {
			return result.getString("password");
		} else throw new NullPointerException("Le compte du joueur n'existe pas !");
	}

}
