package cm.exchange.db.test;

import java.util.List;

import cm.exchange.db.CommentService;
import cm.exchange.entity.Comment;
import android.test.AndroidTestCase;

public class CommentServiceTest extends AndroidTestCase{

	CommentService cs = null;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		cs = new CommentService(getContext());
		cs.open();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		cs.close();
	}
	
	/*
	 * test once
	 */
	public void testCreateDatabase(){
		cs.getDbHelper().onUpgrade(cs.getDatabase(), 1, 2);
	}
	
	public void testInsert(){
		Comment c = new Comment();
		c.setContent("I like all the comment");
		c.setTime("2010-2-3");
		c.setUid(0);
		c.setUsername("lisi");
		cs.insert(c);
	}
	
	public void testGetAllData(){
		List<Comment> l = cs.getAllData();
		for(Comment c:l){
			System.out.println(c);
		}
		assertNotNull(l);
	}

	
}
