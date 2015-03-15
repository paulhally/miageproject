package Robot;

/**
 * Created by Master on 08/03/2015.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import de.linguatools.disco.*;

public class CheckUrlByDisco extends LinkSelector {

    private DISCO disco;
    private String[] kw;
    private String discoDirFr = "C:\\Users\\Paul\\Desktop\\fr-wikipedia-20110201-lemma";
   // private String discoDirEn ="C:\\Users\\Master\\workspace\\MiageProjectRobotSelenium\\libs\\en-PubMedOA-20070501";

    public CheckUrlByDisco(String[] kw){
        String[] kwWithoutSpecialCharacters = new String[(kw.length * 2)];
        try {
            this.disco = new DISCO(discoDirFr, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.kw = super.keyWords;
        //d�coupage des key words composés: le-chien => bug, le chien => ca passe
        //pour chaque mot clé, on renvoie un tableau ==> tableau en 2d


    }
    
    public List<String> similarWord(String kwO){
    	
        String[] splitTable;
        int indice=0;
        ReturnDataBN simResult=null;
        kwO = normaliserKw(kwO);
        if(kwO.contains(" ")){
            splitTable = kwO.split(" ");
            int max = splitTable[0].length();
            for (int i=1; i < splitTable.length ; i++) {
                if (max < splitTable[i].length()) {
                    max = splitTable[i].length();
                    indice = i;
                }
            }
            kwO = splitTable[indice];
        }

        List<String>yolo = new ArrayList<String>();
        boolean error=false;
        try {
                simResult=disco.similarWords(kwO);
                for(int i = 1; i < simResult.words.length && i < 10; i++){
                    yolo.add(simResult.words[i]);
            }
        } catch (NullPointerException | IOException e) {
           /* try{
                //System.out.println("passage à la bu anglaise");
               // this.disco = new DISCO(discoDirEn, false);
               // simResult=disco.similarWords(kwO);
               // for(int i = 1; i < simResult.words.length && i < 10; i++){
                //    yolo.add(simResult.words[i]);
            }
            }catch(Exception e1){
                //System.out.println("echec apres chargement bu anglaise");
            }*/
            //System.out.println(e.getClass() + " " + e.getMessage());

        }
        return yolo;
    }
    
    /**
     * Permit to normalize key words.
     * @param kw
     * @return
     */
    public static String normaliserKw(String kw){

            //System.out.println("affichage du précédent kw: " + kw);
        //System.out.println("\n");
            kw = kw.toLowerCase();
            kw = kw.replaceAll("[^a-z]", " ");
            //System.out.println("affichage du nouveau kw: " + kw);
        //System.out.println("\n");
            return kw;
    }
}

/*complication =====> si on a un mot clé, après normalisation, faut garder un seul
* mot clé.
* Pourquoi ?
* ex: le chat donnerait 2 mots clés MAIS si on créer 2 mots clés le et "chat" il faudrait modifier
* le mot clé dans la classe supérieure LinkSelector...
* !! il suffit de normaliser dans la méthode similarWords seulement et non dans le
* constructeur !!
*
* */
