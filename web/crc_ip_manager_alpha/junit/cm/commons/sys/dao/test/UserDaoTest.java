package cm.commons.sys.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.User;
import cm.commons.sys.dao.UserDao;
import cm.commons.sys.dao.impl.UserDaoImpl;

import junit.framework.TestCase;

public class UserDaoTest extends TestCase {

	private UserDao ud;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ud = (UserDao) ac.getBean("userDao");
	}
	
	public void testGetByName(){
		System.out.println(ud.getByName("111"));
	}
	
	public void testDeleteByName(){
		ud.deleteByName("111");
	}
	
	public void testGetUserByAuthority(){
		System.out.println(ud.getUserByAuthority("user"));
	}
	
	public void testUpdateByName(){
		User u = new User();
		u.setAuthority("user");
		u.setPassword("1111111");
		u.setUsername("223");
		ud.updateByName(u);
		
	}
}
