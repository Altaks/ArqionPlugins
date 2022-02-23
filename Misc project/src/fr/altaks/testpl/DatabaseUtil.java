package fr.altaks.testpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;

public class DatabaseUtil {

	private DBConnection activatedConnection;

	private String connection_address, sqlusername, sqlpassword, tablename;
	private int connection_port = -1;
	
	public DatabaseUtil(String ipadress, String sqlusername, String sqlpassword, String tablename, @Nullable int connection_port) {
		
		this.connection_address = ipadress;
		this.sqlusername = sqlusername;
		this.sqlpassword = sqlpassword;
		this.tablename = tablename;
		
		this.connection_port = (connection_port == 0) ? 3306 : connection_port;
		
		this.activatedConnection = new DBConnection(new DBCredentials(this.connection_address, this.sqlusername, this.sqlpassword, this.tablename, this.connection_port));
	}
	
	public void close() {
		try {
			this.activatedConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private DBConnection getActivatedConnection() {
		return this.activatedConnection;
	}
	
	public Connection getConnection() throws SQLException {
		return getActivatedConnection().getConnection();
	}
	
	/**
	 * @param request -> SQL string request, same formatting for a SQL console
	 * @param details -> Arguments to use to replace "?" with.
	 * @return if the update has been done without issues
	 */
	@SuppressWarnings("unchecked")
	public boolean executeUpdate(String request, Class<? extends Object>...details) {
		try {
			PreparedStatement statement = getConnection().prepareStatement(request);
			for(int i = 0; i < details.length; i++) {
				statement.setObject(i+1, details[i]);
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * @param request -> SQL string request, same formatting for a SQL console
	 * @param details -> Arguments to use to replace "?" with.
	 * @return the results list of the querying, or null if an error happened
	 */
	@SuppressWarnings("unchecked")
	public ResultSet executeQuery(String request, Class<? extends Object>...details) {
		try {
			PreparedStatement statement = getConnection().prepareStatement(request);
			for(int i = 0; i < details.length; i++) {
				statement.setObject(i+1, details[i]);
			}
			return statement.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}
	
}

class DBConnection {
	
	private DBCredentials dbCredentials;
	private Connection connection;
	
	public DBConnection(DBCredentials dbCredentials) {
		this.dbCredentials = dbCredentials;
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.dbCredentials.toURI(), this.dbCredentials.getUser(), this.dbCredentials.getPass());
			
			System.out.println("Connection with database successfully opened !");
			
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

class DBCredentials {
	
	private String host;
	private String user;
	private String pass;
	private String dbname;
	
	private int port;
	
	public DBCredentials(String host, String user, String pass, String dbname, int port) {
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.dbname = dbname;
		this.port = port;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPass() {
		return pass;
	}
	
	public String toURI() {
		final StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://")
		  .append(host)
		  .append(":"+port)
		  .append("/")
		  .append(dbname);
	    return sb.toString();
	}
	
}
