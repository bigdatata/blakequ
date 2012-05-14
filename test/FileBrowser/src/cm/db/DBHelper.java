package cm.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	public final static String DB_TAG = "database";
	private final static String DATABASE_NAME = "browser.db";
	private final static int VERSION = 1;
	private String tableName;

	/**
	 * using for create database and table
	 * @param context
	 * @param createTableSQL the sql for create table
	 * @param tableName the table name
	 */
	public DBHelper(Context context, String tableName){
		super(context, DATABASE_NAME, null, VERSION);
		this.tableName = tableName;
	}

	/**
	 * this method called only one time when the databases was created first time
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
			db.execSQL(FavourService.CREATE_TABLE_SQL);
			Log.i(DB_TAG, "create table");
		}catch(SQLException e){
			e.printStackTrace();
			Log.e(DB_TAG, "create table error");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		try{
			db.execSQL("drop table if exists "+tableName);
			onCreate(db);
		}catch(SQLException e){
			e.printStackTrace();
			Log.e(DB_TAG, "update database "+DATABASE_NAME+" error");
		}
	}

}
