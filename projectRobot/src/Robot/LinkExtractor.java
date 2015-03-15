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
    private List<WebElement> ListParagraphe;
    private List<WebElement> ListTitre1;
    private List<WebElement> ListTitre2;
    private List<WebElement> ListTitre3;
    private List<WebElement> ListLien;


    public LinkExtractor(){}
    public LinkExtractor(String url, WebDriver e){
        this.baseUrl=url;
        /*on appelle ici les méthodes toutes les méthodes de la classe car on veut extraire le contenu de toutes les pages
        * c une règle toujours vraie !*/
        // cette méthode donne les liens de la page
     }

    public List<String> RecupLien(WebDriver d){
        System.out.println("Extraction des liens ...");
        List <WebElement> ListLien =  d.findElements(By.xpath("//a[@href]"));
        List <String> TableauLiens = new ArrayList<String>();
        String lien;
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
        for(int j = 0; j < array_Liens.size(); j++){
            System.out.println("Lien "+j+" : "+array_Liens.get(j));
        }
        System.out.println("Fin de l'extraction");
        return array_Liens;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<WebElement> getListParagraphe(WebDriver d) {
        ListParagraphe = d.findElements(By.xpath("//p"));
        return ListParagraphe;
    }

    public void setListParagraphe(List<WebElement> listParagraphe) {
        ListParagraphe = listParagraphe;
    }

    public List<WebElement> getListTitre1(WebDriver d) {
        ListTitre1 = d.findElements(By.xpath("//h1"));
        return ListTitre1;
    }

    public List<WebElement> getListTitre2(WebDriver d) {
        ListTitre2 = d.findElements(By.xpath("//h2"));
        return ListTitre2;
    }


    public List<WebElement> getListTitre3(WebDriver d) {
        ListTitre3 = d.findElements(By.xpath("//h3"));
        return ListTitre3;
    }


    public List<WebElement> getListLien(WebDriver d) {
        ListLien = d.findElements(By.xpath("//a"));
        return ListLien;
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
