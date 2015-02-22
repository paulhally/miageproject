package Robot;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

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
				close();
				JOptionPane jop1 = new JOptionPane();
	    		jop1.showMessageDialog(null, "URL ADRESS Not Found", "Information", JOptionPane.INFORMATION_MESSAGE);
	    		Thread.currentThread().stop();
	    		
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
	    		jop1.showMessageDialog(null, "URL ADRESS Not Found, back to the previous page.", "Information", JOptionPane.INFORMATION_MESSAGE);
	    		
			}
		}
	}
	
	public static void navigation(String lienChoisi, LinkSelector ls,Thread t) {
		lienSauvegarde.add(lienChoisi);
		driver.get(lienChoisi);
		
		try {
			System.out.println("Time Spent in a page " + time + " sec");
			t.sleep(time*1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		compteurAssocieAuLien.add(ls.CompareContenuPageAvecMotCles(driver));
	
	}
	
	
	public RobotFunctionnality(String url,String []kw,Integer t){
	
		this.currentUrl=url;
		this.keyWords=kw;
		this.time=t;
		this.counter=3;
		driver = new FirefoxDriver(); 
	}
	
	@SuppressWarnings("static-access")
	public void go(Thread t) throws Exception{
		
		
		int z=0;
		if(z==0){
			RobotFunctionnality.setUp(currentUrl);
			z++;
		}
		
	
		
		LinkExtractor le=new LinkExtractor(currentUrl);
		List<String>linkList=le.RecupLien(driver);
		
	
		LinkSelector ls=new LinkSelector(keyWords);
		//Select three Best Links Max
		List<String>tBLinks=ls.threeBestLinks(linkList);
		
		for(int u=0;u<tBLinks.size();u++){
			System.out.println("Three Best Links : " + tBLinks.get(u));
		}
		
		if(tBLinks.isEmpty()){
			back();
		}
		else{
			RobotFunctionnality.navigationSetUp(tBLinks,ls,t);
			currentUrl = ls.LienLePlusPertinent(this.compteurAssocieAuLien,this.lienSauvegarde);
			System.out.println("Le lien le plus pertinent est : "+ currentUrl);
			RobotFunctionnality.setUp(currentUrl);
		}
		
		counter--;
		if(counter!=0){
			
			go(t);
		}
		else{
			System.out.println("Surf terminé");
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
			Map<String, List<String>> map = conn.getHeaderFields();
		 
			//System.out.println("Printing Response Header...\n");
		 
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				//System.out.println("Key : " + entry.getKey() 
		                   //        + " ,Value : " + entry.getValue());
			}
		 
			//System.out.println("\nGet Response Header By Key ...\n");
			String server = conn.getHeaderField("Server");
		 
			if (server == null) {
				//System.out.println("Key 'Server' is not found!");
				return false;
			} else {
				//System.out.println("Server - " + server);
				return true;
			}
		 
			
	 
	    } catch (Exception e) {
		e.printStackTrace();
	    }
		
		return false;
	 
	  }
	
	
}
