package cm.exchange.ui;

import cm.exchange.entity.User;
import cm.exchange.util.FileUtil;
import android.app.Application;
import android.location.Location;

/**
 * 
 * @author qh
 *
 */
public class ExchangeApplication extends Application {
	private String uid;
	private String name;
	private User user;
	private Location location;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//using to create a file using to store image or other file.
		if(FileUtil.isSDCardAvailable()){
			FileUtil.createAPPFolder("pic");
		}
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}
