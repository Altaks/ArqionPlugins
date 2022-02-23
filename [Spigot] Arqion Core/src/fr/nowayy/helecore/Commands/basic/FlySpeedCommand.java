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

public class FlySpeedCommand implements TabExecutor {

	@SuppressWarnings("unused")
	private float defaultWalkSpeed = 0.2f, defaultFlySpeed = 0.1f;

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if(cmd.getName().equalsIgnoreCase("flyspeed") && args.length < 2) {
			for(int i = 1; i < 11; i++) {
				list.add(i+"");
			}
			return list;
		}
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("flyspeed") && sender instanceof Player) {
			
			Player target = null;
			
			if(args.length > 1) {
				try {
					target = Bukkit.getPlayer(args[1]);
				} catch (NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[0]));
				}
			} else target = (Player)sender;
			
			int speedMultiplier = 1;
			try {
				speedMultiplier = Integer.parseInt(args[0]);
			} catch (Exception e) {
				sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError);
				return true;
			}
			if(speedMultiplier >= 11) {
				sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError + "");
				return true;
			}
			
			target.setFlySpeed(speedMultiplier * defaultFlySpeed);
			target.sendMessage(Main.prefix+ Messages.FlySpeed_ChangingYourself.toString().replace("{@FlySpeed}", speedMultiplier + ""));
			if(!((Player)sender).equals(target)) {
				sender.sendMessage(Main.prefix + Messages.FlySpeed_ChangingOther.toString().replace("{@FlySpeed}", speedMultiplier + "").replace("{@target}", target.getDisplayName()));
			}
			return true;
		}
		return false;
	}

}
