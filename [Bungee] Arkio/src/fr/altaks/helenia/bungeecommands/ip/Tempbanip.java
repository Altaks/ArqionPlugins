package fr.altaks.helenia.bungeecommands.ip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Tempbanip extends Command {
	
	private Main main;

	public Tempbanip(String name, Main main) {
		super("tempbanip","helenia.tempipban");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(args.length >= 3) {
			
			String pseudo = args[0];
			
			StringJoiner reason = new StringJoiner(" ");
			
			for(String s : args) {
				if(s != args[0] && s != args[1]) reason.add(s);
			}
			
			String interval = args[1];
			Timestamp toWhen = Timestamp.from(Instant.ofEpochMilli(System.currentTimeMillis() + (getTimedValue(interval)*1000l)));
			
			
			try {
				String ip = getIp(pseudo);
				tempbanIP(ip, reason.toString(), toWhen);
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
	
	public void tempbanIP(String ip, String reason, Timestamp toWhen) throws SQLException {
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE SET banned=?, tempipban_timestamp=?, lastbanreason=? WHERE ip=?");
		
		statement.setBoolean(1, true);
		statement.setTimestamp(2, toWhen);
		statement.setString(3, reason);
		
		statement.setString(4, ip);
		
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
				case "min":
				case "minutes":
					multiplier = 60; // sec
					break;
			default:
				break;
			}
			
			total += (value * multiplier);
		}
		
		return total;
	}

}
