package mvc;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Vue extends JTextArea implements Observateur{
	
	public Vue() {
		super(5,5);
	}

	public synchronized void actualiser(Sujet s) {
		Modele m= (Modele)s;
		this.setText(getText()+m.getCompte().get(m.getCompte().size()-1)+"\n");
	}
	
}
