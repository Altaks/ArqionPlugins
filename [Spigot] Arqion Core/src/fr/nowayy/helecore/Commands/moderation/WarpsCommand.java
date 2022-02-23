package fr.nowayy.helecore.Commands.moderation;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.manager.WarpsManager;
import fr.nowayy.helecore.utils.Messages;

public class WarpsCommand implements TabExecutor {
	
	private Main main;
	public WarpsCommand(Main main) {
		this.main = main;
	}

	String warpName;
	Location warp;
	
	@SuppressWarnings("deprecation")
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("home") && args.length >= 2) {
			WarpsManager manager = new WarpsManager(main, Bukkit.getOfflinePlayer(args[0].split(":")[0]));
			if (args[0].contains(":")) {
				if (sender.hasPermission("helecore.admin.homes")) {
					return Arrays.asList(manager.getWarpsList());
				}
			} else {
				return Arrays.asList(manager.getWarpsList());
			}
		}
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("warps") && sender instanceof Player && args.length >= 0) {
			
			Player target = (Player) sender;
			WarpsManager manager = new WarpsManager(main, target);
			StringJoiner joiner = new StringJoiner("§e, §6");
			
			Arrays.asList(manager.getWarpsList()).forEach(e -> {
				joiner.add(e);
			});
			
			if (joiner.length() == 0) {
				sender.sendMessage(Main.Error_Prefix + Messages.Error_NoWarps.toString());
			}else {
				target.sendMessage(Main.prefix + "§6" + joiner.toString());
				return true;
			}
			
			
			
		} else if (cmd.getName().equalsIgnoreCase("warp") && sender instanceof Player) {
			if (args.length == 1) {
				Player target = (Player) sender;
				
				WarpsManager manager = new WarpsManager(main, target);
				try {
					warp = manager.getWarp(args[0]);
					target.teleport(warp);
				} catch(NullPointerException | IllegalArgumentException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_WarpDontExists.toString().replace("{@WarpName}", args[0]));
					System.out.println("[HeleError] Une erreur est survenue lors de la récupération du warp '" + args[0] + "'.");
					return true;
				}
			} else sender.sendMessage(Main.Error_Prefix + Messages.Error_ArgumentsMissing.toString());
			
			return true;
			
			
		} else if (cmd.getName().equalsIgnoreCase("setwarp") && sender instanceof Player && args.length == 1) {
		
			Player target = (Player) sender;
			warpName = args[0];
			WarpsManager manager = new WarpsManager(main, target);
			
			try{
				manager.setWarp(target.getLocation(), warpName);
			} catch(NullPointerException e) {
				System.out.println("[HeleError] Une erreur est survenue lors de la création du warp \"" + warpName+ "\".");
			}
			target.sendMessage(Main.prefix + Messages.Warp_Set.toString().replace("{@WarpName}", warpName));
			return true;
		
		
		} else if (cmd.getName().equalsIgnoreCase("delwarp") && sender instanceof Player && args.length == 1) {
			
			Player target = (Player) sender;
			WarpsManager manager = new WarpsManager(main, target);
			warpName = args[0];
			if(manager.isWarped(warpName)) {
				try {
					manager.delWarp(warpName);
				} catch(NullPointerException e) {
					System.out.println("[HeleError] Une erreur est survenue lors de la suppression du warp \"" + warpName +"\".");
					return true;
				}
				sender.sendMessage(Main.Error_Prefix + Messages.Warp_Del.toString().replace("{@WarpName}", warpName));
				return true;
			} else {
				sender.sendMessage(Main.Error_Prefix + Messages.Error_WarpDontExists.toString().replace("{@WarpName}", args[0]));
			}
			
		}

		return false;
	}

}
