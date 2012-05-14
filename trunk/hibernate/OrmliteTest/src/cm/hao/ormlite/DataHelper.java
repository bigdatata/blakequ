package cm.hao.ormlite;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DBNAME = "ormlite.db";
	private static final int DBVERSION = 1;
	private Dao<User, Integer> userDao = null;

	public DataHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}
	
	public DataHelper(Context context){
		this(context, DBNAME, null, DBVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource conn) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(conn, User.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e(DataHelper.class.getName(), "创建数据库失败！", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource conn, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(conn, User.class, true);
			onCreate(db,conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e(DataHelper.class.getName(), "更新数据库失败！", e);
			e.printStackTrace();
		}

	}
	
	/**
	 * close the database
	 */
	public void close(){
		super.close();
		userDao = null;
	}
	
	/**
	 * @return Dao using for database
	 * @throws SQLException
	 */
	public Dao<User, Integer> getUserDao() throws SQLException{
		if(userDao == null){
			userDao = getDao(User.class);
		}
		return userDao;
	}

}
