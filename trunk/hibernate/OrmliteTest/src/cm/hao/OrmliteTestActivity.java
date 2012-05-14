package cm.hao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import cm.hao.ormlite.DataHelper;
import cm.hao.ormlite.User;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OrmliteTestActivity extends Activity {
    /** Called when the activity is first created. */
	TextView text1 = null;
	DataHelper dh = new DataHelper(this);
	Dao<User, Integer> dao = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text1 = (TextView)findViewById(R.id.text1);
        try {
			dao  = dh.getUserDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clear();
		
		User user = new User();
		user.setAge(13);
		user.setUsername("liling");
		insert(user);
		
		User user1 = new User();
		user1.setAge(133);
		user1.setUsername("ling");
		insert(user1);
		
		List<User> l = queryAll();
		for(User u:l){
			text1.append(u.toString());
		}
		
//		delete(user1);
//		text1.append("next\n");
//		List<User> l1 = queryAll();
//		for(User u:l1){
//			text1.append(u.toString());
//		}
		
//		dh.close();
    }
    
    public boolean insert(User user){
    	boolean success = false;
    	if(user != null){
			try {
				dao.create(user);
				success = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return success;
    }
    
    public boolean delete(User user){
    	boolean success = false;
    	try {
			dao.delete(user);
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
    }
    
    public List<User> queryAll(){
    	List<User> list = null;
    	try {
			list = dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return list;
    }
    
    public void clear(){
    	List<User> l = queryAll();
    	for(User u : l){
    		try {
				dao.delete(u);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}