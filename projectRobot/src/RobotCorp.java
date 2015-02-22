

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.classfile.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RobotCorp {

	private WebDriver driver;
	private  long SecondeAAttendre = 10;
	private String baseUrl;
	//private String MotCle[] = { "directory", "harvard", "phonebook" };
	private String MotCle[] = { "nationales", "présentation", "journées", "2015", "concours", "jnm2015.fr" };
	List <Integer> compteurAssocieAuLien = new ArrayList<Integer>();
	List <String> lienSauvegarde = new ArrayList<String>();

	public WebDriver setUp() throws Exception {
		driver = new FirefoxDriver(); 
		return driver;

	}

	public List<String> RecupLien(WebDriver d, String URL) {
		d.get(URL);
		System.out.println("Extraction des liens ...");
		List<WebElement> ListLien = d.findElements(By.xpath("//a[@href]"));
		List<String> TableauLiens = new ArrayList<String>();
		String lien = new String();
		for (int i = 0; i < ListLien.size(); i++) {
			lien = ListLien.get(i).getAttribute("href");
			if (lien.startsWith("http")) {
				TableauLiens.add(lien);
			}
		}
		/*for (int j = 0; j < TableauLiens.size(); j++) {
			System.out.println("Lien " + j + " : " + TableauLiens.get(j));

		}*/
		return TableauLiens;
		//return ChoixDuLien(TableauLiens, d);

	}

	@SuppressWarnings("unchecked")
	public List<String> PremierLien(List <String> TableauLiens){
		int k = 0;
		int compteur = 0;
		Object[][] lienRanking = new Object[TableauLiens.size()][2];
       
   
		for(int j = 0; j< TableauLiens.size(); j++){ // parcours du tableau de lien
			compteur = 0;
			for(int z = 0; z < MotCle.length; z++){ // parcours des mots cles			
				if(TableauLiens.get(j).contains(MotCle[z])){
					compteur++;
				}
			}
			lienRanking[j][0] = TableauLiens.get(j);
			lienRanking[j][1] = new Integer(compteur); 
		}
	
		//On prend les 3 liens ayant eu le plus de mots clés 
		List<String> TableauPoidsFort = new ArrayList<String>();
		java.util.Arrays.sort(lienRanking, new AlphaComparator());
		displayTableau(lienRanking);
		int PoidsMax = (int) lienRanking[lienRanking.length -1][1] ;
		int maxLiensPris= 0;
		if(PoidsMax > 0){
			for(int j = 0; j< lienRanking.length; j++){
				if(maxLiensPris <= 3){
					if(lienRanking[j][1].equals(PoidsMax)){
						TableauPoidsFort.add((String) lienRanking[j][0]);
						maxLiensPris++;
						//System.out.println("Tableau poids fort : " +lienRanking[j][0] );
					}
				}else
					break;		
			}
		}
		
		
		//System.out.println("LE poids max : "+PoidsMax);
		
		return TableauPoidsFort;
		
	}

	@SuppressWarnings("rawtypes")
	class AlphaComparator implements java.util.Comparator {
		   public int compare(Object o1, Object o2) {
		      return ((Integer) ((Object[]) o1)[1]).compareTo((Integer) ((Object[]) o2)[1]);
		   }
		}
	public static void displayTableau(Object[][] tab) {
	      for (int i = 0; i < tab.length; i++) {
	         for (int j = 0; j < tab[i].length; j++) {
	            System.out.print(tab[i][j]);
	            System.out.print(' ');
	         }
	         System.out.println(); 
	      }
	      System.out.println();     
	   }


	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List <String> SupprimerDoublon(List<String> aSupprimer){
	    Set set = new HashSet() ;
	    set.addAll(aSupprimer) ;
	    List<String> LiensSansDoublons = new ArrayList<String>(set);
		return LiensSansDoublons;
		
	}
public String LienLePlusPertinent(){
		
		int compteurMax = 0;
		int indice = 0;
		for(int i=0; i<compteurAssocieAuLien.size(); i++){
			if(compteurMax < compteurAssocieAuLien.get(i)){
				compteurMax = compteurAssocieAuLien.get(i);
				indice = i;
			}
			
		}
		return lienSauvegarde.get(indice);
		
	}
	public int CompareContenuPageAvecMotCles(WebDriver d){	
		
		List<WebElement> ListParagraphe = d.findElements(By.xpath("//p"));
		List<WebElement> ListTitre1 = d.findElements(By.xpath("//h1"));
		List<WebElement> ListTitre2 = d.findElements(By.xpath("//h2"));
		List<WebElement> ListTitre3 = d.findElements(By.xpath("//h3"));
		List<WebElement> ListLien = d.findElements(By.xpath("//a"));
		String text = new String();
		int compteurMotCles = 0;
		for (int i = 0; i < ListParagraphe.size(); i++) {
			text = ListParagraphe.get(i).getText();
			text=text.toLowerCase();
			System.out.println("contenu paragraphe : " + text);
			for(int z = 0; z < MotCle.length; z++){ // parcours des mots cles			
				if(text.contains(MotCle[z])){
					compteurMotCles++;
				}
			}
		}
		for (int i = 0; i < ListTitre1.size(); i++) {
			text = ListTitre1.get(i).getText();
			text=text.toLowerCase();
			System.out.println("contenu titre 1" + text);
			for(int z = 0; z < MotCle.length; z++){ // parcours des mots cles		
				
				if(text.contains(MotCle[z])){
					compteurMotCles++;
				}
			}
		}
		
		for (int i = 0; i < ListTitre2.size(); i++) {
			text = ListTitre2.get(i).getText();
			text=text.toLowerCase();
			System.out.println("contenu titre 2" + text);
			for(int z = 0; z < MotCle.length; z++){ // parcours des mots cles		
				
				if(text.contains(MotCle[z])){
					compteurMotCles++;
				}
			}
		}
		for (int i = 0; i < ListTitre3.size(); i++) {
			text = ListTitre3.get(i).getText();
			text=text.toLowerCase();
			System.out.println("contenu titre3 : " + text);
			for(int z = 0; z < MotCle.length; z++){ // parcours des mots cles			
				if(text.contains(MotCle[z])){
					compteurMotCles++;
				}
			}
		}
		for (int i = 0; i < ListLien.size(); i++) {
			text = ListLien.get(i).getText();
			text=text.toLowerCase();
			System.out.println("contenu lien : " + text);
			for(int z = 0; z < MotCle.length; z++){ // parcours des mots cles			
				if(text.contains(MotCle[z])){
					compteurMotCles++;
				}
			}
		}
		System.out.println("La page contient : " +compteurMotCles+ " occurences de mots cles");
		return compteurMotCles;
	}
	
	// *********************************Navigation***********************************************
	public void navigationSetUp(List<String> LiensChoisi, WebDriver d){
		if(LiensChoisi.size() != 0){
			System.out.println("Lien qui va etre acceder : " +LiensChoisi.get(LiensChoisi.size() - 1));
			navigation(LiensChoisi.get(LiensChoisi.size() - 1), d);
			System.out.println(" Le lien " +LiensChoisi.get(LiensChoisi.size() - 1)+" va etre supprimer");
			LiensChoisi.remove(LiensChoisi.size() - 1);
			navigationSetUp(LiensChoisi, d);
		}
	}
	
	public void navigation(String lienChoisi, WebDriver d) {
		lienSauvegarde.add(lienChoisi);
		d.get(lienChoisi);
		
		//d.manage().timeouts().implicitlyWait(SecondeAAttendre, TimeUnit.SECONDS); 
		compteurAssocieAuLien.add(CompareContenuPageAvecMotCles(d));
	}
	
	
	
	//*********************************************************************************************
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WebDriver d;
		String Lien = new String();
		String LienPertinent = new String();
		//Lien = "http://www.directory.harvard.edu";
		Lien = "https://www.google.fr/search?q=jnm2015&ie=utf-8&oe=utf-8&gws_rd=cr&ei=UrjpVJKZOMe2UfTPgpAI#q=jnm2015";
		List<String> LiensChoisi = new ArrayList<String>();
		RobotCorp Robot = new RobotCorp();
		d = Robot.setUp();
		
		//for(int w = 0; w < 3; w++){
			LiensChoisi = Robot.RecupLien(d,Lien);
			LiensChoisi = Robot.PremierLien(LiensChoisi);
			if(LiensChoisi.isEmpty()){
				
			}
			else{
				LiensChoisi = Robot.SupprimerDoublon(LiensChoisi);
				for(int k = 0; k<LiensChoisi.size(); k++ )
					System.out.println("Lien choisi : " + LiensChoisi.get(k));
				
				Robot.navigationSetUp(LiensChoisi, d);
				LienPertinent = Robot.LienLePlusPertinent();
				System.out.println("Le lien le plus pertinent est : "+LienPertinent);
			}
			
		//}
		
		
		// A partir du lien le plus pertinant rappeler RecupLien() etc boucler 3 fois
		System.out.println(".......Navigation finie ......");

	}

}
