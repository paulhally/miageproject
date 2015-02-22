package Robot;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class LinkExtractor {
	
	
	private String baseUrl;
	
	public LinkExtractor(String url){
		this.baseUrl=url;
	

	}
	
	
	public List<String> RecupLien(WebDriver d){
		System.out.println("Extraction des liens ...");
		List <WebElement> ListLien =  d.findElements(By.xpath("//a[@href]"));
		List <String> TableauLiens = new ArrayList<String>(); 
		String lien = new String();
		
		for(int i = 0; i < ListLien.size(); i++){
			lien = ListLien.get(i).getAttribute("href");
			
			if(lien.startsWith("http")&& !lien.contains("webcache")){
				TableauLiens.add(lien);
			}
		}
		
		//Gestion des doublons
		// Créer une liste de contenu unique basée sur les éléments de ArrayList
	    Set<String> mySet = new HashSet<String>(TableauLiens);
	 
	    // Créer une Nouvelle ArrayList à partir de Set
	    List<String> array_Liens = new ArrayList<String>(mySet);
	    
		for(int j = 0; j< array_Liens.size(); j++){
			System.out.println("Lien "+j+" : "+array_Liens.get(j));
			
		}
		System.out.println("Fin de l'extraction");
		return array_Liens;

		
	}
	
	/*public String PremierLien(List <String> TableauLiens){
		int k = 0;
		for(int j = 0; j< TableauLiens.size(); j++){
			if(TableauLiens.get(j).contains(MotCle)){
				k = j;
				break;
			}
		}
		return TableauLiens.get(k);
		
	}
	public String ChoixDuLien(List <String> TableauLiens, WebDriver d){
		//System.out.println("Lien Choisi : " +PremierLien(TableauLiens));
		return PremierLien(TableauLiens);
	}*/
	
	

}
