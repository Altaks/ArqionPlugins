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

public class WalkSpeedCommand implements TabExecutor {

	@SuppressWarnings("unused")
	private float defaultWalkSpeed = 0.2f, defaultFlySpeed = 0.1f;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("walkspeed") && args.length < 2) {
			for (int i = 1; i < 11; i++) {
				list.add(i + "");
			}
			return list;
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("walkspeed")) {
			
			Player target = null;
			
			if (args.length > 1) {
				try {
					target = Bukkit.getPlayer(args[1]);
				} catch(NullPointerException e) {
					sender.sendMessage(Main.Error_Prefix + Messages.Error_PlayerNotFound.toString().replace("{@target}", args[1]));
					return true;
				}
			} else target = (Player) sender;
			
			int speedMultiplier = 1;
			try {
				speedMultiplier = Integer.parseInt(args[0]);
			} catch (Exception e) {
				sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError.toString());
				return true;
			}
			
			if (speedMultiplier >= 11) {
				sender.sendMessage(Main.Error_Prefix + Messages.Speed_ChangingError.toString());
				return true;
			}
			
			target.setWalkSpeed(speedMultiplier * defaultWalkSpeed);
			target.sendMessage(Main.prefix + Messages.WalkSpeed_ChangingYourself.toString().replace("{@WalkSpeed}", speedMultiplier + ""));
			
			if (!((Player) sender).equals(target)) {
				sender.sendMessage(Main.prefix + Messages.WalkSpeed_ChangingOther.toString().replace("{@target}", target.getDisplayName()).replace("{@WalkSpeed}", speedMultiplier + ""));
			}
			return true;
			}
		
		return false;
	}

}
