
/**
 * 子线程循环10，主线程循环100，如此循环50次
 * @author Administrator
 *
 */
public class CurThread {
	int count1 = 0,count2 = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	class MyThread implements Runnable{

		Business b = new Business();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			b.mainThread();
		}
		
	}
	
	private class Business{
		boolean flag = true;
		public synchronized void mainThread(){
			if(flag){
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i1=0; i1<10; i1++){
				System.out.println(Thread.currentThread().getName()+"--main:"+i1);
			}
			flag = false;
			this.notify();
		}
		
		public synchronized void subThread(){
			if(!flag){
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i1=0; i1<5; i1++){
				System.out.println(Thread.currentThread().getName()+"--sub:"+i1);
			}
			flag = true;
			this.notify();
		}
	}
	
	
	
	

}
