package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ControleurP implements ActionListener{

	private Modele modele;
	private JButton b1;
	private JButton b2;
	
	public ControleurP(Modele m, JButton b1,JButton b2) {
		this.modele=m;
		this.b1=b1;
		this.b2=b2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
