import java.util.Observable;

/**
 * ���۲���
 * @author Administrator
 *
 */
public class Person extends Observable {
	
	public void doWork(){
		if(true){
			super.setChanged();
		}
		notifyObservers("�۲��߿�ʼ������");
	}
}
