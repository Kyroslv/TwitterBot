package mvc;

import java.awt.BorderLayout;
import java.sql.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Requetes.Requete;
import SherloBot.Compte;
import SherloBot.DBConnection;
import SherloBot.ListeFollow;
import SherloBot.ListeFollowes;

public class Menu {

	public Menu() throws SQLException {


		JFrame frame1=new JFrame();
		
		JPanel panneauDeControle= new JPanel(new GridLayout(2,1));
		JPanel panneau2 = new JPanel(new GridLayout(1,2));
		
		
		JButton b1=new JButton("Requêtes");
		ImageIcon img1 = new ImageIcon("img\\requete.jpg");
		b1.setIcon(img1);
		b1.setHorizontalTextPosition(JLabel.CENTER);
		b1.setFont(new Font("arial",Font.BOLD,20));
		b1.setForeground(Color.BLUE);
		
		
		JButton b2=new JButton("Analyse");
		ImageIcon img2 = new ImageIcon("img\\Analyse.jpg");
		b2.setIcon(img2);
		b2.setHorizontalTextPosition(JLabel.CENTER);
		b2.setFont(new Font("arial",Font.BOLD,20));
		b2.setForeground(Color.BLUE);
		
		JLabel txt=new JLabel("Que voulez vous faire?",SwingConstants.CENTER);
		txt.setFont(new Font("arial",Font.BOLD,30));
		ImageIcon img3 = new ImageIcon("img\\twitter.jpg");
		txt.setIcon(img3);
		txt.setHorizontalTextPosition(JLabel.CENTER);
		

		
		
		
		panneauDeControle.add(txt);
		panneauDeControle.add(panneau2);
		
		panneau2.add(b1);
		panneau2.add(b2);
		Connection c = DBConnection.getDBConnection();
		
		int tCompte = 0, tLik = 0, tListeFollow = 0, tListeFollowes = 0, tTweet = 0;
		PreparedStatement compte = c.prepareStatement("select * from compte");
		PreparedStatement lik = c.prepareStatement("select * from lik");
		PreparedStatement listefollow = c.prepareStatement("select * from listefollow");
		PreparedStatement listefollowes = c.prepareStatement("select * from listefollowes");
		PreparedStatement tweet = c.prepareStatement("select * from tweet");

		ResultSet rsCompte = compte.executeQuery();
		while(rsCompte.next()){
		tCompte = rsCompte.getRow();
		}
		ResultSet rsLik = lik.executeQuery();
		while(rsLik.next()){
		tLik = rsLik.getRow();
		}
		ResultSet rsListefollow = listefollow.executeQuery();
		while(rsListefollow.next()){
		tListeFollow = rsListefollow.getRow();
		}
		ResultSet rsListefollowes = listefollowes.executeQuery();
		while(rsListefollowes.next()){
		tListeFollowes = rsListefollowes.getRow();
		}
		ResultSet rsTweet = tweet.executeQuery();
		while(rsTweet.next()){
		tTweet = rsTweet.getRow();
		}
		if (tCompte ==0) {
			if (tLik ==0) {
				if (tListeFollow ==0) {
					if (tListeFollowes ==0) {
						if (tTweet ==0) {
							b1.setEnabled(false);
						}
					}
				}
			}
		}
		
		frame1.getContentPane().add(panneauDeControle);

		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(new Dimension(400,400));
		frame1.setVisible(true);



		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame1.setVisible(false);
				new Requete();
			}
		});


		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame1.setVisible(false);
				try {
					new Analyse();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

}
