package SherloBot;

import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Test {
	
	TConnexion con;
	
	public void connexion() throws SQLException {
		try{
			con = TConnexion.getTConnection();

		}catch (Exception e) {
			System.out.println("ERREUR, essaie d'une nouvelle connexion");
			System.out.println(e);
			con = TConnexion.getTConnection();		
		}
		System.out.println("Connexion reussis");
		
		//Connexion effectué auparavant mais trop complexe à expliquer
		
		//accès a la page twitter
		con.driver.get("https://twitter.com/SherloTob");

		// Initalisation d'un variable qui va servir a stocker le résultat
		String r = null;
		
		//utilisation du selenium, creer une liste d'element Web 
		//qui correspond aux condition que l'on à paramétré
		List<WebElement> img =con.driver.findElements(By.cssSelector("#react-root "
		+ ".css-901oao.css-bfa6kz.r-9ilb82.r-18u37iz.r-1qd0xha.r-a023e6.r-16dba41.r-rjixqe.r-bcqeeo.r-qvutc0"));

		//assigne à r la valeur 0 de la liste sous forme de texte  
		r = img.get(0).getText();
		
		//Affichage de la variable r qui contient la solution
		System.out.println(r);	
	}	
	
	public static void main(String args[]) throws SQLException {
		Test t = new Test();
		t.connexion();
		
		
	}
	
}
