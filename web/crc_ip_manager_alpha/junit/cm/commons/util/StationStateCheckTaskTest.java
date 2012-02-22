package cm.commons.util;


import org.junit.Test;


public class StationStateCheckTaskTest {

	@Test
	public void test() throws InterruptedException{
		StationStateCheckTask checkTask=new StationStateCheckTask(4,4);
		checkTask.startCheckTask();
		checkTask.addOrRefreshTime("成都市");
		Thread.sleep(5000);
		checkTask.addOrRefreshTime("成都市qw");
		Thread.sleep(1000);
		System.out.println(checkTask.getWarnStation());
		Thread.sleep(5000);
		//checkTask.addOrRefreshTime("成都市");
		System.out.println(checkTask.getWarnStation());
	}
	
	
}
