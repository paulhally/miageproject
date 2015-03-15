package Robot;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import de.linguatools.disco.*;

public class LinkSelector {
    DISCO disco;
    protected String keyWords[];
    protected List<String> linkList;
    private List<WebElement> ListParagraphe;
    private List<WebElement> ListTitre1;
    private List<WebElement> ListTitre2;
    private List<WebElement> ListTitre3;
    private List<WebElement> ListLien;


    public LinkSelector(){}
    public LinkSelector(String[]kw, List<String> linkList){

        /*modifié*/
        this.keyWords=kw;
        //System.out.println("test01 " + this.keyWords[0]);
        this.linkList = linkList;
    }

    @SuppressWarnings("unchecked")
    /**
     * Count the frequency of key words in every links and take the 3 best Links of a page.
     *
     * @param TableauLiens
     * @return
     */
    public List<String> threeBestLinks(List <String> TableauLiens){
        /*:))*/
        CheckUrlByContains c_urlContains = new CheckUrlByContains(this.keyWords);
        Object[][]lienRanking = c_urlContains.checkUrlContains(TableauLiens);
        List<String> TableauPoidsFort = new ArrayList();


        /*Le reste reste dans cette méthode car cette méthode est commune, à tous les types de selection de liens*/
            //On prend les 3 liens ayant eu le plus de mots clés
        if(lienRanking.length > 0) {
            java.util.Arrays.sort(lienRanking, new AlphaComparator());
            displayArray(lienRanking);
            int PoidsMax = (int) lienRanking[lienRanking.length - 1][1];
            System.out.println("affichage de Poids max " + PoidsMax);

            int maxLiensPris = 0;
            if (PoidsMax > 0) {
                for (int j = 0; j < lienRanking.length; j++) {
                    if (maxLiensPris < 3) {
                        if (lienRanking[j][1].equals(PoidsMax)) {
                            TableauPoidsFort.add((String) lienRanking[j][0]);
                            maxLiensPris++;
                            System.out.println("Tableau poids fort : " + lienRanking[j][0]);
                        }
                    } else break;
                }
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

/*similar words effacé d'ici*/
    public int[] CompareContenuPageAvecMotCles(WebDriver d){
        LinkExtractor ls = new LinkExtractor();
        ListParagraphe = ls.getListParagraphe(d);
        ListTitre1 = ls.getListTitre1(d);
        ListTitre2 = ls.getListTitre2(d);
        ListTitre3 = ls.getListTitre3(d);
        ListLien = ls.getListLien(d);
        CheckUrlByDisco c_urlDisco = new CheckUrlByDisco(keyWords);
        int[] counter = new int[2]; // counter[0] for counter of keyWords and counter[1] for similarWords
        try {
            counter(counter, ListParagraphe, c_urlDisco);
            counter(counter, ListTitre1, c_urlDisco);
            counter(counter, ListTitre2, c_urlDisco);
            counter(counter, ListTitre3, c_urlDisco);
            counter(counter, ListLien, c_urlDisco);
            System.out.println("text affichage test erreur ?");
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            //System.out.println("erreur dans le code... " + e.getMessage());
        }

        System.out.println("le counter de ListParagraphe renvoie: ");
        System.out.println("The page countains : " +counter[0]+ " key words.");
        System.out.println("The page countains : " +counter[1]+ " similar words.");
        return counter;
    }

    private void counter(int[]counter, List<WebElement> list, CheckUrlByDisco c_urlDisco) {
        String text;
        int keyWordsCount = 0;
        int similarWordsCount = 1;
        try {
            for (int i = 0; i < list.size(); i++) {
                text = list.get(i).getText();
                text = text.toLowerCase();
                text = text.replaceAll("\\s", "");
                if (!text.equals("")){
                    for (int j = 0; j < keyWords.length; j++) { // parcours des mots cles
                        if (text.contains(keyWords[j])) {
                            counter[keyWordsCount]++;
                        }
                        List<String> lsW = c_urlDisco.similarWord(keyWords[j]);
                        if (!lsW.isEmpty()) {
                            for (int k = 0; k < lsW.size(); k++) {
                                if (text.contains(lsW.get(k))) {
                                    counter[similarWordsCount]++;
                                }
                            }
                        }
                    }
                }
                /*Affichage de counter*/

            }
        } catch (StaleElementReferenceException e) {
            System.out.println(e.getMessage());
        }
    }

    public String LienLePlusPertinent(List<int[]>compteurAssocieAuLien,List<String>lienSauvegarde){

        double compteurMax = 0;
        int indice = 0;
        int scoreLink=0;
        for(int i=0; i<compteurAssocieAuLien.size(); i++){
        	
        	scoreLink=compteurAssocieAuLien.get(i)[0]*2;
        	scoreLink=scoreLink+compteurAssocieAuLien.get(i)[1];
        	
            if(compteurMax < scoreLink){
                compteurMax = scoreLink;
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
            return (((Integer) ((Object[]) o1)[1]).compareTo((Integer) ((Object[]) o2)[1]));
        }
    }

    public static void main(String[] args) {
        //LinkSelector ls=new LinkSelector();
        //ls.similarWord();
    }

    public String[] getKeyWords() {
        return keyWords;
    }
}
