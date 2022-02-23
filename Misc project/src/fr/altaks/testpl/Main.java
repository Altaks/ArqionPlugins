package fr.altaks.testpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import fr.altaks.testpl.bdd.DatabaseManager;

public class Main extends JavaPlugin {
	
	public static void main(String[] args) {
	
		DatabaseManager manager = new DatabaseManager();
		
		try {
			
			final Connection connection = manager.getActivatedConnection().getConnection();
			final PreparedStatement statement = connection.prepareStatement("SELECT * FROM warns");
			
			ResultSet result = statement.executeQuery();
			System.out.println(result.first());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}