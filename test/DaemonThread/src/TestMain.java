import java.io.IOException;


public class TestMain {
	/**
	 * ���������������̣߳����̣߳�Daemon-thread, Default-thread
	 * Daemon-thread���ػ��̣߳�����˼����������������̶߳�����ģ�����˵������̺߳�Default-threadû��ֹͣ
	 * ��Daemon-thread��һֱ���У�һ�������̶߳�û�����ˣ���Daemon-threadҲ��û������ı�Ҫ���Զ�����
	 * ������߳��ǵ�������ɺ��Զ�������Default-thread�ǵ�ѭ����20�Զ�����
	 * �����
	 * ����Default-thread��iС��20������----���߳̽�����Default-threadû�н������ػ��̼߳������С�
	 * ����Default-thread��i����20������----���߳̽�����Default-thread�������ػ��߳��Զ�������
	 * @param args
	 */
	public static void main(String[] args) {
		TestThread test = new TestThread();
		test.setName("Daemon-thread");
	      test.setDaemon(true);    //����ʱ��������Ϊfalse����ô��������Ǹ���ѭ����û���˳�����������Ϊtrue���������߳̽�����test�߳�Ҳ������
	       test.start();
	       System.out.println("isDaemon = " + test.isDaemon());
	       System.out.println("main-thread waiting for input...");
	       
	       TestThread test2 = new TestThread();
	       test2.setName("Default-thread");
	       test2.start();
	       
	       try {
	           System.in.read();   // �������룬ʹ�����ڴ�ͣ�٣�һ�����յ��û����룬main�߳̽������ػ��߳��Զ�����
	       } catch (IOException ex) {}
	       System.out.println("main-thread over!");
	}
}
