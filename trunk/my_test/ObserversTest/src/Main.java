
public class Main {

	public static void main(String[] args) {
		//���۲���
		Person p = new Person();
		
		//�۲���1
		EmailObserver eo = new EmailObserver();
		//�۲���2
		JmsOberver jo = new JmsOberver();
		
		//�������۲�����ӵ�person
		p.addObserver(jo);
		p.addObserver(eo);
		
		System.out.println("----------");
		p.doWork();
	}
}
