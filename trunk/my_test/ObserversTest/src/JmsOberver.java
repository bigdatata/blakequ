import java.util.Observable;
import java.util.Observer;

/**
 * �۲���2
 * @author Administrator
 *
 */
public class JmsOberver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("�۲���2�۲쵽���:"+arg);
	}

}
