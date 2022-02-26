package fr.nowayy.arqionbox.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;

import fr.nowayy.arqionbox.Main;
import fr.nowayy.arqionbox.api.bdd.DatabaseManager;

public class MoneyUtil {
	
	private DatabaseManager dbManager;
	private Main main;
	public static final String API_PREFIX = "§7[§eHeleMoney§7]§r \u00BB ";
	
	public MoneyUtil(Main main) {
		dbManager = new DatabaseManager();
		this.main = main;
	}
	
	public void finish() {
		dbManager.close();
	}
	
	/**
	 * Permet d'acquerir la connexion a la bdd, faire attention aux requ�tes envoy�es merci
	 * @return l'objet Connection li� a la bdd
	 * @throws SQLException : Erreur pouvant intervenir a cause d'une erreur interne de la BDD ou d'une erreur de requ�te
	 */
	public Connection getDBConnection() throws SQLException {
		return this.dbManager.getActivatedConnection().getConnection();
	}
	
	/**
	 * Permet de formatter les montants (en double) vers des montants formatt�s (en String)
	 * @param amount : le montant 
	 * @return le string formatt�; un 200.0d devient "200,00�"
	 */
	public static String formatAmount(double amount) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.FRANCE);
		return format.format(amount).replace("\u00a0", " ");
	}
	
	/**
	 * Permet d'ajouter le montant d�fini a la monnaie du joueur
	 * @param playerid : UUID du joueur cibl�
	 * @param amount : montant du versement
	 */
	public void addMoneyToPlayer(UUID playerid, double amount) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			try {
				
				double actualPlayerMoney = getMoneyOfPlayer(playerid);
				double playerMoneyToSet = actualPlayerMoney + amount;
				
				setMoneyOfPlayer(playerid, playerMoneyToSet);
				
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while adding money to a player ("+ playerid +")");
			}
		});
	}
	
	/**
	 * Permet de retirer le montant d�fnini a la monnaie du joueur
	 * @param playerid : UUID du joueur cibl�
	 * @param amount : montant du retrait
	 */
	public void removeMoneyToPlayer(UUID playerid, double amount) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			
			try {
				
				double actualPlayerMoney = getMoneyOfPlayer(playerid);
				double playerMoneyToSet = actualPlayerMoney - amount;
				
				setMoneyOfPlayer(playerid, playerMoneyToSet);
				
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while removing money to a player ("+ playerid +")");
			}
			
		});
	}
	
	/**
	 * Permet de r�initialiser la monnaie du joueur
	 * @param playerid : UUID du joueur cibl�
	 */
	public void resetMoneyOfPlayer(UUID playerid) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			
			try {
				
				setMoneyOfPlayer(playerid, 0);
				
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while removing money to a player ("+ playerid +")");
			}
			
		});
		
	}
	
	/**
	 * Permet d'assigner la monnaie du joueur cibl�
	 * @param id : UUID du joueur
	 * @param newAmount : Montant qui sera la nouvelle quantit� totale de monnaie du joueur
	 * @throws SQLException : Erreur pouvant intervenir a cause d'une erreur interne de la BDD ou d'une erreur de requ�te
	 */
	public void setMoneyOfPlayer(UUID id, double newAmount) throws SQLException {
		
		final Connection connection = dbManager.getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE player_money SET money=? WHERE userid=?");
		
		statement.setDouble(1, newAmount);
		statement.setString(2, id.toString());
		
		statement.executeUpdate();
	}
	
	/**
	 * Permet de r�cup�rer la quantit� d'argent poss�d� par le joueur
	 * @param id : UUID du joueur
	 * @return Quantit� de monnaie du joueur
	 * @throws SQLException : Erreur pouvant intervenir a cause d'une erreur interne de la BDD ou d'une erreur de requ�te
	 */
	public double getMoneyOfPlayer(UUID id) throws SQLException {
		
		final Connection connection = dbManager.getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT money FROM player_money WHERE userid=?");

		statement.setString(1, id.toString());
		
		final ResultSet result = statement.executeQuery();
		result.first();
		
		return result.getDouble("money");
	}
	
	/**
	 * Permet d'obtenir l'identifiant (index) correspondant au tiers de la banque du joueur
	 * @param id : UUID du joueur cibl�
	 * @return identifiant du tier de la banque du joueur
	 * @throws SQLException : Erreur pouvant intervenir a cause d'une erreur interne de la BDD ou d'une erreur de requ�te
	 */
	public int getBankTier(UUID id) throws SQLException {
		
		final Connection connection = dbManager.getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT bank_tier FROM player_money WHERE userid=?");

		statement.setString(1, id.toString());
		
		final ResultSet result = statement.executeQuery();
		result.first();
		
		return result.getInt("bank_tier");
	}
	
	/**
	 * Permet de mettre a jour le niveau actuel de la banque du joueur, ne prend pas en compte le niveau actuel (update sans condition)s
	 * @param playerid : UUID du joueur cibl�
	 * @param banktier : ID du tier de banque souhait�
	 */
	public void updateBankTier(UUID playerid, int banktier) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			try {
				
				final Connection connection = dbManager.getActivatedConnection().getConnection();
				final PreparedStatement statement = connection.prepareStatement("UPDATE player_money SET bank_tier=? WHERE userid=?");
				
				statement.setInt(1, banktier);
				statement.setString(2, playerid.toString());
				
				statement.executeUpdate();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while changing bank tier of a player ("+ playerid +")");
				
			}
			
		});
	}
	
	/**
	 * Permet d'ajouter de la monnaie dans la banque du joueur, prend en compte la capacit� maximale. Lance une HeleMoneyException en cas de capacit� de banque trop faible
	 * @param playerid : UUID du joueur cibl�
	 * @param amount : Montant du versement
	 */
	public void addMoneyToPlayerBank(UUID playerid, double amount) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			try {
				
				double actualPlayerMoney = getMoneyOfPlayerBank(playerid);
				double playerMoneyToSet = actualPlayerMoney + amount;
				
				BankTier banktier = BankTier.getFromId(getBankTier(playerid));
				if(playerMoneyToSet > banktier.max_storage) {
					throw new HeleMoneyException(API_PREFIX + "The money you've tried to add is too high for the player's bank !");
				}
				setMoneyOfPlayerBank(playerid, playerMoneyToSet);
				
			} catch (SQLException | HeleMoneyException e) {
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while adding money to a player ("+ playerid +")");
			}
		});
	}
	
	/**
	 * Permet de retirer de la monnaie dans la banque du joueur, ne prend pas en compte l'argent actuel du joueur, attention a emp�cher les cas de dette !
	 * @param playerid : UUID du joueur cibl�
	 * @param amount : Montant du retrait
	 */
	public void removeMoneyToPlayerBank(UUID playerid, double amount) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			
			try {
				
				double actualPlayerMoney = getMoneyOfPlayerBank(playerid);
				double playerMoneyToSet = actualPlayerMoney - amount;
				
				setMoneyOfPlayerBank(playerid, playerMoneyToSet);
				
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while removing money to a player ("+ playerid +")");
			}
			
		});
	}
	
	/**
	 * Permet de r�initialiser la monnaie dans la banque d'un joueur
	 * @param playerid : UUID du joueur cibl�
	 */
	public void resetMoneyOfPlayerBank(UUID playerid) {
		Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
			
			try {
				
				setMoneyOfPlayerBank(playerid, 0);
				
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getLogger().info("§cAn error has occured while removing money to a player ("+ playerid +")");
			}
			
		});
		
	}
	
	/**
	 * Permet d'assigner la monnaie dans la banque du joueur
	 * @param id : UUID du joueur cibl�
	 * @param newAmount : Quantit� d'argent qui sera affect�e a la banque du joueur
	 * @throws SQLException : : Erreur pouvant intervenir a cause d'une erreur interne de la BDD ou d'une erreur de requ�te
	 */
	public void setMoneyOfPlayerBank(UUID id, double newAmount) throws SQLException {
		
		final Connection connection = dbManager.getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("UPDATE player_money SET bank_money=? WHERE userid=?");
		
		statement.setDouble(1, newAmount);
		statement.setString(2, id.toString());
		
		statement.executeUpdate();
	}
	
	/**
	 * Permet d'obtenir la monnaie situ�e dans la banque d'un joueur
	 * @param id : UUID du joueur
	 * @return Monnaie situ�e dans la banque du joueur (double)
	 * @throws SQLException : Erreur pouvant intervenir a cause d'une erreur interne de la BDD ou d'une erreur de requ�te
	 */
	public double getMoneyOfPlayerBank(UUID id) throws SQLException {
		
		final ResultSet result;
		
		final Connection connection = dbManager.getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT bank_money FROM player_money WHERE userid=?");

		statement.setString(1, id.toString());
		
		result = statement.executeQuery();
		result.first();
		
		return result.getDouble("bank_money");
	}
	

}
