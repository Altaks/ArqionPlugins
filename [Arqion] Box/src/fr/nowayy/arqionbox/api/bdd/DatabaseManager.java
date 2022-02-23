package fr.nowayy.arqionbox.api.bdd;

import java.sql.SQLException;

public class DatabaseManager {
	
	private DBConnection activatedConnection;
	
	public DatabaseManager() {
		
		this.activatedConnection = new DBConnection(new DBCredentials("51.210.233.53", "skyblock", "ArqBddSkyblock2022", "skyblock", 3306));
		
	}
	
	public void close() {
		try {
			this.activatedConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DBConnection getActivatedConnection() {
		return this.activatedConnection;
	}

}
