
public class TestThread extends Thread {
	boolean flag = true;
	int i=0;
	public void run() {           //永真循环线程
	       while(flag){
	    	   try {
	               Thread.sleep(1000);
	           } catch (InterruptedException ex) {   }
	           System.out.println(getName()+":"+(i++));
	           if(getName().equals("Default-thread") && i==20){
	        	   flag = false;
	        	   System.out.println("Default-thread dead!-------");
	           }
	       }
	 }
	
	
}
