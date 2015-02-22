package Robot;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;

import de.linguatools.disco.*;

public class LinkSelector {
	DISCO disco;
	private String keyWords[];
	
	
	public LinkSelector(String[]kw){
		String discoDir="D:\\dico";
		this.keyWords=kw;		
		try {
			this.disco = new DISCO(discoDir, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Count the frequency of key words in every links and take the 3 best Links of a page.
	 * 
	 * @param TableauLiens
	 * @return
	 */
	public List<String> threeBestLinks(List <String> TableauLiens){

		int counter = 0;
		Object[][] lienRanking = new Object[TableauLiens.size()][2];
       
   
		for(int j = 0; j< TableauLiens.size(); j++){ // parcours du tableau de lien
			counter = 0;
			for(int z = 0; z < keyWords.length; z++){ // parcours des mots cles			
				if(TableauLiens.get(j).contains(keyWords[z])){
					counter++;
				}
			}
			lienRanking[j][0] = TableauLiens.get(j);
			lienRanking[j][1] = new Integer(counter); 
		}
	
		//On prend les 3 liens ayant eu le plus de mots clés 
		List<String> TableauPoidsFort = new ArrayList<String>();
		
		java.util.Arrays.sort(lienRanking, new AlphaComparator());
		displayArray(lienRanking);
		
		int PoidsMax = (int) lienRanking[lienRanking.length -1][1] ;
		int maxLiensPris= 0;
		
		if(PoidsMax > 0){
			for(int j = 0; j< lienRanking.length; j++){
				if(maxLiensPris < 3){
					if(lienRanking[j][1].equals(PoidsMax)){
						TableauPoidsFort.add((String) lienRanking[j][0]);
						maxLiensPris++;
						//System.out.println("Tableau poids fort : " +lienRanking[j][0] );
					}
				}
				else break;
			}
		}
			
		return TableauPoidsFort;	
	}
	
	/**
	 * Function which permit to display an array[][]	
	 * @param array
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
	
	
	@SuppressWarnings("null")
	/**
	 * Return the 10 best similar words of a key word.
	 * @param keyWord
	 * @return
	 */
	public List<String> similarWord(String kwO){
		
		ReturnDataBN simResult=null;
		
		List<String>yolo =new ArrayList<String>();
		boolean error=false;
		try {
			
			simResult=disco.similarWords(kwO);
		
			for(int i = 1; i < simResult.words.length; i++){
	            yolo.add(simResult.words[i]);
				if( i >=10 ) break; // the most of time, more is useless.
	        }
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			//System.err.println("Pas de synonymes");
		
		}
		
		return yolo;
	}
	
public int[] CompareContenuPageAvecMotCles(WebDriver d){	
		
		List<WebElement> ListParagraphe = d.findElements(By.xpath("//p"));
		List<WebElement> ListTitre1 = d.findElements(By.xpath("//h1"));
		List<WebElement> ListTitre2 = d.findElements(By.xpath("//h2"));
		List<WebElement> ListTitre3 = d.findElements(By.xpath("//h3"));
		List<WebElement> ListLien = d.findElements(By.xpath("//a"));
		String text = new String();
		int [] counter=new int[2]; // counter[0] for counter of keyWords and counter[1] for similarWords
	
		for (int i = 0; i < ListParagraphe.size(); i++) {
			text = ListParagraphe.get(i).getText();
			text=text.toLowerCase();
			text=text.replaceAll("\\s", "");
			//System.out.println("contenu paragraphe : " + text);
			for(int z = 0; z < keyWords.length; z++){ // parcours des mots cles			
				if(text.contains(keyWords[z])){
					counter[0]++;
				}
				List<String>lsW =similarWord(keyWords[z]);
				if(!lsW.isEmpty()){
					for(int k=0;k<lsW.size();k++){
						if(text.contains(lsW.get(k))){
							counter[1]++;
						}
					}
				}
			}
		}
		for (int i = 0; i < ListTitre1.size(); i++) {
			text = ListTitre1.get(i).getText();
			text=text.toLowerCase();
			text=text.replaceAll("\\s", "");
			//System.out.println("contenu titre 1" + text);
			for(int z = 0; z < keyWords.length; z++){ // parcours des mots cles			
				if(text.contains(keyWords[z])){
					counter[0]++;
				}
				List<String>lsW =similarWord(keyWords[z]);
				if(!lsW.isEmpty()){
					for(int k=0;k<lsW.size();k++){
						if(text.contains(lsW.get(k))){
							counter[1]++;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < ListTitre2.size(); i++) {
			text = ListTitre2.get(i).getText();
			text=text.toLowerCase();
			text=text.replaceAll("\\s", "");
			//System.out.println("contenu titre 2" + text);
			for(int z = 0; z < keyWords.length; z++){ // parcours des mots cles			
				if(text.contains(keyWords[z])){
					counter[0]++;
				}
				List<String>lsW =similarWord(keyWords[z]);
				if(!lsW.isEmpty()){
					for(int k=0;k<lsW.size();k++){
						if(text.contains(lsW.get(k))){
							counter[1]++;
						}
					}
				}
			}
		}
		for (int i = 0; i < ListTitre3.size(); i++) {
			text = ListTitre3.get(i).getText();
			text=text.toLowerCase();
			text=text.replaceAll("\\s", "");
			//System.out.println("contenu titre3 : " + text);
			for(int z = 0; z < keyWords.length; z++){ // parcours des mots cles			
				if(text.contains(keyWords[z])){
					counter[0]++;
				}
				List<String>lsW =similarWord(keyWords[z]);
				if(!lsW.isEmpty()){
					for(int k=0;k<lsW.size();k++){
						if(text.contains(lsW.get(k))){
							counter[1]++;
						}
					}
				}
			}
		}
		for (int i = 0; i < ListLien.size(); i++) {
			text = ListLien.get(i).getText();
			text=text.toLowerCase();
			text=text.replaceAll("\\s", "");
			//System.out.println("contenu lien : " + text);
			for(int z = 0; z < keyWords.length; z++){ // parcours des mots cles			
				if(text.contains(keyWords[z])){
					counter[0]++;
				}
				List<String>lsW =similarWord(keyWords[z]);
				if(!lsW.isEmpty()){
					for(int k=0;k<lsW.size();k++){
						if(text.contains(lsW.get(k))){
							counter[1]++;
						}
					}
				}
			}
		}
		System.out.println("The page countains : " +counter[0]+ " key words.");
		System.out.println("The page countains : " +counter[1]+ " similar words.");
		return counter;
	}

	public String LienLePlusPertinent(List<int[]>compteurAssocieAuLien,List<String>lienSauvegarde){
	
		double compteurMax = 0;
		int indice = 0;
		
		for(int i=0; i<compteurAssocieAuLien.size(); i++){
			if(compteurMax < compteurAssocieAuLien.get(i)[0]){
				compteurMax = compteurAssocieAuLien.get(i)[0];
				indice = i;
			}
			
		}
		return lienSauvegarde.get(indice);
	}

	@SuppressWarnings("rawtypes")
	/**
	 * 
	 * Class which permit to sort different elements in alphabetic order.
	 *
	 */
	class AlphaComparator implements java.util.Comparator {
		   public int compare(Object o1, Object o2) {
		      return ((Integer) ((Object[]) o1)[1]).compareTo((Integer) ((Object[]) o2)[1]);
		   }
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LinkSelector ls=new LinkSelector();
		//ls.similarWord();

	}
}
