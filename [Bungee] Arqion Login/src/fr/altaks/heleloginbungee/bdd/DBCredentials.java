package fr.altaks.heleloginbungee.bdd;

public class DBCredentials {
	
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
		// changement de système, flemme de refaire le reste
		final StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://")
		  .append(host)
		  .append(":"+port)
		  .append("/")
		  .append(dbname);
	    return sb.toString();
	}
	
}

