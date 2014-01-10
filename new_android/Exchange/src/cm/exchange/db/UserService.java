package cm.exchange.db;

import java.util.ArrayList;
import java.util.List;

import cm.exchange.entity.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * the class have base method using for database of User
 * Note: before using method you must invoke {@link #open()}, after must invoke {@link #close()}
 * @author qh
 *
 */
public class UserService extends DBService<User>{
	Context context;
	public final static String TABLE_NAME = "user";
	public final static String CREATE_TABLE_SQL = "create table "+TABLE_NAME+" ("+
				UserColumns._ID + " integer primary key autoincrement,"+
				UserColumns.UID + " integer not null,"+
				UserColumns.USER_NAME + "  text not null,"+
				UserColumns.LOCATION + " text,"+
				UserColumns.TELEPHONE + " text,"+
				UserColumns.QQ + " text)";
	private final static class UserColumns implements BaseColumns{
		public final static String UID = "uid";
		public final static String USER_NAME = "username";
		public final static String LOCATION = "location";
		public final static String TELEPHONE = "telephone";
		public final static String QQ = "qq";
	}
	
	/**
	 * Constructor of the user table. Note: please invoke the method
	 * {@link #open()} before you use the database and {@link #close()} before
	 * the activity has been destroy, recommending invoke in the method
	 * Activity.onStop.
	 * 
	 * @param context
	 */
	public UserService(Context context) {
		super(context, TABLE_NAME);
		this.context = context;
	}
	
	
	@Override
	public boolean insert(User user){
		ContentValues values = new ContentValues();
		values.put(UserColumns.UID, user.getId());
		values.put(UserColumns.USER_NAME, user.getUsername());
		values.put(UserColumns.LOCATION, user.getLocation());
		values.put(UserColumns.TELEPHONE, user.getTelephone());
		values.put(UserColumns.QQ, user.getQq());
		return getDatabase().insert(TABLE_NAME, null, values) > -1;
	}
	
	/**
	 * 
	 * @param id
	 * @param username
	 * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. if id < 0, return -1.
	 */
	public int deleteUser(int id, String username){
		if(id < 0)
			return -1;
		if(username.equals(""))
			return getDatabase().delete(TABLE_NAME, UserColumns.UID+"="+id, null);
		return getDatabase().delete(TABLE_NAME, UserColumns.UID+"="+id+" and "+UserColumns.USER_NAME+"="+"'"+username+"'", null);
	}
	
	/**
	 * 
	 * @param id
	 * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. if id < 0, return -1.
	 */
	public int deleteUserById(int id){
		return deleteUser(id, "");
	}
	
	/**
	 * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise.
	 */
	public int deleteAll(){
		return getDatabase().delete(TABLE_NAME, "1", null);
	}
	
	@Override
	public List<User> getAllData(){
		List<User> list = new ArrayList<User>();
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{UserColumns.UID,
				UserColumns.USER_NAME, UserColumns.LOCATION, UserColumns.TELEPHONE,
				UserColumns.QQ}, null, null, null, null, null);
		User user = null;
		if(cursor.moveToFirst()){
			do{
				user = new User();
				user.setId(cursor.getInt(0));
				user.setUsername(cursor.getString(1));
				user.setLocation(cursor.getString(2));
				user.setTelephone(cursor.getString(3));
				user.setQq(cursor.getString(4));
				list.add(user);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	/**
	 * @return the row number of table
	 */
	public int getItemCount(){
		Cursor cursor = getDatabase().rawQuery("select count(*) from "+TABLE_NAME, null);
		boolean b = cursor.moveToLast();    
        int totalNum = (int)cursor.getLong(0);
        cursor.close();
        return totalNum;
	}
	
	/**
	 * 查找是否有指定id的数据
	 * @param id
	 * @return
	 */
	public boolean checkHaveDataById(int id){
		//if table is null, return false
		if(getItemCount() == 0)
			return false;
		Cursor cursor = getDatabase().rawQuery("select "+UserColumns.UID+" from "+TABLE_NAME+" where uid = "+id, null);
		//if have data return true, otherwise false
		boolean b = cursor.moveToLast();    
        cursor.close();
		return b;
	}
	
	
	/**
	 * get data by id
	 * @param id the id of goods
	 * @return if id is not exists will return null, otherwise return Goods
	 */
	public User getDataById(int id){
		User user = null;
		String sql = "select * from "+TABLE_NAME+" where uid="+id;
		Cursor cursor = getDatabase().rawQuery(sql, null);
		if(cursor.moveToFirst()){
			user = new User();
			user.setId(cursor.getInt(1));
			user.setUsername(cursor.getString(2));
			user.setLocation(cursor.getString(3));
			user.setTelephone(cursor.getString(4));
			user.setQq(cursor.getString(5));
		}
		cursor.close();
		return user;
	}
	
	/**
	 * update the user, if not exists will insert 
	 * @param user
	 * @return
	 */
	public boolean update(User user){
		if(checkHaveDataById(user.getId())){
			ContentValues values = new ContentValues();
			values.put(UserColumns.UID, user.getId());
			values.put(UserColumns.USER_NAME, user.getUsername());
			values.put(UserColumns.TELEPHONE, user.getTelephone());
			values.put(UserColumns.QQ, user.getQq());
			values.put(UserColumns.LOCATION, user.getLocation());
			int i = getDatabase().update(TABLE_NAME, values, "uid = "+user.getId(), null);
			return i<0 ? false:true;
		}
		return insert(user);
	}
	
	
}
