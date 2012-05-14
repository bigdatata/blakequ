
public class ThreadTest extends Thread {
	int total;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setName("thread-0");
		System.out.println("**********id:"+getId()+" name:"+getName()+"**********");
		synchronized (this) {
			System.out.println("thread-0 is running...");
			for(int i=0; i<100; i++){
				total+=i;
				System.out.println("thread-0 total is "+total);
			}
			notify();//Í¨Öª»½ÐÑ
		}
		System.out.println("**********************");
//			System.out.println("id:"+Thread.currentThread().getId()+" name:"+Thread.currentThread().getName());
//			Thread.sleep(100);
	}
}
