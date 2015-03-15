package Robot;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import ihm.DisplayEndWindow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class RobotFunctionnality {

    static String currentUrl;
    String[] keyWords;
    static Integer time;
    Integer counter;
    private static WebDriver driver;
    boolean error=false;
    static List <int[]> compteurAssocieAuLien = new ArrayList<int[]>();
    static List <String> lienSauvegarde = new ArrayList<String>();
    private String urlInitiale = "";
    private String urlFinale = "";
    private boolean  tour1 = true;
    private long startTime = 0;
    private long finalTime = 0;




    //--------------------------------- NAVIGATION -----------------------------------------------

    public static void back(){
        driver.navigate().back();
    }

    public static void close(){
        driver.close();
    }

    public static void setUp(String adresseUrl) throws Exception {

        if(correctNavigationLink(adresseUrl)){
            currentUrl = adresseUrl;
            driver.get(currentUrl);
        }
        else{
            RobotFunctionnality.close();
            JOptionPane jop1 = new JOptionPane();
            jop1.showMessageDialog(null, "URL de l'adresse introuvable", "Information", JOptionPane.INFORMATION_MESSAGE);
            Thread.currentThread().interrupt();
        }
    }

    public static void navigationSetUp(List<String> LiensChoisi, LinkSelector ls,Thread t){
        if(LiensChoisi.size() != 0){
            System.out.println("Lien qui va etre acceder : " +LiensChoisi.get(LiensChoisi.size() - 1));
            if(correctNavigationLink(LiensChoisi.get(LiensChoisi.size() - 1))){
                navigation(LiensChoisi.get(LiensChoisi.size() - 1), ls,t);
                System.out.println(" Le lien " +LiensChoisi.get(LiensChoisi.size() - 1)+" va etre supprimer");
                LiensChoisi.remove(LiensChoisi.size() - 1);
                navigationSetUp(LiensChoisi, ls,t);
            }
            else{
                back();
                JOptionPane jop1 = new JOptionPane();
                jop1.showMessageDialog(null, "URL de l'adresse introuvable, retour sur la page précedente.", "Information", JOptionPane.INFORMATION_MESSAGE);

            }
        }
    }

    public static void navigation(String lienChoisi, LinkSelector ls,Thread t) {
        lienSauvegarde.add(lienChoisi);
        driver.get(lienChoisi);
        try {
            System.out.println("Temps passé sur la page " + time + " sec");
            t.sleep(time*1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Lien qui va etre acceder");
        }
        //bug ligne suivante
        compteurAssocieAuLien.add(ls.CompareContenuPageAvecMotCles(driver));
    }

    public RobotFunctionnality(String url,String []kw,Integer t){
        this.currentUrl=url;
        this.keyWords=kw;
        this.time=t;
        this.counter=2;
        driver = new FirefoxDriver();
    }

    @SuppressWarnings("static-access")
    public void go(Thread t) throws Exception{
       // startTime = System.currentTimeMillis();
        //System.out.println("tour1 vaut:==> " + tour1);
        if(tour1){
            urlInitiale = currentUrl;
            tour1 = false;
        }
        
        System.out.println("**************************************");
        System.out.println("valeur de l'url courante " + currentUrl);
        System.out.println("**************************************");

        /*int z=0;
        if(z==0){*/
        RobotFunctionnality.setUp(currentUrl);
        LinkExtractor le=new LinkExtractor(currentUrl, driver);
        List<String>linkList=le.RecupLien(driver);
        LinkSelector ls=new LinkSelector(keyWords,linkList);
        //Select three Best Links Max
        List<String>tBLinks=ls.threeBestLinks(linkList);

        for(int i=0;i<tBLinks.size();i++){
            System.out.println("Les trois meilleurs liens : " + tBLinks.get(i));
        }
        if(tBLinks.isEmpty()){
            back();
        }
        else{
            RobotFunctionnality.navigationSetUp(tBLinks,ls,t);
            String bestUrl=ls.LienLePlusPertinent(this.compteurAssocieAuLien,this.lienSauvegarde);
            if(currentUrl!=bestUrl){
	            currentUrl = bestUrl;
	            System.out.println("Le lien le plus pertinent est : "+ currentUrl);
	            RobotFunctionnality.setUp(currentUrl);
            }
            else{
            	System.out.println("Le lien le plus pertinent n'a pas changé. ");
            	
            	counter=1;
            }
        }
        counter--;
        if(counter!=0){
            go(t);
        }
        else{
            System.out.println("Surf terminé");
            urlFinale = currentUrl;
            finalTime = System.currentTimeMillis();
            long executionTime = (finalTime - startTime)/1000;
            /*System.out.println("*********Résultats********");
            System.out.println("l'éxecution a duré:==> " + executionTime);
            System.out.println("Url initiale: " + urlInitiale);
            System.out.println("url finale: " + urlFinale);
            System.out.println("*******fin affichage**********");*/
            //insérer la fenêtre des résultat DisplayEndWindow
            DisplayEndWindow dew = new DisplayEndWindow(urlInitiale, urlFinale,executionTime);

        }
    }
    /**
     * Test the connection of a page
     * @param url
     * @return
     */
    public static boolean correctNavigationLink(String url){
        try{
            URL obj = new URL(url);
            URLConnection conn = obj.openConnection();

            //Map<String, List<String>> map = conn.getHeaderFields();
            //System.out.println("Printing Response Header...\n");

            /*for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                //System.out.println("Key : " + entry.getKey()
                //        + " ,Value : " + entry.getValue());
            }*/
            //System.out.println("\nGet Response Header By Key ...\n");
            String server = conn.getHeaderField("Server");
            return server != null;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;

    }
}
