package fr.nowayy.helecore.Commands.basic;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.manager.HomesManager;
import fr.nowayy.helecore.utils.Messages;

public class HomeCommand implements TabExecutor {

	private Main main;
	public HomeCommand(Main main) {
		this.main = main;
	}
	
	String homeName;
	String playerOwnerName;
	Location home;
	
	@SuppressWarnings("deprecation")
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("home") && args.length >= 2) {
			HomesManager manager = new HomesManager(main, Bukkit.getOfflinePlayer(args[0].split(":")[0]));
			if (args[0].contains(":")) {
				if (sender.hasPermission("helecore.admin.homes")) {
					return Arrays.asList(manager.getHomeListFromPlayer(Bukkit.getOfflinePlayer(args[0].split(":")[0])));
				}
			} else {
				return Arrays.asList(manager.getHomeListFromPlayer((OfflinePlayer) sender));
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("home") && sender instanceof Player) {
			Player target = null;
			
			if (args.length == 2) {
				try {
					target = Bukkit.getPlayer(args[1]);
				} catch (NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
			} else if (args.length == 1) {
				
				target = (Player) sender;
			} else homeName = "home";
			
			
			if (args[0].contains(":")) {
				String[] values = args[0].split(":");
				playerOwnerName = values[0]; homeName = values[1];
			} else {
				playerOwnerName = sender.getName();
				homeName = args[0];
			}
			
			HomesManager pmanager = new HomesManager(main, Bukkit.getOfflinePlayer(playerOwnerName));
			
			try{
				home = pmanager.getHome(homeName);
				target.teleport(home);
			} catch(NullPointerException | IllegalArgumentException e) {
				sender.sendMessage(Main.Error_Prefix + Messages.Error_HomeDontExists.toString().replace("{@Home}", homeName));
				return true;
			}
				target.sendMessage(Main.prefix + Messages.Home_TeleportYourself.toString().replace("{@HomeName}", homeName));
				if (sender.getName() != target.getName()) {
					sender.sendMessage(Main.prefix + Messages.Home_TeleportOther.toString().replace("{@HomeName}", homeName).replace("{@target}", target.getDisplayName()));
					return true;
				}
		} else if (cmd.getName().equalsIgnoreCase("sethome") && sender instanceof Player) {
			
			OfflinePlayer p = (Player) sender;
			String homeName = args[0];
			
			HomesManager manager = new HomesManager(main, p);
			
			manager.setHome(p.getPlayer().getLocation(), homeName);
			
			sender.sendMessage(Main.prefix + Messages.Home_Set.toString().replace("{@HomeName}", homeName));
			return true;
		
		} else if (cmd.getName().equalsIgnoreCase("delhome") && sender instanceof Player) {
			OfflinePlayer p = (Player) sender;
			String homeName = args[0];
			
			HomesManager manager = new HomesManager(main, p);
			
			manager.delHome(homeName);
			
			sender.sendMessage(Main.prefix + Messages.Home_Del.toString().replace("{@HomeName}", homeName));
			return true;
		} else if (cmd.getName().equalsIgnoreCase("homes")) {
			
			OfflinePlayer target = null;
			if (args.length == 1) {
				try {
					target = Bukkit.getOfflinePlayer(args[0]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
				
			} else if (sender instanceof Player) {
				target = (Player) sender;
			} else return false;
			
			HomesManager manager = new HomesManager(main, target);
			StringJoiner joiner = new StringJoiner("§e, §6");
			
			Arrays.asList(manager.getHomesList()).forEach(e -> {
				joiner.add(e);
			});
			
			sender.sendMessage(Main.prefix + "§6" +joiner.toString());
			return true;
		}
		

		return false;
	}

}