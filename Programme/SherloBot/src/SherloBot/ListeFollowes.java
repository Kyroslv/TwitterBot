package SherloBot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListeFollowes {
	
	private int numFollowes=-1;
	private String identifiant;
	private String followes;

	public ListeFollowes(Compte i, Compte f) throws SQLException {
		this.identifiant=i.getIdentifiant();
		this.followes=f.getIdentifiant();
		Connection connect = DBConnection.getDBConnection();
		String SQLPrep = "SELECT numFollowes FROM ListeFollowes where identifiant=? and followes=?;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, identifiant);
		prep1.setString(2, followes);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();

		while (rs.next()) {
			numFollowes = rs.getInt("numFollowes");
		}
	}
	
	private ListeFollowes(String i, String f) throws SQLException {
		this.identifiant=i;
		this.followes=f;
		Connection connect = DBConnection.getDBConnection();
		String SQLPrep = "SELECT numFollowes FROM ListeFollowes where identifiant=? and followes=?;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, identifiant);
		prep1.setString(2, followes);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();

		while (rs.next()) {
			numFollowes = rs.getInt("numFollowes");
		}
	}
	
	public static ListeFollowes findByNumFollowes(int n) throws SQLException {
		ListeFollowes l = null;
		Connection c = DBConnection.getDBConnection();
		String FI="SELECT * FROM LISTEFOLLOWES WHERE numFollowes = ?";
		PreparedStatement getS = c.prepareStatement(FI);
		getS.setInt(1, n);
		ResultSet rs = getS.executeQuery();
		while (rs.next()) {
			String id = rs.getString("identifiant");
			String f = rs.getString("followes");
			l = new ListeFollowes(id, f);
		}
		return l;

	}
	
	public static ListeFollowes findByIdentifiant(String id) throws SQLException {
		ListeFollowes l = null;
		Connection c = DBConnection.getDBConnection();
		String FI="SELECT * FROM LISTEFOLLOWES WHERE identifiant = ?";
		PreparedStatement getS = c.prepareStatement(FI);
		getS.setString(1, id);
		ResultSet rs = getS.executeQuery();
		while (rs.next()) {
			String f = rs.getString("followes");
			l = new ListeFollowes(id, f);
		}
		return l;

	}

	public static ListeFollowes findByFollowes(String f) throws SQLException {
		ListeFollowes l = null;
		Connection c = DBConnection.getDBConnection();
		String FI="SELECT * FROM LISTEFOLLOWES WHERE followes = ?";
		PreparedStatement getS = c.prepareStatement(FI);
		getS.setString(1, f);
		ResultSet rs = getS.executeQuery();
		while (rs.next()) {
			String id = rs.getString("identifiant");
			l = new ListeFollowes(id, f);
		}
		return l;

	}


	public static void createTable() throws SQLException {
		Connection c = DBConnection.getDBConnection();


		String createString = "CREATE TABLE IF NOT EXISTS ListeFollowes ("+
				"numFollowes integer AUTO_INCREMENT,"+
			    "identifiant    varchar(50),"+
			    "followes      varchar(50),"+
			    "constraint PKListeFollowes primary key (numFollowes))";
		try {
		Statement stmt = c.createStatement();
		stmt.executeUpdate(createString);
		createString = "ALTER TABLE ListeFollowes\n"+ 
				"ADD CONSTRAINT FKLFollowesCid FOREIGN KEY (identifiant) references Compte (identifiant),\n"+
				"ADD CONSTRAINT FKLFollowesCf FOREIGN KEY (followes) references Compte (identifiant)";
		stmt = c.createStatement();
		stmt.executeUpdate(createString);
		System.out.print("Table ListeFollowes créé\n");
		}catch(Exception e){
			System.out.println("La table Liste Followes existe deja");
		}
	}


	public static void deleteTable() throws SQLException {
		Connection c = DBConnection.getDBConnection();
		String drop = "DROP TABLE IF EXISTS ListeFollowes";
		Statement stmt = c.createStatement();
		stmt.executeUpdate(drop);
		System.out.println("Supprime table ListeFollowes");
	}


	public void delete() throws SQLException {
		Connection c = DBConnection.getDBConnection();
		PreparedStatement prep = c.prepareStatement("DELETE FROM ListeFollowes WHERE identifiant=?");
		prep.setString(1, this.identifiant);
		prep.execute();
		System.out.println("Suppression dans ListeFollowes");
		this.numFollowes=-1;
		this.identifiant="-1";
		this.followes="-1";
	}


	public void save() throws SQLException {
		if (this.numFollowes==-1){
			this.saveNew();
		}else {
			this.update();
		}
	}


	private void saveNew() throws SQLException {
		Connection c = DBConnection.getDBConnection();
		String SQLPrep = "INSERT INTO ListeFollowes (identifiant,followes) VALUES (?,?);";
		PreparedStatement prep = c.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);			
		prep.setString(1, this.identifiant);
		prep.setString(2, this.followes);
		prep.executeUpdate();
		System.out.println("ajout Followes");

		int autoInc = -1;
		ResultSet rs = prep.getGeneratedKeys();
		if (rs.next()) {
			autoInc = rs.getInt(1);
		}
		System.out.print("  ->  numFollowes utilise lors de l'ajout : ");
		System.out.println(autoInc);
		this.numFollowes=autoInc;
		System.out.println();

	}


	private void update() throws SQLException {
		/*
		Connection c = DBConnection.getDBConnection();
		String SQLprep = "update ListeFollow set identifiant=? and follow=? where numFollow=?;";
		PreparedStatement prep = c.prepareStatement(SQLprep);
		prep.setString(1, this.identifiant);
		prep.setString(2, this.followes);
		prep.setInt(3, this.numFollowes);
		prep.execute();
		*/
		System.out.println("le lien entre " + followes + " qui est suivis par " + identifiant + " existe deja" );
	}
	

	@Override
	public String toString() {
		return "ListeFollowes [numFollowes=" + numFollowes + ", identifiant=" + identifiant + ", followes=" + followes
				+ "]";
	}

	/**
	 * @return the numFollowes
	 */
	public int getNumFollowes() {
		return numFollowes;
	}

	/**
	 * @return the identifiant
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * @return the followes
	 */
	public String getFollowes() {
		return followes;
	}

	
}
