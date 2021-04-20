package SherloBot;


import java.sql.SQLException;

import mvc.Menu;

public class Main {

	public static void main(String args[]) throws SQLException{

		//Main.deleteTable();	
		
		Main.createTable();
		 
		new Menu();


	}
	
	public static void createTable() throws SQLException {
		Compte.createTable();
		ListeFollow.createTable();
		ListeFollowes.createTable();
		Tweet.createTable();
		Lik.createTable();
	}
	
	public static void deleteTable() throws SQLException {
		Lik.deleteTable();
		Tweet.deleteTable();		
		ListeFollowes.deleteTable();
		ListeFollow.deleteTable();
		Compte.deleteTable();	
	}

}
