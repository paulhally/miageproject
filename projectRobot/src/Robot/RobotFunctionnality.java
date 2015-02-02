package Robot;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class RobotFunctionnality {

	static String currentUrl;
	String[] keyWords;
	Integer time;
	private static WebDriver driver; 
	
	public static WebDriver setUp(String adresseUrl) throws Exception {
		// A faire qu une fois ?
		currentUrl = adresseUrl;
		driver.get(currentUrl);
		return driver;
		
	}
	
	public RobotFunctionnality(String url,String []kw,Integer t){
		
		this.currentUrl=url;
		this.keyWords=kw;
		this.time=t;
		driver = new FirefoxDriver(); 
	}
	
	public void go(){
		WebDriver d = null;
			System.out.println(currentUrl);
			try {
				d = RobotFunctionnality.setUp(currentUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	
		
		LinkExtractor le=new LinkExtractor(currentUrl);
		List<String>linkList=le.RecupLien(d);
		
		
	}
}
