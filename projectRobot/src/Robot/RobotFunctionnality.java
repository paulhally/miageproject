package Robot;

import ihm.DisplayEndWindow;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Map;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * La partie navigation du robot, qui va nous permettre de naviguer de pages en
 * pages
 * 
 *
 */
public class RobotFunctionnality {

	static String currentUrl;
	String[] keyWords;
	static Integer time;
	Integer counter;
	private static WebDriver driver;
	private TimeSpent timeS;
	
	
	static List<int[]> compteurAssocieAuLien = new ArrayList<int[]>();
	static List<String> lienSauvegarde = new ArrayList<String>();
	static List<Integer> position = new ArrayList<Integer>();
	static ArrayList<String> allLinks = new ArrayList<String>();
	/**
	 * Permet de supprimer des liens dit "ancres", car impertinent
	 * 
	 * @param tmp
	 *            Notre tableau de liens temporaires
	 * @param Model
	 *            Un String contenant l'url de la page en cours de navigation
	 * @return Un tableau de liens sans liens ancres
	 */
	public static List<String> deleteAnchor(List<String> tmp, String Model) {
		// String Model = cu;
		List<String> afterRemoval = new ArrayList<String>();
		for (int i = 0; i < tmp.size(); i++) {
			if (!(tmp.get(i).startsWith(Model)))
				afterRemoval.add(tmp.get(i));
		}
		return afterRemoval;
	}

	// --------------------------------- NAVIGATION
	// -----------------------------------------------

	/**
	 * Permet d'acceder à un lien web
	 * 
	 * @param adresseUrl
	 *            Notre premeir lien web qui va etre accéder, a partir duquel on
	 *            va commencer la recherche (expl:google.fr)
	 * @throws Exception
	 */
	public static void setUp(String adresseUrl) throws Exception {
		currentUrl = adresseUrl;
		if (correctNavigationLink(adresseUrl)) {
			currentUrl = adresseUrl;
			driver.get(currentUrl);
		} else {
			close(driver);
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "URL ADRESS Not Found", "Information",JOptionPane.INFORMATION_MESSAGE);
			Thread.currentThread().stop();

		}
	}

	/**
	 * Permet d'accéder à nos trois meilleures liens un par un
	 * 
	 * @see RobotFunctionnality#navigation(String, LinkSelector, Thread)
	 * @param LiensChoisi
	 *            Tableaux contenant nos trois meilleures liens
	 * @param ls
	 *            notre objet LinkSelector
	 * @param t
	 *            une thread qu'on mettra en pause, pour espacer le temps de
	 *            navigation entre chaque pages
	 */
	public static void navigationSetUp(List<String> LiensChoisi,
			LinkSelector ls, Thread t) {
		if (LiensChoisi.size() != 0) {
			System.out.println("Lien qui va etre acceder : "
					+ LiensChoisi.get(LiensChoisi.size() - 1));
			if (correctNavigationLink(LiensChoisi.get(LiensChoisi.size() - 1))) {
				navigation(LiensChoisi.get(LiensChoisi.size() - 1), ls, t);
				System.out.println(" Le lien "
						+ LiensChoisi.get(LiensChoisi.size() - 1)
						+ " va etre supprimer");
				LiensChoisi.remove(LiensChoisi.size() - 1);
				navigationSetUp(LiensChoisi, ls, t);
			} else {
				back();
				JOptionPane jop1 = new JOptionPane();
				jop1.showMessageDialog(null,
						"URL ADRESS Not Found, back to the previous page.",
						"Information", JOptionPane.INFORMATION_MESSAGE);

			}
		}
	}

	/**
	 * Permet d'acceder à nos trois meilleures liens un par un
	 * 
	 * @see RobotFunctionnality#navigationSetUp(List, LinkSelector, Thread)
	 * @param lienChoisi
	 * @param ls
	 * @param t
	 */
	public static void navigation(String lienChoisi, LinkSelector ls, Thread t) {
		lienSauvegarde.add(lienChoisi);
		allLinks.add(lienChoisi);
		driver.get(lienChoisi);

		try {
			System.out.println("Time Spent in a page " + time + " sec");
			t.sleep(time * 1000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Lien qui va etre ajouter au compteur lien" + lienChoisi);
		compteurAssocieAuLien.add(ls.CompareContenuPageAvecMotCles(driver));

	}

	/**
	 * Constructeur du Robot
	 * 
	 * @param url
	 *            Lien web
	 * @param kw
	 *            Tableaux de nos mots cles
	 * @param t
	 *            Temps de navigation
	 * 
	 * @see RobotFunctionnality#currentUrl
	 * @see RobotFunctionnality#keyWords
	 * @see RobotFunctionnality#time
	 * @see RobotFunctionnality#counter
	 */
	public RobotFunctionnality(String url, String[] kw, Integer t) {

		this.currentUrl = url;
		this.keyWords = kw;
		this.time = t;
		this.counter = 1;
		driver = new FirefoxDriver();
		this.timeS=new TimeSpent();
		timeS.start();
	}

	@SuppressWarnings("static-access")
	/**
	 * Method which launch the robot
	 * @param t
	 * @throws Exception
	 */
	public void go(Thread t) throws Exception {
		int z = 0;
		if (z == 0) {
			this.allLinks.add(currentUrl);
			RobotFunctionnality.setUp(currentUrl);
			z++;
		}

		LinkExtractor le = new LinkExtractor(currentUrl);
		List<String> linkList = le.RecupLien(driver);
		LinkSelector ls = new LinkSelector(keyWords);
		// Select three Best Links Max
		List<String> tBLinks = ls.threeBestLinks(linkList);
		position = ls.positionLienDansLaPage(tBLinks, linkList);

		for (int u = 0; u < tBLinks.size(); u++) {
			System.out.println("Trois meilleurs liens : " + tBLinks.get(u));
		}
		//tBLinks = RobotFunctionnality.deleteAnchor(tBLinks, currentUrl);
		
		if (tBLinks.isEmpty()) {
			System.out.println("Aucun lien n'est meilleur");
			counter=1;
		} else {
			RobotFunctionnality.navigationSetUp(tBLinks, ls, t);
			currentUrl = ls.LienLePlusPertinent(this.compteurAssocieAuLien,this.lienSauvegarde, this.position);
			System.out.println("Le lien le plus pertinent est : " + currentUrl);
			RobotFunctionnality.setUp(currentUrl);
		}

		counter--;
		if (counter != 0) {
			this.lienSauvegarde.clear();
			this.compteurAssocieAuLien.clear();
			go(t);
		} else {
			this.allLinks.add(currentUrl);
			DisplayEndWindow endDisplay=new DisplayEndWindow(allLinks,timeS.getTime());
			timeS.arret();
			System.out.println("Surf terminé");
		}

	}

	/**
	 * + * Test the connection of a page + * @param url + * @return +
	 */
	public static boolean correctNavigationLink(String url) {
		try {
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			Map<String, List<String>> map = conn.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			}
			String server = conn.getHeaderField("Server");
			if (server == null) 
				return false;
			 else 
				return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public static void back() {
		driver.navigate().back();
	}

	/**
	 * @author Kevin Carli Ferme la navigation
	 * @param d
	 */
	public static void close(WebDriver d) {
		d.close();
	}
}
