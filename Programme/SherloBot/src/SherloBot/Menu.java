package SherloBot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mvc.ControleurB;
import mvc.Modele;
import mvc.Vue;

public class Menu {

	public Menu() throws SQLException {

		Vue v = new Vue();

		Modele modele = new Modele(v);		


		//JPanel Nord
		JPanel panneauDeControle= new JPanel(new GridLayout(2,2));
		panneauDeControle.add(new JLabel("Donner un compte à analyser "+"    ",JLabel.CENTER));

		JTextField txt = new JTextField("@SherloTob",10);

		txt.setPreferredSize(new Dimension(200,30));



		panneauDeControle.add(txt);
		
		panneauDeControle.add(new JLabel("Nombre de compte à analyser "+"    ",JLabel.CENTER));

		JTextField nbC = new JTextField("10",10);
		nbC.setPreferredSize(new Dimension(200,30));


		panneauDeControle.add(nbC);
		
		JButton b = new JButton("Lancer la recherche");	

		ControleurB control= new ControleurB(modele,b,txt,nbC);

		b.addActionListener(control);

		//Construction de l'IG dans une JFrame		 
		JFrame frame=new JFrame();
		frame.getContentPane().add(panneauDeControle,BorderLayout.NORTH);
		frame.getContentPane().add(b,BorderLayout.SOUTH);
		frame.getContentPane().add(new JScrollPane(v),BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(400,400));
		frame.setVisible(true);
	}

}
