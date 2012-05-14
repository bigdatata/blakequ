
public class IncDec {
	int j=0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IncDec id = new IncDec();
		Inc inc = id.new Inc();
		Dec dec = id.new Dec();
		for(int i=0; i<2; i++){
			id.new Dec().start();
			new Thread(inc).start();
		}
	}
	
	private class Inc implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0; i<100; i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				inc();
			}
		}
		
	}
	
	private class Dec extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i=0; i<100; i++){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dec();
			}
		}
		
	}
	
	private synchronized void inc(){
		j --;
		System.out.println(Thread.currentThread().getName()+"--inc:"+j);
	}
	
	private synchronized void dec(){
		j ++;
		System.out.println(Thread.currentThread().getName()+"--dec:"+j);
	}

}
