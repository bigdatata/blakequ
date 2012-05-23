import java.io.IOException;


public class TestMain {
	/**
	 * 这里有三个测试线程：主线程，Daemon-thread, Default-thread
	 * Daemon-thread是守护线程，顾名思义就是依附于其他线程而生存的，就是说如果主线程和Default-thread没有停止
	 * 则Daemon-thread会一直运行，一旦两个线程都没运行了，则Daemon-thread也就没有生存的必要，自动结束
	 * 这里，主线程是当输入完成后自动结束，Default-thread是当循环到20自动结束
	 * 结果：
	 * 当在Default-thread的i小于20，输入----主线程结束，Default-thread没有结束，守护线程继续运行。
	 * 当在Default-thread的i等于20，输入----主线程结束，Default-thread结束，守护线程自动结束。
	 * @param args
	 */
	public static void main(String[] args) {
		TestThread test = new TestThread();
		test.setName("Daemon-thread");
	      test.setDaemon(true);    //调试时可以设置为false，那么这个程序是个死循环，没有退出条件。设置为true，即可主线程结束，test线程也结束。
	       test.start();
	       System.out.println("isDaemon = " + test.isDaemon());
	       System.out.println("main-thread waiting for input...");
	       
	       TestThread test2 = new TestThread();
	       test2.setName("Default-thread");
	       test2.start();
	       
	       try {
	           System.in.read();   // 接受输入，使程序在此停顿，一旦接收到用户输入，main线程结束，守护线程自动结束
	       } catch (IOException ex) {}
	       System.out.println("main-thread over!");
	}
}
