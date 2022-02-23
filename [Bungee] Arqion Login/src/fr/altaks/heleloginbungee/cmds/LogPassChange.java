package fr.altaks.heleloginbungee.cmds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.altaks.heleloginbungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class LogPassChange extends Command {

	private Main main;
	
	public LogPassChange(String name, Main main) {
		super(name);
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("admin.logpasschange") && args.length == 3) {
			
			String first = args[0], second = args[1];
			
			if(first.equals(second)) {
				try {
					changePasswordOfPlayer(first, args[2]);
				} catch (SQLException e) {
					sender.sendMessage(new TextComponent(Main.PREFIX + "§cUne erreur est survenue lors du changement du mot de passe du joueur, l'erreur est affichée en console."));
					e.printStackTrace();
					return;
				}
				sender.sendMessage(new TextComponent(Main.PREFIX + "§6Le mot de passe du compte §e" + args[2] + " §6est maintenant changé !"));
			} else {
				sender.sendMessage(new TextComponent(Main.PREFIX + "§cLes mots de passe ne correspondent pas !"));
			}
			
		}

	}
	
	public void changePasswordOfPlayer(String password, String playername) throws SQLException {
		
		final Connection connection = main.getDbManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE logincredentials SET password=? WHERE playername=?");
		
		statement.setString(1, password);
		statement.setString(2, playername);
		
		statement.executeUpdate();
	}

}
