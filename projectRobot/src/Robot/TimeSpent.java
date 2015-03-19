package Robot;

public class TimeSpent extends Thread{
	
	int time;
	boolean arret;
	public TimeSpent(){
		time=0;
		arret=true;
	}
	
	public void run(){
		try {
			while(arret){
				this.sleep(1000);
				time++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void arret(){
		this.arret=false;
	}
}
