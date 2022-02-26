package fr.altaks.helemoney.commands;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.MoneyUtil;

public class AdminMoney implements TabExecutor {
	
	private Main main;
	
	public AdminMoney(Main main) {
		this.main = main;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("give","add","take","remove","clear","reset","set");
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length == 3) {
			
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
			if(target == null) {
				sender.sendMessage(MoneyUtil.API_PREFIX + "§cLe joueur n'est jamais venu sur le serveur !");
				return true;
			}
			
			double amount = Double.parseDouble(args[2]);
			
			switch (args[0].toLowerCase()) {
				
				case "give":
				case "add":

					
					main.getMoneyUtil().addMoneyToPlayer(target.getUniqueId(), amount);
					sender.sendMessage(MoneyUtil.API_PREFIX + "§e" + target.getName() + "§6 a maintenant §e" + MoneyUtil.formatAmount(amount) + "§6 en plus");
					return true;
					
				case "take":
				case "remove":
					
					
					main.getMoneyUtil().removeMoneyToPlayer(target.getUniqueId(), amount);
					sender.sendMessage(MoneyUtil.API_PREFIX + "§6" + target.getName() + "§6 a maintenant §e" + MoneyUtil.formatAmount(amount) + "§6 en moins");
					return true;
					
				case "set":
					try {
						
						main.getMoneyUtil().setMoneyOfPlayer(target.getUniqueId(), amount);
						sender.sendMessage(MoneyUtil.API_PREFIX + "§e" + target.getName() + "§6 a maintenant §6" + MoneyUtil.formatAmount(amount));
					} catch (SQLException e) {
						sender.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue lors de la mise a jour dans la base de données");
						e.printStackTrace();
					}
					return true;
				
				default:
					return false;
			}
			
		} else if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear")) {

				OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				if(target == null) {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§cLe joueur n'est jamais venu sur le serveur !");
					return true;
				}
				
				main.getMoneyUtil().resetMoneyOfPlayer(target.getUniqueId());
				sender.sendMessage(MoneyUtil.API_PREFIX + "§e" + target.getName() + "§6 a maintenant §e0.00€");
				return true;
				
			}
		}
		
		return false;
	}

}
