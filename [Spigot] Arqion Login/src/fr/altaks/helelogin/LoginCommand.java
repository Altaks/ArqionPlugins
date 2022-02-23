package fr.altaks.helelogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class LoginCommand implements CommandExecutor {
	
	private Main main;
	
	public LoginCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("login") && sender instanceof Player && args.length > 0) {
			
			String playername = ((Player)sender).getName();
			String password = args[0];
			
			ResultSet set= null;
			try {
				set = getLinkedSetInBDD(playername);
			} catch (SQLException e1) {
				sender.sendMessage(Main.PREFIX + "§cUne erreur est survenue lors de la récupération des données de votre utilisateur, veuillez en informer le staff pour qu'il puisse régler ce problème au plus vite");
				e1.printStackTrace();
				return true;
			}
			
			try {
				if(set.next()) {
					
					String realpassword = set.getString("password");
					
					if(password.equals(realpassword)) {
						sender.sendMessage(Main.PREFIX + "§6Connection à votre compte effectuée avec succès !");
						Bukkit.getScheduler().cancelTask(main.getSpamid().get(((Player)sender).getUniqueId()));
						sendPlayerToLobby((Player)sender);
					} else {
						sender.sendMessage(Main.PREFIX + "§cCeci n'est pas votre mot de passe !");
						return true;
					}
				} else {
					// pas trouvé
					sender.sendMessage(Main.PREFIX + "§cVotre compte n'existe pas !\n§6Veuillez utiliser /register <mot-de-passe> <mot-de-passe> pour vous créer un compte");
					return true;
				}
			} catch (SQLException e) {
				sender.sendMessage(Main.PREFIX + "§cUne erreur est survenue lors de la récupération des données de votre compte, veuillez en informer le staff pour qu'il puisse régler ce problème au plus vite");
				e.printStackTrace();
			}
			
			
			
		}
		return false;
	}
	
	private void sendPlayerToLobby(Player player) {

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("Connect");
		out.writeUTF("Lobby");
		
		player.sendPluginMessage(main, "BungeeCord", out.toByteArray());
		
	}
	
	public ResultSet getLinkedSetInBDD(String playername) throws SQLException {
		
		final Connection connection = main.getDbManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT password FROM logincredentials WHERE playername=?");
		
		statement.setString(1, playername);
		
		return statement.executeQuery();
	}
}
