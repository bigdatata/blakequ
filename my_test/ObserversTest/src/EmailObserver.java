import java.util.Observable;
import java.util.Observer;

/**
 * 观察者1
 * @author Administrator
 *
 */
public class EmailObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("观察者1观察到结果");
	}

}
