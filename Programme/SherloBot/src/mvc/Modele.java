package mvc;

import java.sql.SQLException;
import java.util.ArrayList;

import SherloBot.SherloBot;

public class Modele implements Sujet {

	SherloBot sherlo;
	private ArrayList<Observateur> observateurs;
	private ArrayList<String> compte;
	public int nbC;

	public Modele(Vue v) throws SQLException {

		this.observateurs = new ArrayList<Observateur> ();
		observateurs.add(v);
		this.compte = new ArrayList<String> ();
		this.sherlo = new SherloBot(this);
		sherlo.connexion();

	}

	public void modifier(String txt,String nb) throws SQLException{
		if (txt.equals("")) {
			txt = "@SherloTob";
		}
		this.compte.add(txt);


		if (nb.equals("")) {
			this.nbC = 10;
		}else {
			this.nbC = Integer.parseInt(nb);
		}

		this.notifierObservateurs();

		sherlo.AnalyseCompte(txt);
	}

	public void ajoutTexte(String txt) throws SQLException{
		this.compte.add(txt);
		this.notifierObservateurs();

	}

	public void enregistrerObservateur(Observateur o) {
		this.observateurs.add(o);
	}

	public void supprimerObservateur(Observateur o) {
		int i = this.observateurs.indexOf(o);
		if (i >= 0) {
			this.observateurs.remove(i);
		}
	}

	public synchronized void notifierObservateurs() {
		for (int i = 0; i < this.observateurs.size(); i++) {
			Observateur observer = this.observateurs.get(i);
			observer.actualiser(this);
		}
	}

	public ArrayList<String> getCompte() {
		return compte;
	}

	public void setCompte(ArrayList<String> compte) {
		this.compte = compte;
	}





}
