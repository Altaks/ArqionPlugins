package fr.altaks.helemoney.commands;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.BankTier;
import fr.altaks.helemoney.api.MoneyUtil;

public class Deposit implements CommandExecutor {

	private Main main;
	
	public Deposit(Main main) {
		this.main = main;
	}
	
	@Override	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("deposit") && sender instanceof Player && args.length > 0) {
			
			try {
				double amount = Double.parseDouble(args[0]);
				Player player = (Player)sender;
				
				if(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId()) < amount) {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§cVous ne pouvez pas déposer autant d'argent dans votre banque, vous n'avez pas assez sur vous");
					return true;
				}
				
				if(main.getMoneyUtil().getMoneyOfPlayerBank(player.getUniqueId()) + amount > BankTier.getFromId(main.getMoneyUtil().getBankTier(player.getUniqueId())).max_storage) {
					sender.sendMessage(MoneyUtil.API_PREFIX + "§cVous ne pouvez pas déposer autant d'argent dans votre banque, elle ne peut pas stocker autant, songez à l'améliorer !");
					return true;
				}
				
				main.getMoneyUtil().removeMoneyToPlayer(player.getUniqueId(), amount);
				main.getMoneyUtil().addMoneyToPlayerBank(player.getUniqueId(), amount);
				sender.sendMessage(MoneyUtil.API_PREFIX + "§6Vous avez déposé §e" + MoneyUtil.formatAmount(amount) + "§6 dans votre banque !");
				sender.sendMessage(MoneyUtil.API_PREFIX + "§6Vous avez maintenant §e" + MoneyUtil.formatAmount(main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId()) - amount) + "§6 sur vous");
				return true;
				
			} catch (NumberFormatException e) {
				sender.sendMessage(MoneyUtil.API_PREFIX + "§cVeuillez entrer une valeur correcte !");
				return false;
			} catch (SQLException e) {
				sender.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue lors de votre dépot, veuillez prévenir le staff pour qu'il puisse régler ce problème au plus vite");
				e.printStackTrace();
			}
			
		}
		
		return false;
	}

}
