package cm.exchange.db.test;
import java.util.List;

import cm.exchange.db.UserService;
import cm.exchange.entity.User;
import android.test.AndroidTestCase;

/**
 * test case for database
 * @author qh
 *
 */
public class UserServiceTest extends AndroidTestCase {
	
	UserService ut = null;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		ut = new UserService(getContext());
		ut.open();
	}
	

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		ut.close();
	}
	
	/**
	 * test create database
	 * Note: you must create database before test other testcase
	 */
	public void testCreateDatabase(){
		ut.open();
	}
	
	/**
	 * test insert data
	 */
	public void testInsert(){
		User user = new User();
		user.setId(2);
		user.setLocation("fds");
		user.setQq("123456");
		user.setTelephone("1234");
		user.setUsername("liming");
		assertEquals(true, ut.insert(user));
	}
	
	/**
	 * test delete data
	 */
	public void testDeleteUser(){
		//sure return 1 , the effect row only one
		assertEquals(1, ut.deleteUserById(3));
	}
	
	public void testDleteAll(){
		assertEquals(2, ut.deleteAll());
	}
	
	/**
	 * test show data
	 */
	public void testGetAllData(){
		List<User> l = ut.getAllData();
		for(User u:l){
			System.out.println(u);
			if(u.getLocation() == null)
			System.out.println("null");
		}
		assertNotNull(l);
	}
	
	/**
	 * test insert data
	 */
	public void testUpdate(){
		User user = new User();
		user.setId(3);
		user.setLocation("3222");
		user.setQq("123456");
		user.setTelephone("1234");
		user.setUsername("liming");
		assertEquals(true, ut.update(user));
	}
	
	public void testCheckHaveDataById(){
		assertEquals(false, ut.checkHaveDataById(3));
	}
	
}
