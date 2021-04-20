package Requetes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import SherloBot.DBConnection;


// TODO: Auto-generated Javadoc
/**
 * The Class Principale.
 */
public class Requete {

	JTextArea donnees = new JTextArea("");

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public Requete() {
		JPanel panelBas = new JPanel(new BorderLayout());
		JPanel panelHaut = new JPanel(new BorderLayout());
		JPanel panelIndication = new JPanel(new BorderLayout());
		JPanel panelSaisie = new JPanel(new BorderLayout());
		JPanel panelValider = new JPanel(new BorderLayout());
		JPanel panelDonnees = new JPanel(new BorderLayout());

		JLabel titre= new JLabel("Requete Twitter");
		titre.setPreferredSize(new Dimension(1000,60));
		titre.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel indication= new JLabel("Saisissez votre requête ici :");
		indication.setPreferredSize(new Dimension(1000,30));

		JLabel textRequete= new JLabel("Cliquez sur les différents boutons pour former votre requête");
		textRequete.setPreferredSize(new Dimension(400,20));

		/*
		 * permet un affichage plus agrï¿½able
		 * */
		JLabel vide=new JLabel(); vide.setPreferredSize(new Dimension(50,20));
		JLabel vide2=new JLabel(); vide2.setPreferredSize(new Dimension(40,20));
		JLabel vide4=new JLabel(); vide4.setPreferredSize(new Dimension(400,30));
		JLabel vide5=new JLabel(); vide5.setPreferredSize(new Dimension(400,30));
		JLabel vide6=new JLabel(); vide6.setPreferredSize(new Dimension(40,20));
		JLabel vide7=new JLabel(); vide7.setPreferredSize(new Dimension(40,30));



		donnees.setRows(25);
		donnees.setColumns(25);
		donnees.setWrapStyleWord(true);


		JScrollPane Scroll = new JScrollPane(donnees);
		Scroll.setBounds(10,60,780,500);
		Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


		JButton bouton = new JButton("Comptes entièrement analysés");
		JButton bouton2 = new JButton("Tweets analysés comme retweeté");
		JButton bouton3 = new JButton("Tweets liké qui ont plus de 2 likes");
		JButton bouton4 = new JButton("Comptes qui ont plus de 2 follow");
		JPanel panelBouton = new JPanel(new GridLayout(1,8));
		panelBouton.setPreferredSize(new Dimension(1000,30));
		panelBouton.add(bouton);
		panelBouton.add(bouton2);
		panelBouton.add(bouton3);
		panelBouton.add(bouton4);

		JButton valider = new JButton("Valider");
		valider.setPreferredSize(new Dimension(50,30));

		JTextField saisie = new JTextField("Select * from Compte");
		saisie.setPreferredSize(new Dimension(920,20));

		panelDonnees.add(vide7,BorderLayout.WEST);

		panelDonnees.add(Scroll,BorderLayout.CENTER);




		panelSaisie.add(vide6,BorderLayout.WEST);
		panelSaisie.add(vide2,BorderLayout.EAST);
		panelSaisie.add(saisie,BorderLayout.CENTER);

		panelValider.add(vide4,BorderLayout.WEST);
		panelValider.add(vide5,BorderLayout.EAST);
		panelValider.add(valider,BorderLayout.CENTER);

		panelBas.add(panelValider,BorderLayout.CENTER);
		panelBas.add(panelDonnees,BorderLayout.SOUTH);
		panelBas.add(panelSaisie,BorderLayout.NORTH);

		panelIndication.add(vide,BorderLayout.WEST);
		panelIndication.add(indication,BorderLayout.CENTER);
		panelIndication.add(textRequete,BorderLayout.EAST);

		panelHaut.add(titre,BorderLayout.NORTH);
		panelHaut.add(panelIndication,BorderLayout.SOUTH);
		panelHaut.add(panelBouton,BorderLayout.CENTER);


		JFrame f =new JFrame();
		f.getContentPane().add(panelBas,BorderLayout.CENTER);
		f.getContentPane().add(panelHaut,BorderLayout.NORTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(new Dimension(1000,600));
		f.setVisible(true);


		bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisie.setText("Select * from Compte where analyser like \"oui\"" );

			}
		});

		bouton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisie.setText("Select * from Tweet where status like \"Retweeted\"" );
			}
		});

		bouton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisie.setText("Select * from Lik where likes>2" );

			}
		});

		bouton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saisie.setText("Select * from listefollow group by identifiant having count(identifiant)>2 " );

			}
		});

		valider.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					donnees.setText(Req(saisie.getText()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

	}

	public String Req(String r) throws SQLException {

		String res="";
		int i =0;
		Connection c;
		c = DBConnection.getDBConnection();
		String FI=r;
		PreparedStatement getS = c.prepareStatement(FI);

		try{
			donnees.setForeground (Color.black);
			ResultSet rs = getS.executeQuery();
			ArrayList<String> tab = this.separe(r.toLowerCase().split(" "));

			if (tab.get(1).equals("*")) {
				switch(tab.get(2)) {
				case "compte" :
					while (rs.next()) {
						res += this.getInfos(rs,0);
						i++;
					}
					if (i != 0) {
						res ="La requête a trouvé "+ i +" solution\n\n--------------------\n" +res;
					}else {
						res ="La requête n'a pas trouvé de solution";
					}

					break;
				case "tweet" :
					while (rs.next()) {
						res += this.getInfos(rs,1);
						i++;
					}
					if (i != 0) {
						res ="La requête a trouvé "+ i +" solution\n\n--------------------\n" +res;
					}else {
						res ="La requête n'a pas trouvé de solution";
					}
					break;
				case "lik" :
					while (rs.next()) {
						res += this.getInfos(rs,2);
						i++;
					}
					if (i != 0) {
						res ="La requête a trouvé "+ i +" solution\n\n--------------------\n" +res;
					}else {
						res ="La requête n'a pas trouvé de solution";
					}
					break;
				case "listefollowes" :
					while (rs.next()) {
						res += this.getInfos(rs,3);
						i++;
					}
					if (i != 0) {
						res ="La requête a trouvé "+ i +" solution\n\n--------------------\n" +res;
					}else {
						res ="La requête n'a pas trouvé de solution";
					}
					break;
				case "listefollow" :
					while (rs.next()) {
						res += this.getInfos(rs,4);
						i++;
					}
					if (i != 0) {
						res ="La requête a trouvé "+ i +" solution\n\n--------------------\n" +res;
					}else {
						res ="La requête n'a pas trouvé de solution";
					}
					break;
				default: res += "ERREUR";

				}

			}else {
				String[] analys = tab.get(1).split(" ");

				while (rs.next()) {
					for (int z=0;z<analys.length;z++) {
						res += analys[z] +" : "+rs.getString(analys[z]) + "\n";
					}
					res += "--------------------\n";
					i++;
				}
				if (i != 0) {
					res ="La requête a trouvé "+ i +" solution\n--------------------\n" +res;
				}else {
					res ="La requête n'a pas trouvé de solution";
				}

			}
		}catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			res ="||ERREUR|| Erreur de syntaxe dans la requête ||ERREUR||\n";
			donnees.setForeground (Color.red);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			String[] tab = sw.toString().split(" ");
			int g=1;
			boolean cont = true;
			while(cont) {
				try {
					if (!tab[g].substring(0,4).equals("java")) {
						g++;
					}else {
						cont = false;
					}
				}catch(Exception ex) {
					g++;
				}
				
				
			}
			String err="";
			for (int q=1;q<g-1;q++) {
				err+=tab[q]+" ";
			}
			err+=tab[g-1].replace("at","");
			res+=err;
		}

		return res;
	}

	public ArrayList<String> separe(String[] strings) {
		ArrayList<String> res = new ArrayList<String>();

		int a=0;
		boolean s= false;
		while (!strings[a].equals("select")) {
			a++;
		}
		res.add(strings[a]) ;
		if (strings[a+1].equals("distinct")) {
			s = true;
			a++;
		}
		int j = 0;
		while (!strings[j].equals("from")) {
			j++;
		}

		String res2 = "";
		for (int k =a+1;k<j;k++) {
			res2+= strings[k].trim().replace(',', ' ');

		}

		res.add(res2) ;

		res.add(strings[j+1]) ;

		if (s) {
			res.add(strings[a]);
		}

		return res;

	}

	public String getInfos(ResultSet rs ,int i) throws SQLException {
		String res = "";

		//Compte
		if (i==0) {
			String id = rs.getString("identifiant");
			String ps = rs.getString("pseudo");
			String bi = rs.getString("biographie");
			String l = rs.getString("lieu");
			String d = rs.getString("dateCreation");
			String analyser = rs.getString("analyser");
			res +="Identifiant : "+ id+"\nPseudo : "+ ps+"\nBiographie : "+ bi+"\nLieu : "+ l +"\nDate de création : "+d+"\nCompte fini d'être analysé : "+ analyser + "\n--------------------\n";
		}

		//Tweet
		if (i==1) {
			String co = rs.getString("compte");
			String s = rs.getString("status");
			String iT = rs.getString("identifiantTweet");
			String pT = rs.getString("pseudoTweet");
			String t = rs.getString("tweet");
			String img = rs.getString("image");
			int ret = rs.getInt("retweet");
			int l = rs.getInt("lik");
			String d = rs.getString("dateTweet");
			res +="Compte où le tweet a été trouvé : "+ co+"\nC'est un Tweet ou Retweet : "+ s+"\nCompte de celui qui a tweeté : "+ iT+"\nPseudo de celui qui a tweeté : "+pT+"\nTexte de tweet : \n-_-_-_-_-_-_\n"+ t + "\n-_-_-_-_-_-_\nImage du tweet : "+ img + "\nNombre de retweet : "+ret+ "\nNombre de like : "+l+ "\nDate du tweet : "+d+"\n--------------------\n";
		}

		//Like
		if(i==2) {
			String co = rs.getString("compte");
			String iT = rs.getString("identifiantLik");
			String pT = rs.getString("pseudoLik");
			String t = rs.getString("tweet");
			String img = rs.getString("image");
			int ret = rs.getInt("retweet");
			int l = rs.getInt("likes");
			String d = rs.getString("dateLik");
			res +="Compte où le tweet liké a été trouvé : "+ co+"\nCompte de celui qui a tweeté : "+ iT+"\nLieu : "+ l +"\nPseudo de celui qui a tweeté : "+pT+"\nTexte de tweet : \n-_-_-_-_-_-_\n"+ t + "\n-_-_-_-_-_-_\nImage du tweet : "+ img + "\nNombre de retweet : "+ret+ "\nNombre de like : "+l+ "\nDate du tweet : "+d+"\n--------------------\n";
		}

		//listeFollowes
		if(i==3) {
			String id = rs.getString("identifiant");
			String f = rs.getString("followes");
			res +="Compte analysé : "+ id+"\nCompte suivis par "+id+" : "+ f+"\n--------------------\n";
		}

		//listefollow
		if(i==4) {
			String id = rs.getString("identifiant");
			String f = rs.getString("follow");
			res +="Compte analysé : "+ id+"\nCompte qui suit "+id+" : "+ f+"\n--------------------\n";
		}
		return res;
	}
}
