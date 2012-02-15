package cm.commons.sys.service.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.User;
import cm.commons.sys.service.UserService;

import junit.framework.TestCase;
import java.util.List;

public class UserServiceTest extends TestCase {

	private UserService us;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		us = (UserService) ac.getBean("userService");
	}
	
	public void testGetByName(){
		System.out.println(us.getByName("11"));
	}
	
	public void testGetUserByAuthority(){
		for(User u :(List<User>)us.getUserByAuthority("admin")){
			System.out.println(u);
		}
	}
	
	public void testUpdateByName(){
		User u = new User();
		u.setAuthority("admin");
		u.setId(1);
		u.setPassword("33");
		u.setUsername("22");
		us.updateByName(u);
	}

}
