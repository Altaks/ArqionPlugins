package fr.altaks.helemoney.commands;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.MoneyUtil;

public class Money implements CommandExecutor {
	
	private Main main;
	
	public Money(Main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("money")) {
			
			if(args.length == 0 && sender instanceof Player) {
				
				Player player = (Player)sender;
				try {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§6Vous avez actuellement §e" + MoneyUtil.formatAmount(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId())) + "§6 sur vous et §e" +  MoneyUtil.formatAmount(main.getMoneyUtil().getMoneyOfPlayerBank(player.getUniqueId())) + "§6 en banque");
					return true;
				} catch (SQLException e) {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue, veuillez contacter le staff afin qu'il puisse régler ce problème au plus vite");
					e.printStackTrace();
				}
				
			} else if(args.length > 0 && sender.hasPermission("money.seeOthers")) {
				
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
				try {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§e" + player.getName() + "§6 a actuellement §e" + MoneyUtil.formatAmount(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId())) + "§6 sur lui et §e" +  MoneyUtil.formatAmount(main.getMoneyUtil().getMoneyOfPlayerBank(player.getUniqueId())) + "§6 en banque");
					return true;
				} catch (SQLException e) {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue, veuillez contacter le staff afin qu'il puisse régler ce problème au plus vite");
					e.printStackTrace();
				}
				
			}
			
		}
		
		return false;
	}

}
