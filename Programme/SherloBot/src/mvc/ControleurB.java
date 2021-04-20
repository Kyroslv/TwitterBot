package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTextField;

public class ControleurB implements ActionListener {
	
	private Modele modele;
	private JButton b;
	private JTextField c;
	private JTextField n;
	
	public ControleurB(Modele m, JButton b,JTextField c,JTextField n) {

		this.modele=m;
		this.b=b;
		this.c=c;
		this.n=n;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String txt=c.getText();
		String nb=n.getText();

		if (!txt.equals("") &&  !txt.substring(0,1).equals("@")) txt = "@"+txt;
		
		try {
			modele.modifier(txt,nb);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		c.setText("");
		n.setText("");	
	}


}
