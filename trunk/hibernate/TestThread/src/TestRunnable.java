
public class TestRunnable implements Runnable {
	String name="";
	public TestRunnable(){}
	public TestRunnable(String name){
		this.name = name;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//设置当前线程名字
			if(!"".equals(name)) Thread.currentThread().setName(name);
			Thread.sleep(100);
			System.out.println("testRunnable id:"+Thread.currentThread().getId()+" name:"+Thread.currentThread().getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
