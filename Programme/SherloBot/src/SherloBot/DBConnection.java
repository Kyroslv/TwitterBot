package SherloBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * La classe DBConnection.
 */
public class DBConnection {

	
	/** 
	 * Variables a modifier en fonction de la base utilisee 
	 */
	
	/** Le nom d'utilisateur */
	public String userName = "root";
	
	/** Le mot de passe */
	public String password = "";
	
	/** Le nom du serveur */
	public  String serverName = "localhost";
	
	/** Le port utilise */
	public String portNumber = "3306";
	
	/** Le nom de la table*/
	public String tableName = "SherloBot";
	
	/** L'url de la base */
	private String urlDB;

	public static String dbName = "SherloBot";
	public Connection connect;
	private static DBConnection DBcon;

	/**
	 * Constructeur de la connexion
	 *
	 * @throws SQLException the SQL exception
	 */
	private DBConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		this.urlDB = "jdbc:mysql://" + serverName + ":";
		this.urlDB += portNumber + "/" + this.dbName;
		this.connect = DriverManager.getConnection(urlDB, connectionProps);
	}

	/**
	 * Methode permettant de n'autoriser q'une seule instance de la classe
	 *
	 * @return l'instance autorisee
	 * @throws SQLException the SQL exception
	 */
	public static synchronized Connection getDBConnection( ) throws SQLException{
		if (DBcon == null) {
			DBcon = new DBConnection( );
		}
		return DBcon.connect;
	}

	/**
	 * Methode setter permettant changer la base utilisee
	 *
	 * @param nomDB le nom de la base
	 * @throws SQLException the SQL exception
	 */
	public static synchronized void setNomDB(String nomDB) throws SQLException {
		dbName=nomDB;
		DBcon = new DBConnection();
		
	}

}

