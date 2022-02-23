package fr.altaks.helelogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class RegisterCommand implements CommandExecutor {

	private Main main;
	
	public RegisterCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /register <password> <password>
		if(cmd.getName().equalsIgnoreCase("register") && sender instanceof Player && args.length >= 2) {
			
			Player player = (Player)sender;
			String playername = player.getName();
			
			// test si identique au pseudo
			// test si confirmation identique � 1er arg
			// test si d�j� pr�sent dans la bdd
			// insertion dans la bdd
			// tp du joueur dans le lobby
			
			String password = args[0], confirmation = args[1];
			
			if(!password.equals(confirmation)) {
				player.sendMessage(Main.PREFIX + "�cLes mots de passe ne correspondent pas !");
				return false;
			} else if(password.equals(playername)) {
				player.sendMessage(Main.PREFIX + "�cVous ne pouvez pas choisir un mot de passe similaire � votre pseudonyme !");
				return true;
			}
			
			try {
				if(isAlreadyInBDD(playername)) {
					player.sendMessage(Main.PREFIX + "�cVous avez d�j� un compte ! Utilisez /login pour vous connecter");
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				registerPlayerInBdd(playername, password);
				player.sendMessage(Main.PREFIX + "�cVotre compte a bien �t� cr�� !");
				sendPlayerToLobby(player);
			} catch (SQLException e) {
				player.sendMessage(Main.PREFIX + "�cVotre cr�ation de compte a eu un probl�me, veuillez contacter le staff afin d'obtenir plus d'information");
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

	public boolean isAlreadyInBDD(String playername) throws SQLException {
		
		final Connection connection = main.getDbManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT password FROM logincredentials WHERE playername=?");
		
		statement.setString(1, playername);
		
		final ResultSet result = statement.executeQuery();
		return result.next();
	}
	
	
	public void registerPlayerInBdd(String playername, String password) throws SQLException {
		
		final Connection connection = main.getDbManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("INSERT INTO logincredentials VALUES (?,?)");
		
		statement.setString(1, playername);
		statement.setString(2, password);
		
		statement.executeUpdate();
	}

}
