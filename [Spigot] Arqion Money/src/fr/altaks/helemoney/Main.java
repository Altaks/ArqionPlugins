package fr.altaks.helemoney;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.helemoney.api.MoneyUtil;
import fr.altaks.helemoney.commands.AdminBank;
import fr.altaks.helemoney.commands.AdminMoney;
import fr.altaks.helemoney.commands.Bank;
import fr.altaks.helemoney.commands.Deposit;
import fr.altaks.helemoney.commands.Money;
import fr.altaks.helemoney.commands.Withdraw;
import fr.altaks.helemoney.listener.BankUpgradeListener;
import fr.altaks.helemoney.listener.DeathListener;
import fr.altaks.helemoney.listener.PlayerJoinListener;

public class Main extends JavaPlugin {
	
	private MoneyUtil moneyUtil;
	
	public MoneyUtil getMoneyUtil() {
		return moneyUtil;
	}

	@Override
	public void onEnable() {
		moneyUtil = new MoneyUtil(this);
		
		getCommand("adminmoney").setExecutor(new AdminMoney(this));
		getCommand("adminbank").setExecutor(new AdminBank(this));
		
		getCommand("adminmoney").setTabCompleter(new AdminMoney(this));
		getCommand("adminbank").setTabCompleter(new AdminBank(this));
		
		getCommand("deposit").setExecutor(new Deposit(this));
		getCommand("withdraw").setExecutor(new Withdraw(this));
		
		getCommand("money").setExecutor(new Money(this));
		getCommand("bank").setExecutor(new Bank(this));
		
		Bukkit.getPluginManager().registerEvents(new DeathListener(this), this);
		Bukkit.getPluginManager().registerEvents(new BankUpgradeListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);

	}
	
	@Override
	public void onDisable() {

		moneyUtil.finish();
	}

}
