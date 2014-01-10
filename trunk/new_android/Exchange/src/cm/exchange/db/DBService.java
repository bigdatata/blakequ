package cm.exchange.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * this is abstract class 
 * if you want write a handler of entity class (using handler database)
 * you must extends this class and the method ({@link #open()} and {@link #close()})had been complete
 * @author qh
 *
 * @param <T> the type of entity class 
 */
public abstract class DBService<T> {
	private DBHelper dbHelper = null;
	private SQLiteDatabase database = null;
	Context context;
	String tableName, tableSQL;
	
	/**
	 * Constructor of the user table. Note: please invoke the method
	 * {@link #open()} before you use the database and {@link #close()} before
	 * the activity has been destroy, recommending invoke in the method
	 * Activity.onStop.
	 * @param context
	 * @param dbHelper
	 * @param database
	 * @param tableName
	 * @param tableSQL
	 */
	public DBService(Context context, String tableName){
		this.context = context;
		this.tableName = tableName;
	}
	/**
	 * open the database , if null, create it and open
	 */
	public final void open(){
		dbHelper = new DBHelper(context, tableName);
		database = dbHelper.getWritableDatabase();
	}
	
	/**
	 * close the database
	 */
	public final void close(){
		if(database.isOpen()){
			database.close();
		}
		dbHelper.close();
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	
	
	public DBHelper getDbHelper() {
		return dbHelper;
	}
	/**
	 * insert data 
	 * @param t data parameter
	 * @return if success return true
	 */
	public abstract boolean insert(T t);	
	
	/**
	 * get all data from databases
	 * @return list of data
	 */
	public abstract List<T> getAllData();
	
}
