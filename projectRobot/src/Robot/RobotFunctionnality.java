package Robot;


import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class RobotFunctionnality {

	static String currentUrl;
	String[] keyWords;
	Integer time;
	Integer counter;
	private static WebDriver driver; 
	
	public static WebDriver setUp(String adresseUrl) throws Exception {
		
		currentUrl = adresseUrl;
		driver.get(currentUrl);
		return driver;
		
	}
	
	public RobotFunctionnality(String url,String []kw,Integer t){
		
		this.currentUrl=url;
		this.keyWords=kw;
		this.time=t;
		this.counter=5;
		driver = new FirefoxDriver(); 
	}
	
	public void go(Thread t){
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
		
		//LinkSelector --> wait for it
		
		Random rand=new Random();
		int i=rand.nextInt(linkList.size());
		System.out.println("Ramdom : " + i);
		try {
			d = RobotFunctionnality.setUp(linkList.get(i));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		counter--;
		if(counter!=0){
			this.currentUrl=linkList.get(i);
			try {
				System.out.println("En Attente pendant " + time + " sec");
				t.sleep(time*1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			go(t);
		}
		else{
			System.out.println("Surf terminé");
		}

	}
	
	public void back(WebDriver d){
		d.navigate().back();
	}
	
	public void close(WebDriver d){
		d.close();
	}
}
