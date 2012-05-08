import java.util.Observable;

/**
 * 被观察者
 * @author Administrator
 *
 */
public class Person extends Observable {
	
	public void doWork(){
		if(true){
			super.setChanged();
		}
		notifyObservers("观察者开始工作！");
	}
}
