/**
 * CETTE CLASSE N'EST PAS UTILE MAIS SURTOUT NE PAS LA SUPPRIMER !!!!!!!!!!!!!!!
 * 
 * 
 * 
 * 
 * DON'T DELETE
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RobotCorp {

	  private WebDriver driver; 
	  private String baseUrl;
	  private String MotCle = "directory";

	  
	public WebDriver setUp(String adresseUrl) throws Exception {
		driver = new FirefoxDriver(); // A faire qu une fois ?
		baseUrl = adresseUrl;
		driver.get(baseUrl);
		return driver;
		
	}
	
	public String RecupLien(WebDriver d){
		System.out.println("Extraction des liens ...");
		List <WebElement> ListLien =  d.findElements(By.xpath("//a[@href]"));
		List <String> TableauLiens = new ArrayList<String>(); 
		String lien = new String();
		for(int i = 0; i < ListLien.size(); i++){
			lien = ListLien.get(i).getAttribute("href");
			if(lien.startsWith("http")){
				TableauLiens.add(lien);
			}
		}
		for(int j = 0; j< TableauLiens.size(); j++){
			System.out.println("Lien "+j+" : "+TableauLiens.get(j));
			
		}
		return ChoixDuLien(TableauLiens,d);
		
	}
	
	public String PremierLien(List <String> TableauLiens){
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
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WebDriver d;
		String Lien = new String();
		Lien = "http://www.directory.harvard.edu";
		
		RobotCorp Robot = new RobotCorp();
		d = Robot.setUp(Lien);
		System.out.println("Lien choisi : " +Robot.RecupLien(d));		
		System.out.println(".......Navigation finie ......");

	}

}
