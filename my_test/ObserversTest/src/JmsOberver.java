import java.util.Observable;
import java.util.Observer;

/**
 * 观察者2
 * @author Administrator
 *
 */
public class JmsOberver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("观察者2观察到结果:"+arg);
	}

}
