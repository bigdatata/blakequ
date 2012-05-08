
public class Main {

	public static void main(String[] args) {
		//被观察者
		Person p = new Person();
		
		//观察者1
		EmailObserver eo = new EmailObserver();
		//观察者2
		JmsOberver jo = new JmsOberver();
		
		//将两个观察者添加到person
		p.addObserver(jo);
		p.addObserver(eo);
		
		System.out.println("----------");
		p.doWork();
	}
}
