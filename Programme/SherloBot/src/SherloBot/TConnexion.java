package SherloBot;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * La classe DBConnection.
 */
public class TConnexion {


	public WebDriver driver;
	private static TConnexion Tcon;

	/**
	 * Constructeur de la connexion
	 *
	 * @throws SQLException the SQL exception
	 */
	private TConnexion(){
		File chemin = new File(new File("").getAbsolutePath()).getParentFile();
		System.setProperty("webdriver.chrome.driver", chemin +"\\Driver\\chromedriver.exe");	
		driver = new ChromeDriver();
		driver.manage().window().maximize(); 
		driver.get("https://twitter.com");

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		try {
			System.out.println("chargement");
			Thread.sleep(2000, 0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<WebElement> Connect= driver.findElements(By.cssSelector("a.css-4rbku5.css-18t94o4.css-1dbjc4n.r-1niwhzg"));
		Connect.get(0).click();
		
		try {
			System.out.println("chargement");
			Thread.sleep(2000, 0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<WebElement> User =driver.findElements(By.name("session[username_or_email]"));
		List<WebElement> MDP =driver.findElements(By.name("session[password]"));
		
		User.get(0).sendKeys("@SherloTob");
		MDP.get(0).sendKeys("NqlBot400"+ Keys.ENTER);
		
		try {
			User =driver.findElements(By.name("session[username_or_email]"));
			MDP =driver.findElements(By.name("session[password]"));

			User.get(0).sendKeys("sherlobot4@gmail.com");
			MDP.get(0).sendKeys("NqlBot400"+ Keys.ENTER);
			System.out.println("Verification a la connexion");
		}catch(Exception e) {
			System.out.println("Pas de verification a la connexion");
		}


	}

	/**
	 * Methode permettant de n'autoriser q'une seule instance de la classe
	 *
	 * @return l'instance autorisee
	 * @throws SQLException the SQL exception
	 */
	public static synchronized TConnexion getTConnection( ){
		if (Tcon == null) {
			Tcon = new TConnexion( );
		}
		return Tcon;
	}


}

