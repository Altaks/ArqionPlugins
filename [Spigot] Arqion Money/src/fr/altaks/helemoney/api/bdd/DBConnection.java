package fr.altaks.helemoney.api.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class DBConnection {
	
	private DBCredentials dbCredentials;
	private Connection connection;
	
	public DBConnection(DBCredentials dbCredentials) {
		this.dbCredentials = dbCredentials;
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.dbCredentials.toURI(), this.dbCredentials.getUser(), this.dbCredentials.getPass());
			
			Bukkit.getLogger().info("Successfully connected to DB !");
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() throws SQLException {
		if(this.connection != null) {
			if(!this.connection.isClosed()) {
				this.connection.close();
			}
		}
	}
	
	public Connection getConnection() throws SQLException {
		if(this.connection != null) {
			if(!this.connection.isClosed()) {
				return this.connection;
			}
		}
		connect();
		return this.connection;
	}

}
