package SherloBot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Lik {

	private int idLik=-1;
	private String compte;
	private String identifiantLik;
	private String pseudoLik;
	private String tweet;
	private String image;
	private int retweet;
	private int likes;
	private String dateLik;


	public  Lik(Compte c, Compte idT, String p, String t, String i, int r, int l, String d) throws SQLException {		
		this.compte = c.getIdentifiant();
		this.identifiantLik = idT.getIdentifiant();
		this.pseudoLik = p;
		this.tweet = t;
		this.image = i;
		this.retweet = r;
		this.likes = l;
		this.dateLik = d;

		Connection connect = DBConnection.getDBConnection();
		String SQLPrep = "SELECT idLik FROM Lik where compte=? and identifiantLik=? and pseudoLik=? and tweet=? and image=? and dateLik=?;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, compte);
		prep1.setString(2, identifiantLik);
		prep1.setString(3, pseudoLik);
		prep1.setString(4, tweet);
		prep1.setString(5, image);
		prep1.setString(6, dateLik);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();

		while (rs.next()) {
			idLik = rs.getInt("idLik");
		}
	}


	public static void createTable() throws SQLException {
		{
			Connection c = DBConnection.getDBConnection();
			String createString = "CREATE TABLE IF NOT EXISTS Lik ( " + "idLik INTEGER  AUTO_INCREMENT, "+
					"compte varchar(50)," +
					"identifiantLik varchar(50)," +
					"pseudoLik varchar(50)," +
					"tweet varchar(300)," +
					"image varchar(500)," +
					"retweet INTEGER," +
					"likes INTEGER," +
					"dateLik varchar(50)," +					
					"constraint PKLik primary key (idLik))";

			try {
				Statement stmt = c.createStatement();
				stmt.executeUpdate(createString);

				createString = "ALTER TABLE Lik\n"+ 
						"ADD CONSTRAINT FKcidl FOREIGN KEY (compte) references Compte (identifiant),"+
						"ADD CONSTRAINT FKidTidl FOREIGN KEY (identifiantLik) references Compte (identifiant)";

				stmt = c.createStatement();
				stmt.executeUpdate(createString);
				System.out.print("Table Lik créé\n");
			}catch(Exception e){
				System.out.println("La table Lik existe deja");
			}

		}

	}


	public static void deleteTable() throws SQLException {
		{
			Connection connect = DBConnection.getDBConnection();
			String drop = "DROP TABLE IF EXISTS Lik";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(drop);
			System.out.println("Supprime table Lik");
		}

	}


	public void save() throws SQLException {
		if (this.idLik==-1){
			this.saveNew();
		}else {
			this.update();
		}

	}


	public void saveNew() throws SQLException {
		{
			Connection connect = DBConnection.getDBConnection();
			String SQLPrep = "INSERT INTO Lik (compte,identifiantLik,pseudoLik,tweet,image,dateLik,retweet,likes) VALUES (?,?,?,?,?,?,?,?);";
			PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);			
			prep.setString(1, compte);
			prep.setString(2, identifiantLik);
			prep.setString(3, pseudoLik);
			prep.setString(4, tweet);
			prep.setString(5, image);
			prep.setString(6, dateLik);
			prep.setInt(7, retweet);
			prep.setInt(8, likes);
			prep.executeUpdate();
			System.out.println("ajout Lik");

			// recuperation de la derniere ligne ajoutee (auto increment)
			// recupere le nouvel id
			int autoInc = -1;
			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				autoInc = rs.getInt(1);
			}
			System.out.print("  ->  idLik utilise lors de l'ajout : ");
			System.out.println(autoInc);
			this.idLik=autoInc;
			System.out.println();
		}

	}


	public void update() throws SQLException {
		{
			Connection connect = DBConnection.getDBConnection();
			String SQLprep = "update Lik set idLik=? and compte=? and image=? and  retweet=? and likes=? where tweet=? and identifiantLik=? and pseudoLik=? and dateLik=?;";
			PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setInt(1, this.idLik);
			prep.setString(2, compte);				
			prep.setString(3, image);
			prep.setInt(4, retweet);
			prep.setInt(5, likes);			
			prep.setString(6, tweet);
			prep.setString(7, identifiantLik);
			prep.setString(8, pseudoLik);
			prep.setString(9, dateLik);

			prep.execute();
			System.out.println("modification Lik");
			System.out.println();
		}

	}

}

