package Robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;

import de.linguatools.disco.*;

/**
 * Permet de scorer, trier et choisir les liens web
 * 
 * @see LinkSelector#CompareContenuPageAvecMotCles(WebDriver)
 * @see LinkSelector#LienLePlusPertinent(List, List, int[])
 */
public class LinkSelector {
	DISCO disco;
	private String keyWords[];

	public LinkSelector(String[] kw) {
		String discoDir = "C:\\Users\\Paul\\Desktop\\fr-wikipedia-20110201-lemma";
		this.keyWords = kw;
		try {
			this.disco = new DISCO(discoDir, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Parcours la liste de tout les liens, et parcours le tableaux des mots
	 * cles Verifie si le lien contient des mots cles, et incremente un compteur
	 * en consequence Retourne le lien possédant le compteur le plus eleve, si
	 * plusieurs liens possedent le même compteur, en retourne que trois
	 * 
	 * @param TableauLiens
	 *            La liste de tout les liens de la page web
	 * @see LinkExtractor#RecupLien(WebDriver)
	 * 
	 * @return La liste des trois meilleurs liens
	 */
	@SuppressWarnings("unchecked")
	public List<String> threeBestLinks(List<String> TableauLiens) {

		int counter = 0;
		Object[][] lienRanking = new Object[TableauLiens.size()][2];

		for (int j = 0; j < TableauLiens.size(); j++) { // parcours du tableau
														// de lien
			counter = 0;
			for (int z = 0; z < keyWords.length; z++) { // parcours des mots
														// cles
				if (TableauLiens.get(j).contains(keyWords[z])) {
					counter++;
				}
			}
			lienRanking[j][0] = TableauLiens.get(j);
			lienRanking[j][1] = new Integer(counter);
		}
		List<String> TableauPoidsFort = new ArrayList<String>();
		java.util.Arrays.sort(lienRanking, new AlphaComparator());
		displayArray(lienRanking);

		int PoidsMax = (int) lienRanking[lienRanking.length - 1][1];
		int maxLiensPris = 0;

		if (PoidsMax > 0) {
			for (int j = 0; j < lienRanking.length; j++) {
				if (maxLiensPris < 3) {
					if (lienRanking[j][1].equals(PoidsMax)) {
						TableauPoidsFort.add((String) lienRanking[j][0]);
						maxLiensPris++;
					}
				} else
					break;
			}
		}

		return TableauPoidsFort;
	}

	/**
	 * Permet d'afficher le contenu d'un tableaux a deux dimensions
	 * 
	 * @param array
	 *            Notre tableau contenant la liste de tout les liens avec leurs
	 *            comtpeurs associes
	 */
	public static void displayArray(Object[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Return the 10 best similar words of a key word.
	 * 
	 * @param keyWord
	 * @return
	 */
	@SuppressWarnings("null")
	public List<String> similarWord(String kwO) {

		ReturnDataBN simResult = null;

		List<String> similarWords = new ArrayList<String>();
		boolean error = false;
		try {

			simResult = disco.similarWords(kwO);

			for (int i = 1; i < simResult.words.length; i++) {
				similarWords.add(simResult.words[i]);
				if (i >= 10)
					break; // the most of the time, more is useless.
			}
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			// System.err.println("Pas de synonymes");

		}

		return similarWords;
	}

	public int[] ParcoursListe(List<WebElement> ListeElements, int[] counter) {
		String text = new String();
		for (int i = 0; i < ListeElements.size(); i++) {
			text = ListeElements.get(i).getText();
			text = text.toLowerCase();
			text = text.replaceAll("\\s", "");
			for (int z = 0; z < keyWords.length; z++) {
				if (text.contains(keyWords[z])) {
					counter[0]++;
				}
				List<String> lsW = similarWord(keyWords[z]);
				if (!lsW.isEmpty()) {
					for (int k = 0; k < lsW.size(); k++) {
						if (text.contains(lsW.get(k))) {
							counter[1]++;
						}
					}
				}
			}
		}
		return counter;
	}

	/**
	 * Fonction qui permet d'attribuer un score a un lien en fonction de sa
	 * position dans la page. Plus le lien est haut dans la page, plus il
	 * rapport de points
	 * 
	 * @param troisMeilleursLiens
	 *            Contient nos trois meilleurs liens
	 * @param TableauLiens
	 *            Contient tout nos liens
	 * @return Un tableau d'entier contenant le score du lien par rapport a sa
	 *         position
	 */
	public List<Integer> positionLienDansLaPage(List<String> troisMeilleursLiens,
			List<String> TableauLiens) {

		List<Integer> position = new ArrayList<Integer>();

		for (int i = 0; i < troisMeilleursLiens.size(); i++) {
			for (int j = 0; j < TableauLiens.size(); j++) {
				if (TableauLiens.get(j).equals(troisMeilleursLiens.get(i))) {
					position.add((TableauLiens.size() - 1) - j);
					break;
				}
			}
		}
		System.out.println("taille du classement de la position : "+position.size());
		return position;

	}

	/**
	 * Permet de comparer la page web en cours de navigation avec notre tableau
	 * de mots cles, et de synonymes. Retourne un score du nombre d'apparition
	 * de mots cles et de synonymes
	 * 
	 * @param d
	 * @return Un tableaux d'entier qui contient le nombre d'occurence de mots
	 *         cles et de synonymes dans la page web actuellement navigué
	 */
	public int[] CompareContenuPageAvecMotCles(WebDriver d) {

		List<WebElement> ListParagraphe = d.findElements(By.xpath("//p"));
		List<WebElement> ListTitre1 = d.findElements(By.xpath("//h1"));
		List<WebElement> ListTitre2 = d.findElements(By.xpath("//h2"));
		List<WebElement> ListTitre3 = d.findElements(By.xpath("//h3"));
		List<WebElement> ListLien = d.findElements(By.xpath("//a"));

		int[] counter = new int[2];
		counter = ParcoursListe(ListParagraphe, counter);
		counter = ParcoursListe(ListTitre1, counter);
		counter = ParcoursListe(ListTitre2, counter);
		counter = ParcoursListe(ListTitre3, counter);
		counter = ParcoursListe(ListLien, counter);

		System.out
				.println("The page countains : " + counter[0] + " key words.");
		System.out.println("The page countains : " + counter[1]
				+ " similar words.");
		return counter;
	}

	/**
	 * Permet de trouver le lien le plus pertinent Mots cles = 1 points, +
	 * Synonymes = 1/2 points, + Position = Nombre de liens - ordre dans la page
	 * (expl : si 10 liens dans la page, le 2ème lien vaut 8 points et le
	 * dernier 0)
	 * 
	 * @author Kevin Carli
	 * @param compteurAssocieAuLien
	 *            notre tableaux a deux dimensions contenant nos compteurs de
	 *            mots cles et de synonymes
	 * @param lienSauvegarde
	 *            notre tableaux de tout les liens
	 * @return un String contenant notre lien le plus pertienent, celui qui
	 *         contient le plus d'occurences de mots cles
	 */
	public String LienLePlusPertinent(List<int[]> compteurAssocieAuLien,
			List<String> lienSauvegarde, List<Integer>position) {

		int compteurMax = 0;
		int indice = 0;
		int sommeCompteur = 0;
		System.out.println("Taille psoitititititi : " + position.size());
		System.out.println("Taille compteuassocielien : " + compteurAssocieAuLien.size());
		
		for (int i = 0; i < compteurAssocieAuLien.size(); i++) {
		
			sommeCompteur = compteurAssocieAuLien.get(i)[0]
					+ (compteurAssocieAuLien.get(i)[1] / 2)
					+ position.get(i);
			if (compteurMax < sommeCompteur) {
				compteurMax = sommeCompteur;
				indice = i;
			}

		}
		return lienSauvegarde.get(indice);
	}

	/**
	 * Permet de trier notre tableaux à deux dimensions, qui contient la liste
	 * des liens avec leurs compteurs, dans l'ordre croissant du compteur
	 * 
	 *
	 */
	@SuppressWarnings("rawtypes")
	class AlphaComparator implements java.util.Comparator {
		public int compare(Object o1, Object o2) {
			return ((Integer) ((Object[]) o1)[1])
					.compareTo((Integer) ((Object[]) o2)[1]);
		}
	}
}
