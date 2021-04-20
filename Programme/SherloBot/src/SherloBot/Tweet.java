package SherloBot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Tweet {

	private int idTweet=-1;
	private String compte;
	private String status;
	private String identifiantTweet;
	private String pseudoTweet;
	private String tweet;
	private String image;
	private int retweet;
	private int lik;
	private String dateTweet;


	public  Tweet(Compte c, String s, Compte idT, String p, String t, String i, int r, int l, String d) throws SQLException {		
		this.compte = c.getIdentifiant();
		this.status = s;
		this.identifiantTweet = idT.getIdentifiant();
		this.pseudoTweet = p;
		this.tweet = t;
		this.image = i;
		this.retweet = r;
		this.lik = l;
		this.dateTweet = d;

		Connection connect = DBConnection.getDBConnection();
		String SQLPrep = "SELECT idTweet FROM Tweet where compte=? and status=? and identifiantTweet=? and pseudoTweet=? and tweet=? and image=? and dateTweet=?;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, compte);
		prep1.setString(2, status);
		prep1.setString(3, identifiantTweet);
		prep1.setString(4, pseudoTweet);
		prep1.setString(5, tweet);
		prep1.setString(6, image);
		prep1.setString(7, dateTweet);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();

		while (rs.next()) {
			idTweet = rs.getInt("idTweet");
		}
	}


	public static void createTable() throws SQLException {
		{
			Connection c = DBConnection.getDBConnection();
			String createString = "CREATE TABLE IF NOT EXISTS Tweet ( " + "idTweet INTEGER  AUTO_INCREMENT, "+
					"compte varchar(50)," +
					"status varchar(50)," +
					"identifiantTweet varchar(50)," +
					"pseudoTweet varchar(50)," +
					"tweet varchar(300)," +
					"image varchar(500)," +
					"retweet INTEGER," +
					"lik INTEGER," +
					"dateTweet varchar(50)," +					
					"constraint PKTweet primary key (idTweet))";

			try {
				Statement stmt = c.createStatement();
				stmt.executeUpdate(createString);

				createString = "ALTER TABLE Tweet\n"+ 
						"ADD CONSTRAINT FKcid FOREIGN KEY (compte) references Compte (identifiant),"+
						"ADD CONSTRAINT FKidTid FOREIGN KEY (identifiantTweet) references Compte (identifiant)";

				stmt = c.createStatement();
				stmt.executeUpdate(createString);
				System.out.print("Table Tweet créé\n");
			}catch(Exception e){
				System.out.println("La table Tweet existe deja");
			}

		}

	}


	public static void deleteTable() throws SQLException {
		{
			Connection connect = DBConnection.getDBConnection();
			String drop = "DROP TABLE IF EXISTS Tweet";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(drop);
			System.out.println("Supprime table Tweet");
		}

	}


	public void save() throws SQLException {
		if (this.idTweet==-1){
			this.saveNew();
		}else {
			this.update();
		}

	}


	public void saveNew() throws SQLException {
		{
			Connection connect = DBConnection.getDBConnection();
			String SQLPrep = "INSERT INTO Tweet (compte,status,identifiantTweet,pseudoTweet,tweet,image,dateTweet,retweet,lik) VALUES (?,?,?,?,?,?,?,?,?);";
			PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);			
			prep.setString(1, compte);
			prep.setString(2, status);
			prep.setString(3, identifiantTweet);
			prep.setString(4, pseudoTweet);
			prep.setString(5, tweet);
			prep.setString(6, image);
			prep.setString(7, dateTweet);
			prep.setInt(8, retweet);
			prep.setInt(9, lik);
			prep.executeUpdate();
			System.out.println("ajout tweet");

			// recuperation de la derniere ligne ajoutee (auto increment)
			// recupere le nouvel id
			int autoInc = -1;
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				autoInc = rs.getInt(1);
			}
			System.out.print("  ->  idTweet utilise lors de l'ajout : ");
			System.out.println(autoInc);
			this.idTweet=autoInc;
			System.out.println();
		}

	}


	public void update() throws SQLException {
		{
			Connection connect = DBConnection.getDBConnection();
			String SQLprep = "update Tweet set idTweet=? and compte=? and status=? and image=? and  retweet=? and lik=? where tweet=? and identifiantTweet=? and pseudoTweet=? and dateTweet=?;";
			PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setInt(1, this.idTweet);
			prep.setString(2, compte);
			prep.setString(3, status);						
			prep.setString(4, image);
			prep.setInt(5, retweet);
			prep.setInt(6, lik);			
			prep.setString(7, tweet);
			prep.setString(8, identifiantTweet);
			prep.setString(9, pseudoTweet);
			prep.setString(10, dateTweet);

			prep.execute();
			System.out.println("modification Tweet");
			System.out.println();
		}

	}

}
