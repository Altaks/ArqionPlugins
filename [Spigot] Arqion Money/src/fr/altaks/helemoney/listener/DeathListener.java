package fr.altaks.helemoney.listener;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.altaks.helemoney.Main;
import fr.altaks.helemoney.api.MoneyUtil;

public class DeathListener implements Listener {
	
	private Main main;
	
	public DeathListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDeathEvent(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		
		try {
			
			double actualMoney = main.getMoneyUtil().getMoneyOfPlayer(player.getUniqueId());
			
			double newMoney = 0.95 * actualMoney;
			double lostMoney = 0.05 * actualMoney;
			
			main.getMoneyUtil().setMoneyOfPlayer(player.getUniqueId(), newMoney);
			player.sendMessage(MoneyUtil.API_PREFIX + "§cVous venez de perdre 5% de votre monnaie lors de votre décès ! §7(" + MoneyUtil.formatAmount(lostMoney) + ")§r, §6Vous avez maintenant §e" + MoneyUtil.formatAmount(newMoney));
			
		} catch (SQLException e) {
			player.sendMessage(MoneyUtil.API_PREFIX + "§cUne erreur est survenue, veuillez prévenir le staff afin qu'il puisse régler ce problème au plus vite !");
			e.printStackTrace();
		}
		
	}

}
