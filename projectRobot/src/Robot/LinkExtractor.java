package Robot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Permet d'extraire tout les liens d'une page web
 * 
 * @see LinkExtractor#RecupLien(WebDriver)
 */
public class LinkExtractor {
	private String baseUrl;

	/**
	 * 
	 * @param url
	 *            Premier lien qui va etre acceder
	 * @see LinkExtractor#baseUrl
	 */
	public LinkExtractor(String url) {
		this.baseUrl = url;
	}

	/**
	 * Recupere l'ensemble des liens de la page web en cours de navigation
	 * 
	 * @param d
	 *            notre fenêtre de navigation
	 * @return Une liste de String contenant l'ensemble des liens de la page web
	 *         navigué
	 */
	public List<String> RecupLien(WebDriver d) {
		System.out.println("Extraction des liens ...");
		List<WebElement> ListLien = d.findElements(By.xpath("//a[@href]"));
		List<String> TableauLiens = new ArrayList<String>();
		String lien = new String();

		for (int i = 0; i < ListLien.size(); i++) {
			lien = ListLien.get(i).getAttribute("href");
			if (lien.startsWith("http") && !lien.contains("webcache")
					&& !lien.contains("google")) {
				TableauLiens.add(lien);
			}
		}

		// Gestion des doublons
		Set<String> mySet = new HashSet<String>(TableauLiens);
		List<String> array_Liens = new ArrayList<String>(mySet);

		for (int j = 0; j < array_Liens.size(); j++) {
			System.out.println("Lien " + j + " : " + array_Liens.get(j));

		}
		System.out.println("Fin de l'extraction");
		return array_Liens;
	}
}
