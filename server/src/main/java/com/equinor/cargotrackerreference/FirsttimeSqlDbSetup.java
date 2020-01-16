package com.equinor.cargotrackerreference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirsttimeSqlDbSetup {
	
	private static final Logger log = LoggerFactory.getLogger("com.equinor");
	
	private static String DB_NAME = null;
	
	public static void verify() {
			
		String[] requiredKeys = { "DB_SERVER_ADMIN_USER","DB_SERVER_ADMIN_PASSWORD", 
								  "DB_APP_USER", "DB_APP_PASSWORD",
				                  "DB_APP_JDBC_STRING" };
		
		for (String key : requiredKeys) {
			String value = get(key);			
			if (value == null || "".equals(value)) {
				log.info("Skipping database initializing.");
				log.debug("Cannot initialize database without environment variable: " + key);
				log.debug("The following env variables needs to be set: " + String.join(" ", requiredKeys));
				log.debug("If the db is already initialized, this log message can be ignored");
				return;
			}
		}
		
		if (!(get("DB_APP_JDBC_STRING").contains("database="))) {
			throw new RuntimeException("The JDBC string needs to contain a database parameter");
		}
				
		DB_NAME=getDatabaseFromUrl(get("DB_APP_JDBC_STRING"));
		
		String masterConnectionUrl =
				getConnectionUrl(get("DB_APP_JDBC_STRING"),								
								  get("DB_SERVER_ADMIN_USER"),								 
								  get("DB_SERVER_ADMIN_PASSWORD"),
								  "master");			
				
		String ctConnectionUrl = 
				getConnectionUrl(get("DB_APP_JDBC_STRING"),						 						 
						 		  get("DB_SERVER_ADMIN_USER"),
						          get("DB_SERVER_ADMIN_PASSWORD"),
						          DB_NAME);			
		
		
		try {								
			Connection masterCon = DriverManager.getConnection(masterConnectionUrl);						
			createAppUserLogin(masterCon);
			masterCon.close();
			
			Connection masterCtCon = DriverManager.getConnection(ctConnectionUrl);			
			createAppDbUser(masterCtCon);					
			masterCtCon.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
				
	}
	
	private static String getDatabaseFromUrl(String connectionUrl) {
		int startIdx = connectionUrl.indexOf("database=")+9;
		int endIdx = connectionUrl.indexOf(";", startIdx);
		return connectionUrl.substring(startIdx, endIdx);
	}
	
	static String getConnectionUrl(String connectionUrl, String user, String password, String dbName) {
		
		if (connectionUrl.contains("user=") || connectionUrl.contains("password=")) {
			throw new RuntimeException("The supplied JDBC string cannot contain user or password");
		}		
		
		String urlDatabaseName = getDatabaseFromUrl(connectionUrl);
		
		int startIdx = connectionUrl.indexOf("://")+3;
		int endIdx = connectionUrl.indexOf(".");
		String dbServerShortName = connectionUrl.substring(startIdx, endIdx);
		connectionUrl = connectionUrl + "user=" + user + "@" + dbServerShortName + ";";	
		connectionUrl = connectionUrl + "password="+password+";";
		connectionUrl = connectionUrl.replace("database="+urlDatabaseName, "database="+dbName);
									
		return connectionUrl;		
	}
	
	private static void createAppDbUser(Connection masterCtDbCon) throws SQLException {
		String checkUserSql = "SELECT name FROM " + DB_NAME + ".sys.database_principals WHERE name = ?";
		
		PreparedStatement checkUser = masterCtDbCon.prepareStatement(checkUserSql);
		checkUser.setString(1, get("DB_APP_USER"));
		
		ResultSet rs = checkUser.executeQuery();
		
		if (rs.next()) {
			log.debug("Database user " + get("DB_APP_USER") + " already exists in " + DB_NAME + ", doing nothing");
		} else {
			String createUserSql = "CREATE USER " + get("DB_APP_USER") + " FROM LOGIN " + get("DB_APP_USER");
			log.debug("Creating database user: " + get("DB_APP_USER") + " in " + DB_NAME);
			PreparedStatement createUser = masterCtDbCon.prepareStatement(createUserSql);
			createUser.execute();
			createUser.close();
			
			log.debug("Granting CREATE SCHEMA to " + get("DB_APP_USER"));
			String grantSchemaSql = "GRANT CREATE SCHEMA TO " + get("DB_APP_USER");
			PreparedStatement grantCreateSchema = masterCtDbCon.prepareStatement(grantSchemaSql);		
			grantCreateSchema.execute();
			grantCreateSchema.close();
			
			log.debug("Granting CREATE TABLE to " + get("DB_APP_USER"));
			String grantTableSql = "GRANT CREATE TABLE TO " + get("DB_APP_USER");
			PreparedStatement grantCreateTable = masterCtDbCon.prepareStatement(grantTableSql);	
			grantCreateTable.execute();
			grantCreateTable.close();
			
			log.debug("Granting CREATE VIEW to " + get("DB_APP_USER"));
			String grantViewSql = "GRANT CREATE VIEW TO " + get("DB_APP_USER");
			PreparedStatement grantCreateView = masterCtDbCon.prepareStatement(grantViewSql);	
			grantCreateView.execute();
			grantCreateView.close();	
			
			log.debug("Granting INSERT, ALTER, SELECT on schema :: dbo TO " + get("DB_APP_USER"));
			String grantDboSql = "GRANT INSERT, ALTER, SELECT on schema :: dbo TO " + get("DB_APP_USER");
			PreparedStatement grantDbo = masterCtDbCon.prepareStatement(grantDboSql);	
			grantDbo.execute();
			grantDbo.close();			
		}
		
		checkUser.close();
	}
	
	private static void createAppUserLogin(Connection masterCon) throws SQLException {
		String checkLoginSql = "SELECT name FROM master.sys.sql_logins WHERE name = ?";
						
		PreparedStatement checkLogin = masterCon.prepareStatement(checkLoginSql);
		checkLogin.setString(1, get("DB_APP_USER"));
		
		ResultSet  rs = checkLogin.executeQuery();
		if (rs.next()) {
			log.debug("Application login user " + get("DB_APP_USER") + " already exists, doing nothing");							
		} else {
			String createLoginSql = "CREATE LOGIN " + get("DB_APP_USER") + " WITH PASSWORD = '" + get("DB_APP_PASSWORD") + "'";
			log.debug("Creating login user: " + get("DB_APP_USER"));
			PreparedStatement createLogin = masterCon.prepareStatement(createLoginSql);									
			createLogin.execute();
			createLogin.close();
		}
		
		checkLogin.close();		
	}
	
	/*
	 * Return true if the SQL server database admin password is in the environment or in a property
	 */
	public static boolean validInitPrecond() {
		boolean proceed = get("DB_SERVER_ADMIN_PASSWORD") != null;
		if (!proceed) {
			log.debug("DB_SERVER_ADMIN_PASSWORD not defined, skipping database initialization");
		} 
		return proceed;
	}
	
	/*
	 * Try environment first, then java properties
	 */
	private static String get(String var) {
		String value = System.getenv(var);
		if (value == null || "".equals(value)) {
			value = System.getProperty(var);
		}
		return value;
	}
	
}
