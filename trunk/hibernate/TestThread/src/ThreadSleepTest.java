
public class ThreadSleepTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("main id:"+Thread.currentThread().getId()+" name:"+Thread.currentThread().getName());
		ThreadTest test = new ThreadTest();
		test.start();
		System.out.println("thread-0 is start");
		synchronized (test) {
			try {
				System.out.println("waiting for thread-0 to complete...");
				test.wait();//释放锁并等待test锁的释放
				System.out.println("thread-0 end now back to main thread");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("thread-0 total is : "+test.total);
		}
		
//		Thread th1 = new Thread(new TestRunnable("run-1"));
//		Thread th2 = new Thread(new TestRunnable("run-2"));
//		th1.start();
//		th2.start();
	}

}
