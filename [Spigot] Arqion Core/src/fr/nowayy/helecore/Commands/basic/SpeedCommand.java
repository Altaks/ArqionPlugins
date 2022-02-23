package fr.nowayy.helecore.Commands.basic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.nowayy.helecore.Main;
import fr.nowayy.helecore.utils.Messages;

public class SpeedCommand implements TabExecutor {

	private float defaultWalkSpeed = 0.2f, defaultFlySpeed = 0.1f;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		Player target = null;
		List<String> list = new ArrayList<String>();
		if(cmd.getName().equalsIgnoreCase("speed") && args.length == 2) {
			try {
				target = Bukkit.getPlayer(args[1]);
			} catch(NullPointerException e) {
				sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0].toString()));
			}
				if (target.isOnGround()) {
					for(int i = 1; i < 6; i++) {
						list.add(i+"");
				}
				} else {
					for( int j = 1; j < 11; j++) {
						list.add(j+"");
				}
			}
			
			return list;
		} else if (cmd.getName().equalsIgnoreCase("speed") && args.length < 2) {
			target = (Player) sender;
			if (target.isOnGround()) {
				for(int x = 1; x < 6; x++) {
					list.add(x+"");
				}
			} else {
				for(int y = 1; y < 11; y++) {
					list.add(y+"");
				}
			}
			return list;
		}
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("speed") && sender instanceof Player) {
			
			Player target = null;
			
			if (args.length == 2) {
				try {
					target = Bukkit.getPlayer(args[0]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
					return true;
				}
			} else target = (Player) sender;
			
			int speedMultiplier = 1;
			try {
				speedMultiplier = Integer.parseInt(args[0]);
			} catch(Exception e1) {
				sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError.toString());
				return true;
			}
			
			
			if (target.isOnGround()) {
				if (speedMultiplier >= 6) {
					sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError.toString()); 
				}
				target.setWalkSpeed(speedMultiplier * defaultWalkSpeed);
				target.sendMessage(Main.prefix + Messages.WalkSpeed_ChangingYourself.toString().replace("{@WalkSpeed}", speedMultiplier + ""));
				if (sender.getName() != target.getName()) {
					sender.sendMessage(Main.prefix + Messages.WalkSpeed_ChangingOther.toString().replace("{@WalkSpeed}", speedMultiplier + "").replace("{@target}", target.getDisplayName()));
				}
			} else {
				if (speedMultiplier >= 11) {
					sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError.toString());
				}
				target.setFlySpeed(speedMultiplier * defaultFlySpeed);
				target.sendMessage(Main.prefix + Messages.FlySpeed_ChangingYourself.toString().replace("{@FlySpeed}", speedMultiplier + ""));
				if (target.getName() != sender.getName()) {
					sender.sendMessage(Main.prefix + Messages.FlySpeed_ChangingOther.toString().replace("{@FlySpeed}", speedMultiplier + "").replace("{@target}", target.getDisplayName()));
				}
			}
		}

		return false;
	}

}
