package fr.altaks.helenia.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helenia.Main;

public class Tempmute implements CommandExecutor {
	
	private Main main;
	
	public Tempmute(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length >= 2) {
			
			String targetname = args[0];
			Player target = Bukkit.getPlayer(targetname);
			if(target == null) {
				sender.sendMessage(Main.PREFIX + "Ce joueur n'est pas sur le serveur");
				return true;
			}
			
			long diffInMillis = getTimedValue(args[1]) * 1000l;
			Timestamp newTempbanTimestamp = new Timestamp(System.currentTimeMillis() + diffInMillis);
			
			try {
				mutePlayerNow(target.getUniqueId(), newTempbanTimestamp);
				main.getMutingTimestamps().put(target.getUniqueId(), System.currentTimeMillis() + diffInMillis);
				sender.sendMessage(Main.PREFIX + " §e" + targetname + "§6 a été rendu muet/te temporairement");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		
		return false;
	}
	
	public void mutePlayerNow(UUID id, Timestamp timestamp) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET muted=?, tempmute_timestamp=? WHERE userid=?");
		
		statement.setBoolean(1, true);
		statement.setTimestamp(2, timestamp);
		
		statement.setString(3, id.toString());
		
		statement.executeUpdate();
	}
	
	public long getTimedValue(String val) {
		
		long total = 0;
		
		Pattern pattern = Pattern.compile("([0-9]+(years|y|days|d|hours|h|minutes|min|months|m|seconds|s))");
		Matcher matcher = pattern.matcher(val);
		
		while(matcher.find()) {
			
			String temp = matcher.group();
			
			String unit = temp.replaceAll("[0-9]+", "");
			int value = Integer.parseInt(temp.replaceAll("(years|y|days|d|hours|h|minutes|min|months|m|seconds|s)", ""));
			long multiplier = 1;
			
			switch (unit) {
				case "y":
				case "years":
					multiplier = 365 * 24 * 60 * 60; // jours * heures * min * sec
					break;
				case "min":
				case "minutes":
					multiplier = 60; // sec
					break;
				case "m":
				case "month":
					multiplier = 30 * 24 * 60 * 60; // jours * heures * min * sec
					break;
				case "d":
				case "days":
					multiplier = 24 * 60 * 60; // heures * min * sec
					break;
				case "h":
				case "hours":
					multiplier = 3600; // min * sec
					break;
			default:
				break;
			}
			
			total += (value * multiplier);
		}
		
		return total;
	}

}
