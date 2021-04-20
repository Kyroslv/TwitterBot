package SherloBot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import mvc.Modele;
//"@ElNa32534207"
public class SherloBot {
	
	/*
	 * ---------------------------------------------------------------------
	 * |||||||||||||||||||||||||||VARIABLE CHANGE|||||||||||||||||||||||||||
	 * ---------------------------------------------------------------------
	 */	
	
	/*
	 * Chemin pour infos compte
	 */
	private final String Identifiant = ".css-901oao.css-bfa6kz.r-9ilb82.r-18u37iz.r-1qd0xha.r-a023e6.r-16dba41.r-rjixqe.r-bcqeeo.r-qvutc0";
	private final String Pseudo = "div.css-901oao.r-1fmj7o5.r-1qd0xha.r-adyw6z.r-1vr29t4.r-135wba7.r-bcqeeo.r-1udh08x.r-qvutc0";
	private final String Biographie ="div.css-1dbjc4n.r-1adg3ll.r-6gpygo";
	private final String Localisation ="span.css-901oao.css-16my406.r-9ilb82.r-4qtqp9.r-poiln3.r-1b7u577.r-bcqeeo.r-qvutc0";
	private final String DateCreation =Localisation;
	private final String Follower ="div.css-901oao.r-1fmj7o5.r-1qd0xha.r-a023e6.r-16dba41.r-rjixqe.r-bcqeeo.r-1h8ys4a.r-1jeg54m.r-qvutc0";
	private final String Followes =Follower;

	/*
	 * Chemin pour infos tweet
	 */
	private final String Tweet ="div.css-1dbjc4n.r-1iusvr4.r-16y2uox.r-ttdzmv";
	private final String Status="a.css-4rbku5.css-18t94o4.css-901oao.r-9ilb82.r-1loqt21.r-1qd0xha.r-a023e6.r-16dba41.r-ad9z0x.r-bcqeeo.r-qvutc0";
	private final String PseudoTweet="div.css-901oao.css-bfa6kz.r-1fmj7o5.r-1qd0xha.r-a023e6.r-b88u0q.r-rjixqe.r-bcqeeo.r-1udh08x.r-3s2u2q.r-qvutc0";
	private final String Arobase=Identifiant;
	private final String Texte="div.css-901oao.r-1fmj7o5.r-1qd0xha.r-1blvdjr.r-16dba41.r-vrz42v.r-bcqeeo.r-bnwqim.r-qvutc0";
	private final String Image="div.css-1dbjc4n.r-1kqtdi0.r-1867qdf.r-1phboty.r-rs99b7.r-1s2bzr4.r-1ny4l3l.r-1udh08x.r-o7ynqc.r-6416eg";
	private final String Retweet = "div.css-1dbjc4n.r-xoduu5.r-1udh08x";
	private final String Like=Retweet;
	private final String DateTweet="a.css-4rbku5.css-18t94o4.css-901oao.css-16my406.r-9ilb82.r-1loqt21.r-poiln3.r-bcqeeo.r-qvutc0";

	TConnexion con;
	Modele modele;
	Boolean anal = false;
	private int nbAnalyse;

	/*
	 * ---------------------------------------------------------------------
	 * |||||||||||||||||||||||ARTIFICIAL INTELLIGENCE|||||||||||||||||||||||
	 * ---------------------------------------------------------------------
	 */	

	public SherloBot(Modele m) throws SQLException {
		this.modele=m;
	}

	/*
	 * permet d'analyser les comptes
	 */
	public void AnalyseCompte(String compte) throws SQLException {

		//se connecte au compte twitter
		this.connexion();

		//test si le compte a deja ete analysé
		this.TestAnalyse(compte);

		if(anal && nbAnalyse<modele.nbC) {

			//permet d'acceder a la page du compte recherche
			this.Recherche(compte);

			//recupere les infos du compte
			Compte m = this.CompteInfo();
			m.save();

			//recupere la liste des followers
			ArrayList<String> listFollower = this.RecupererListeFollower(compte);
			System.out.println(listFollower);

			//recupere la liste des followers
			ArrayList<String> listFollowes = this.RecupererListeFollowes(compte);
			System.out.println(listFollowes);	

			//recupere les infos des tweets
			this.TweetInfo(compte);

			//recupere les infos des likes
			this.LikeInfo(compte);

			//permet d'indiquer que le compte a été analysé
			Compte c = new Compte(compte,"oui");
			c.save();

			modele.ajoutTexte(compte + " analysé");

			System.out.println("Analyse "+listFollower);


			con.driver.get("https://twitter.com/home");

			this.nbAnalyse ++;


			//permet d'analyser les comptes de la liste des followers
			for (int i=0; i<listFollower.size();i++) {
				con.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.println("Analyse"+listFollower.get(i));

				//appel recursif
				this.AnalyseCompte(listFollower.get(i));
			}

			System.out.println("Analyse "+listFollowes);

			//permet d'analyser les comptes de la liste des followers
			for (int i=0; i<listFollowes.size();i++) {
				con.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.println("Analyse"+listFollowes.get(i));

				//appel recursif
				this.AnalyseCompte(listFollowes.get(i));
			}
		}
		System.out.println(nbAnalyse +" comptes analysé");		
	}

	/*
	 * permet de laisser charger la page pour eviter les erreurs
	 */
	public void chargement() {

		try {
			Thread.sleep(1000, 0);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

	/*
	 * permet au bot de se connecter a son compte
	 */
	public void connexion() throws SQLException {
		try{
			con = TConnexion.getTConnection();

		}catch (Exception e) {
			System.out.println("ERREUR, essaie d'une nouvelle connexion");
			System.out.println(e);
			con = TConnexion.getTConnection();		
		}
		System.out.println("Connexion reussis");
	}	

	/*
	 * permet de rechercher un compte et d'acceder a sa page
	 */
	public void Recherche(String compte) {

		//permet de laisser charger
		this.chargement();

		con.driver.get("https://twitter.com/"+compte);

	}

	/*
	 * -------------------------------------
	 * |||||||||||||||ACCOUNT|||||||||||||||
	 * -------------------------------------
	 */	

	public Compte CompteInfo() throws SQLException {

		String id;
		String ps;
		String bio;
		String loc;
		String dat;
		try {
			id = this.RecupererIdentifiant();
		}catch(Exception e) {
			id= null;
		}

		try {
			ps = this.RecupererPseudo();
		}catch(Exception e) {
			ps= null;
		}

		try {
			bio = this.RecupererBiographie();
		}catch(Exception e) {
			bio= null;
		}

		try {
			loc = this.RecupererLocalisation();
		}catch(Exception e) {
			loc= null;
		}

		try {
			dat = this.RecupererDateCreation();
		}catch(Exception e) {
			dat = null;
		}

		System.out.println("||COMPTE||-_-_-_-_-_-_-_-_-_-_\nIdentifiant : "+id + "\nPseudo : "+ ps + "\nBiographie : "+ bio + "\nLocalisation : "+ loc + 
				"\nDate de creation : "+ dat+"\n-_-_-_-_-_-_-_-_-_-_");

		System.out.println("-----------------");

		Compte c = new Compte (id,ps,bio,loc,dat);

		return c;
	}

	/*
	 * -------------------------------------
	 * ||||||||||||||||TWEET||||||||||||||||
	 * -------------------------------------
	 */	

	public void TweetInfo(String compte) throws SQLException {

		String status;
		String pseudo;
		String arob;
		String txt;
		String img;
		String retweet;
		String like;
		String date;

		List<WebElement> t =con.driver.findElements(By.cssSelector(Tweet));

		for (int i=0;i<t.size();i++) {

			this.ScrollTweet(i);

			try {
				status = this.RecupererStatusTweet();
			}catch(Exception e) {	
				status ="Tweet"; 
			}


			try{
				pseudo = this.RecupererPseudoTweet();
			}catch(Exception e) {	
				pseudo = null;
			}

			try{
				arob = this.RecupererArobaseTweet();
			}catch(Exception e) {	
				arob = null;
			}

			try{
				txt = this.RecupererTexteTweet();
			}catch(Exception e) {	
				txt = null;
			}

			try{
				img = this.RecupererImgTweet();			
			}catch(Exception e) {	
				img = null;
			}

			try {
				retweet = this.RecupererRetweetTweet();			
			}catch(Exception e) {	
				retweet = null;
			}

			try {
				like = this.RecupererLikeTweet();			
			}catch(Exception e) {	
				like = null;
			}

			try{
				date = this.RecupererDateTweet();			
			}catch(Exception e) {	
				date = null;
			}

			con.driver.navigate().back();	
			
			int ret = 0;
			if (retweet.equals("")) {
				ret = 0;
			}else {
				ret = Integer.parseInt(retweet);
			}

			int lik = 0;
			if (like.equals("")) {
				lik = 0;
			}else {
				lik = Integer.parseInt(like);
			}

			System.out.println("\n||TWEET||-_-_-_-_-_-_-_-_\nStatus : "+status + "\nPseudo : "+ pseudo + "\nArobase : "+ arob + "\nTexte : \n--------------------\n"+ 
					txt + "\n--------------------\nImage : "+ img + "\nRetweet : "+ ret+ "\nLike : "+ lik+ "\nDate : "+ date +"\n-_-_-_-_-_-_-_-_-_-_");

			System.out.println("-----------------\n");		

			Compte c1 = Compte.findByIdentifiant(compte);
			Compte c2 = Compte.findByIdentifiant(arob);

			if (c2 == null) {
				con.driver.get("https://twitter.com/"+arob);
				c2= this.CompteInfo();
				c2.save();
				con.driver.navigate().back();
			}

			Tweet tweet = new Tweet(c1, status, c2, pseudo, txt, img, ret, lik, date);
			tweet.save();
		}
	}

	/*
	 * -------------------------------------
	 * ||||||||||||||||LIKE||||||||||||||||
	 * -------------------------------------
	 */	

	public void LikeInfo(String compte) throws SQLException {

		con.driver.get("https://twitter.com/"+compte+"/likes");

		String status;
		String pseudo;
		String arob;
		String txt;
		String img;
		String retweet;
		String like;
		String date;

		List<WebElement> t =con.driver.findElements(By.cssSelector(Tweet));

		for (int i=0;i<t.size();i++) {

			this.ScrollTweet(i);

			try{
				pseudo = this.RecupererPseudoTweet();
			}catch(Exception e) {	
				pseudo = null;
			}

			try{
				arob = this.RecupererArobaseTweet();
			}catch(Exception e) {	
				arob = null;
			}

			try{
				txt = this.RecupererTexteTweet();
			}catch(Exception e) {	
				txt = null;
			}

			try{
				img = this.RecupererImgTweet();			
			}catch(Exception e) {	
				img = null;
			}

			try {
				retweet = this.RecupererRetweetTweet();			
			}catch(Exception e) {	
				retweet = null;
			}

			try {
				like = this.RecupererLikeTweet();			
			}catch(Exception e) {	
				like = null;
			}

			try{
				date = this.RecupererDateTweet();			
			}catch(Exception e) {	
				date = null;
			}

			con.driver.navigate().back();	

			int ret = 0;
			if (retweet.equals("")) {
				ret = 0;
			}else {
				ret = Integer.parseInt(retweet);
			}

			int likes = 0;
			if (like.equals("")) {
				likes = 0;
			}else {
				likes = Integer.parseInt(like);
			}
			
			System.out.println("\n||LIKE||-_-_-_-_-_-_-_-_\nPseudo : "+ pseudo + "\nArobase : "+ arob + "\nTexte : \n--------------------\n"+ 
					txt + "\n--------------------\nImage : "+ img + "\nRetweet : "+ ret+ "\nLike : "+ likes+ "\nDate : "+ date +"\n-_-_-_-_-_-_-_-_-_-_");

			System.out.println("-----------------\n");		

			Compte c1 = Compte.findByIdentifiant(compte);
			Compte c2 = Compte.findByIdentifiant(arob);

			
			
			Lik l = new Lik(c1, c2, pseudo, txt, img, ret, likes, date);
			l.save();
		}
	}

	/*
	 * Permet de tester si un compte a deja ete ou non analysé
	 */
	public void TestAnalyse(String compte) throws SQLException {

		Compte analy = Compte.findByIdentifiant(compte);

		if(analy == null) {
			this.anal = true;

		}else {
			if(analy.getAnalyser().equals("non")) {
				this.anal=true;
			}else {
				this.anal=false;
				modele.ajoutTexte(compte + "deja analysé");
			}
		}

	}

	/*
	 * ---------------------------------------------------------------------
	 * |||||||||||||||||||||||||||||DATA MINING|||||||||||||||||||||||||||||
	 * ---------------------------------------------------------------------
	 */

	/*
	 * -------------------------------------
	 * |||||||||||||||ACCOUNT|||||||||||||||
	 * -------------------------------------
	 */	

	/*
	 * Permet de recuperer l'identifiant sur la page d'un compte
	 */
	public String RecupererIdentifiant() throws SQLException{

		//permet de laisser charger
		this.chargement();

		String lI = "";

		List<WebElement> id = con.driver.findElements(By.cssSelector("#react-root "+Identifiant));

		lI = id.get(1).getText();


		return lI;	
	}

	/*
	 * Permet de recuperer le pseudo sur la page d'un compte
	 */
	public String RecupererPseudo() throws SQLException{

		String lI = "";

		List<WebElement> ps = con.driver.findElements(By.cssSelector("#react-root "+Pseudo));

		lI = ps.get(0).getText();

		return lI;	
	}

	/*
	 * Permet de recuperer la biographie sur la page d'un compte
	 */
	public String RecupererBiographie() throws SQLException{

		List<WebElement> bio = con.driver.findElements(By.cssSelector("#react-root "+Biographie));
		String lI =bio.get(0).getText();

		lI = lI.replace("Translate bio", "");

		return lI;	
	}

	/*
	 * Permet de recuperer la localisation sur la page d'un compte
	 */
	public String RecupererLocalisation() throws SQLException{

		String lI = "";

		List<WebElement> ps = con.driver.findElements(By.cssSelector("#react-root "+Localisation));

		try {
			String t = ps.get(1).getText();
			lI = ps.get(0).getText();
		} catch(Exception e) {
			lI=null;
		}



		return lI;	
	}

	/*
	 * Permet de recuperer la date de creation sur la page d'un compte
	 */
	public String RecupererDateCreation() throws SQLException{

		String lI = "";

		List<WebElement> ps = con.driver.findElements(By.cssSelector("#react-root "+DateCreation));

		try {
			lI = ps.get(1).getText();
		} catch(Exception e) {
			lI = ps.get(0).getText();
		}

		return lI;		
	}

	/**
	 * Permet de recuperer les identifiants des comptes qui follows
	 * @param compte
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> RecupererListeFollower(String compte) throws SQLException{

		//permet de laisser charger
		this.chargement();

		ArrayList<String> lF = new ArrayList<String>();

		con.driver.get(con.driver.getCurrentUrl()+"/followers");

		List<WebElement> lFolwer =con.driver.findElements(By.cssSelector("#react-root "+Follower));

		int nb=5;
		if (lFolwer.size()<nb) {
			nb=lFolwer.size();
		}
		for (int i=0;i<nb;i++) {

			//permet de laisser charger
			this.chargement();

			lFolwer =con.driver.findElements(By.cssSelector("#react-root "+Follower));
			con.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			lFolwer.get(i).click();

			Compte c2 = this.CompteInfo();
			c2.save();

			Compte c1 = new Compte(compte);
			c1.save();

			ListeFollow lf = new ListeFollow (c1,c2);
			lf.save();
			lF.add(c2.getIdentifiant());
			modele.ajoutTexte("Ajout dans \"Liste Follower\" : " + c2.getIdentifiant());

			con.driver.navigate().back();

			System.out.println("-----------------");

		}
		con.driver.navigate().back();
		return lF;
	}

	public ArrayList<String> RecupererListeFollowes(String compte) throws SQLException{

		//permet de laisser charger
		this.chargement();

		ArrayList<String> lFes = new ArrayList<String>();

		con.driver.get(con.driver.getCurrentUrl()+"/following");

		List<WebElement> lFolwes =con.driver.findElements(By.cssSelector("#react-root "+Followes));

		int nb=5;
		if (lFolwes.size()<nb) {
			nb=lFolwes.size();
		}
		for (int i=0;i<nb;i++) {

			//permet de laisser charger
			this.chargement();

			lFolwes =con.driver.findElements(By.cssSelector("#react-root "+Followes));
			con.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			lFolwes.get(i).click();

			Compte c2 = this.CompteInfo();
			c2.save();

			Compte c1 = new Compte(compte);
			c1.save();


			ListeFollowes lf = new ListeFollowes (c1,c2);
			lf.save();
			lFes.add(c2.getIdentifiant());
			modele.ajoutTexte("Ajout dans \"Liste Followes\" : " + c2.getIdentifiant());

			con.driver.navigate().back();

			System.out.println("-----------------");

		}
		con.driver.navigate().back();
		return lFes;
	}

	/*
	 * -------------------------------------
	 * ||||||||||||||||TWEET||||||||||||||||
	 * -------------------------------------
	 */	

	public void ScrollTweet(int i) throws SQLException{

		JavascriptExecutor js = (JavascriptExecutor) con.driver;

		int x =0;
		int y = 20;

		List<WebElement> test5 =con.driver.findElements(By.cssSelector(Tweet));		

		try {
			test5.get(i).click();
		}catch(Exception e) {
			boolean continuer=true;
			while (continuer) {
				try {
					js.executeScript("window.scrollBy("+x+","+y+")");
					x+=20;
					y+=20;
					test5.get(i).click();
					continuer = false;
				}catch (Exception exc) {}
			}

		}
	}

	public String RecupererStatusTweet() throws SQLException{

		List<WebElement> status =con.driver.findElements(By.cssSelector("#react-root "+this.Status));
		String r = status.get(0).getText().split(" ")[status.get(0).getText().split(" ").length-1];

		return r;	
	}

	public String RecupererPseudoTweet() throws SQLException{

		List<WebElement> pseudo =con.driver.findElements(By.cssSelector("#react-root "+this.PseudoTweet));
		String r = pseudo.get(1).getText();

		return r;	
	}

	public String RecupererArobaseTweet() throws SQLException{

		List<WebElement> arob =con.driver.findElements(By.cssSelector("#react-root "+this.Arobase));
		String r = arob.get(1).getText();

		return r;	
	}

	public String RecupererTexteTweet() throws SQLException{

		List<WebElement> txt =con.driver.findElements(By.cssSelector("#react-root "+this.Texte));
		String r = txt.get(0).getText();

		return r;	
	}

	public String RecupererImgTweet() throws SQLException{

		String r = null;
		List<WebElement> img =con.driver.findElements(By.cssSelector("#react-root "+ this.Image));
		img.get(0).click();
		List<WebElement> url=con.driver.findElements(By.tagName("img"));

		if (url.size() == 5) {
			if (url.get(3).getAttribute("src").split("/")[3].equals("media")) {
				r = url.get(3).getAttribute("src");
			}
		}else if (url.size() == 4) {
			if (url.get(2).getAttribute("src").split("/")[3].equals("media")) {
				r = url.get(2).getAttribute("src");
			}
		}else if (url.size() == 6) {
			if (url.get(4).getAttribute("src").split("/")[3].equals("media")) {
				r = url.get(4).getAttribute("src");
			}
		}else if (url.size() == 7) {
			if (url.get(4).getAttribute("src").split("/")[3].equals("media")) {
				r = url.get(4).getAttribute("src");
			}
		}else {
			if (url.get(4).getAttribute("src").split("/")[3].equals("media")) {
				r = url.get(4).getAttribute("src");
			}
		}
		

		con.driver.navigate().back();

		return r;	
	}

	public String RecupererRetweetTweet() throws SQLException{

		String r=null;

		List<WebElement> rt =con.driver.findElements(By.cssSelector("#react-root "+ Retweet));
		try {
			r = rt.get(0).getText();
		}catch(Exception e) {
			r = "0";
		}

		return r;	
	}

	public String RecupererLikeTweet() throws SQLException{
		String r=null;

		List<WebElement> rt =con.driver.findElements(By.cssSelector("#react-root "+ Like));
		try {
			r = rt.get(1).getText();
		}catch(Exception e) {
			r = "0";
		}

		return r;	
	}

	public String RecupererDateTweet() throws SQLException{

		List<WebElement> d =con.driver.findElements(By.cssSelector("#react-root "+this.DateTweet));
		String r = d.get(0).getText();

		return r;	
	}

}
