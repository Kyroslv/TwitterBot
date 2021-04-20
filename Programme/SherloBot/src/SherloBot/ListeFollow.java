package SherloBot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListeFollow{
	
	private int numFollow=-1;
	private String identifiant;
	private String follow;

	public ListeFollow(Compte i, Compte f) throws SQLException {
		this.identifiant=i.getIdentifiant();
		this.follow=f.getIdentifiant();
		Connection connect = DBConnection.getDBConnection();
		String SQLPrep = "SELECT numFollow FROM ListeFollow where identifiant=? and follow=?;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, identifiant);
		prep1.setString(2, follow);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();

		while (rs.next()) {
			numFollow = rs.getInt("numFollow");
		}
	}

	public ListeFollow(String i, String f) throws SQLException {
		this.identifiant=i;
		this.follow=f;
		Connection connect = DBConnection.getDBConnection();
		String SQLPrep = "SELECT numFollow FROM ListeFollow where identifiant=? and follow=?;";
		PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
		prep1.setString(1, identifiant);
		prep1.setString(2, follow);
		prep1.execute();
		ResultSet rs = prep1.getResultSet();

		while (rs.next()) {
			numFollow = rs.getInt("numFollow");
		}
	}

	public static ListeFollow findByNumFollow(int n) throws SQLException {
		ListeFollow l = null;
		Connection c = DBConnection.getDBConnection();
		String FI="SELECT * FROM LISTEFOLLOW WHERE numFollow = ?";
		PreparedStatement getS = c.prepareStatement(FI);
		getS.setInt(1, n);
		ResultSet rs = getS.executeQuery();
		while (rs.next()) {
			String id = rs.getString("identifiant");
			String f = rs.getString("follow");
			l = new ListeFollow(id, f);
		}
		return l;

	}

	public static ArrayList<ListeFollow> findByIdentifiant(String id) throws SQLException {
		ArrayList<ListeFollow> l = new ArrayList<ListeFollow>();
		Connection c = DBConnection.getDBConnection();
		String FI="SELECT * FROM LISTEFOLLOW WHERE identifiant = ?";
		PreparedStatement getS = c.prepareStatement(FI);
		getS.setString(1, id);
		ResultSet rs = getS.executeQuery();
		while (rs.next()) {
			String f = rs.getString("follow");
			l.add(new ListeFollow(id, f));
		}
		return l;

	}

	public static ArrayList<ListeFollow>findByFollow(String f) throws SQLException {
		ArrayList<ListeFollow> l = new ArrayList<ListeFollow>();
		Connection c = DBConnection.getDBConnection();
		String FI="SELECT * FROM LISTEFOLLOW WHERE follow = ?";
		PreparedStatement getS = c.prepareStatement(FI);
		getS.setString(1, f);
		ResultSet rs = getS.executeQuery();
		while (rs.next()) {
			String id = rs.getString("identifiant");
			l.add(new ListeFollow(id, f));
		}
		return l;

	}


	public static void createTable() throws SQLException {
		Connection c = DBConnection.getDBConnection();

		String createString = "CREATE TABLE IF NOT EXISTS ListeFollow ("+
				"numFollow integer AUTO_INCREMENT,"+
				"identifiant varchar(50),"+
				"follow varchar(50),"+
				"constraint PKListeFollow primary key (numFollow))";
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate(createString);

			createString = "ALTER TABLE ListeFollow\n"+ 
					"ADD CONSTRAINT FKLCid FOREIGN KEY (identifiant) references Compte (identifiant),\n"+
					"ADD CONSTRAINT FKLCf FOREIGN KEY (follow) references Compte (identifiant)";
			stmt = c.createStatement();
			stmt.executeUpdate(createString);
			System.out.print("Table ListeFollow créé\n");
		}catch(Exception e){
			System.out.println("La table Liste Follow existe deja");
		}
	}


	public static void deleteTable() throws SQLException {
		Connection c = DBConnection.getDBConnection();
		String drop = "DROP TABLE IF EXISTS ListeFollow";
		Statement stmt = c.createStatement();
		stmt.executeUpdate(drop);
		System.out.println("Supprime table ListeFollow");
	}


	public void delete() throws SQLException {
		Connection c = DBConnection.getDBConnection();
		PreparedStatement prep = c.prepareStatement("DELETE FROM ListeFollow WHERE identifiant=?");
		prep.setString(1, this.identifiant);
		prep.execute();
		System.out.println("Suppression dans ListeFollow");
		this.numFollow=-1;
		this.identifiant="-1";
		this.follow="-1";
	}


	public void save() throws SQLException {
		if (this.numFollow==-1){
			this.saveNew();
		}else {
			this.update();
		}
	}


	private void saveNew() throws SQLException {
		Connection c = DBConnection.getDBConnection();
		String SQLPrep = "INSERT INTO ListeFollow (identifiant,follow) VALUES (?,?);";
		PreparedStatement prep = c.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);			
		prep.setString(1, this.identifiant);
		prep.setString(2, this.follow);
		prep.executeUpdate();
		System.out.println("ajout Follow");

		int autoInc = -1;
		ResultSet rs = prep.getGeneratedKeys();
		if (rs.next()) {
			autoInc = rs.getInt(1);
		}
		System.out.print("  ->  numFollow utilise lors de l'ajout : ");
		System.out.println(autoInc);
		this.numFollow=autoInc;
		System.out.println();

	}


	private void update() throws SQLException {
		/*
		Connection c = DBConnection.getDBConnection();
		String SQLprep = "update ListeFollow set identifiant=? and follow=? where numFollow=?;";
		PreparedStatement prep = c.prepareStatement(SQLprep);
		prep.setString(1, this.identifiant);
		prep.setString(2, this.follow);
		prep.setInt(3, this.numFollow);
		prep.execute();
		 */
		System.out.println("le lien entre " + follow + " qui follow " + identifiant + " existe deja" );
	}
	

	@Override
	public String toString() {
		return "ListeFollow [numFollow=" + numFollow + ", identifiant=" + identifiant + ", follow=" + follow + "]";
	}

	/**
	 * @return the numFollow
	 */
	public int getNumFollow() {
		return numFollow;
	}

	/**
	 * @return the identifiant
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * @return the follow
	 */
	public String getFollow() {
		return follow;
	}



}
