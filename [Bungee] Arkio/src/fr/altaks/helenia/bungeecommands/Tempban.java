package fr.altaks.helenia.bungeecommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Tempban extends Command {
	
	private Main main;

	public Tempban(String name, Main main) {
		super("tempban","helenia.tempban");
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
			
			long diffInMillis = getTimedValue(args[1]) * 1000l;
			Timestamp newTempbanTimestamp = new Timestamp(System.currentTimeMillis() + diffInMillis);
			
			StringJoiner joiner = new StringJoiner(" ");
			for(String str : args) {
				if(str != args[0] && str != args[1]) {
					joiner.add(str);
				}
			}
			
			String reason = joiner.toString();
			
			try {
				banPlayerNow(target.getUniqueId(), reason, newTempbanTimestamp);
				Date date = Date.from(newTempbanTimestamp.toInstant());
				DateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.FRANCE);
				target.disconnect(new TextComponent(Main.PREFIX + " Vous avez temporairement été banni par " + sender.getName() + " jusqu'à " + format.format(date)));
				sender.sendMessage(new TextComponent(Main.PREFIX + " Bannissement effectué !"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void banPlayerNow(UUID id, String reason, Timestamp toWhen) throws SQLException {
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE playerinfos SET banned=?, tempban_timestamp=?, lastbanreason=? WHERE userid=?");
		
		statement.setBoolean(1, true);
		statement.setTimestamp(2, toWhen);
		statement.setString(3, reason);
		
		statement.setString(4, id.toString());
		
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
