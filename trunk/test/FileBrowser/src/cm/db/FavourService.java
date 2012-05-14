package cm.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import cm.constant.CollectFile;

public class FavourService {
	private DBHelper dbHelper = null;
	private SQLiteDatabase database = null;
	private Context context;
	
	public final static String TABLE_NAME = "collect";
	private final class CollectColumns implements BaseColumns{
		public static final String TYPE = "type";
		public static final String PATH = "path";
		public static final String DATE = "date";
		public static final String NAME = "name";
	}
	public final static String CREATE_TABLE_SQL = "create table "+TABLE_NAME+" ("+
	CollectColumns._ID + " integer primary key autoincrement,"+
	CollectColumns.PATH + " text not null,"+
	CollectColumns.DATE + " datetime not null,"+
	CollectColumns.TYPE + " text,"+
	CollectColumns.NAME + " text)";
	
	public FavourService(Context context){
		this.context = context;
		open();
	}
	/**
	 * open the database , if null, create it and open
	 */
	public final void open(){
		dbHelper = new DBHelper(context, TABLE_NAME);
		database = dbHelper.getWritableDatabase();
	}
	
	public final boolean isOpen(){
		return database.isOpen();
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
	
	/**
	 * @return the row number of table
	 */
	public int getItemCount(){
		Cursor cursor = database.rawQuery("select count(*) from "+TABLE_NAME, null);
		boolean b = cursor.moveToLast();    
        int totalNum = (int)cursor.getLong(0);
        cursor.close();
        return totalNum;
	}
	
	public List<CollectFile> getAllData(){
		List<CollectFile> files = null;
		Cursor cursor = database.query(TABLE_NAME, new String[]{CollectColumns.PATH, CollectColumns.DATE,
				CollectColumns.NAME, CollectColumns.TYPE}, null, null, null, null, CollectColumns.DATE+" desc");
		CollectFile file = null;
		if(cursor.moveToFirst()){
			files = new ArrayList<CollectFile>();
			do{
				file = new CollectFile();
				file.setPath(cursor.getString(0));
				file.setDate(cursor.getString(1));
				file.setName(cursor.getString(2));
				file.setType(cursor.getString(3));
				files.add(file);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return files;
	}
	
	public List<CollectFile> getAllDataByType(){
		List<CollectFile> files = null;
		Cursor cursor = database.query(TABLE_NAME, new String[]{CollectColumns.PATH, CollectColumns.DATE,
				CollectColumns.NAME, CollectColumns.TYPE}, null, null, null, null, CollectColumns.TYPE);
		CollectFile file = null;
		if(cursor.moveToFirst()){
			files = new ArrayList<CollectFile>();
			do{
				file = new CollectFile();
				file.setPath(cursor.getString(0));
				file.setDate(cursor.getString(1));
				file.setName(cursor.getString(2));
				file.setType(cursor.getString(3));
				files.add(file);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return files;
	}
	
	
	public List<CollectFile> getDataByType(String type){
		List<CollectFile> files = null;
		String sql = "select * from "+TABLE_NAME+" where type='"+type+"' order by "+CollectColumns.DATE;
		Cursor cursor = database.rawQuery(sql, null);
		CollectFile file = null;
		if(cursor.moveToFirst()){
			files = new ArrayList<CollectFile>();
			do{
				file = new CollectFile();
				file.setPath(cursor.getString(1));
				file.setDate(cursor.getString(2));
				file.setType(cursor.getString(3));
				file.setName(cursor.getString(4));
				files.add(file);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return files;
	}
	
	public boolean insert(CollectFile file){
		ContentValues values = new ContentValues();
		if(file != null){
			values.put(CollectColumns.DATE, file.getDate());
			values.put(CollectColumns.NAME, file.getName());
			values.put(CollectColumns.PATH, file.getPath());
			values.put(CollectColumns.TYPE, file.getType());
			return database.insert(TABLE_NAME, null, values) > -1;
		}
		return false;
	}
	
	/**
	 * 查找是否有指定id的数据
	 * @param id
	 * @return
	 */
	public boolean checkHaveDataByPath(String path){
		//if table is null, return false
		if(getItemCount() == 0)
			return false;
		Cursor cursor = database.rawQuery("select "+CollectColumns.PATH+" from "+TABLE_NAME+" where path = '"+path+"'", null);
		//if have data return true, otherwise false
		boolean b = cursor.moveToLast();    
        cursor.close();
		return b;
	}
	
	public boolean update(CollectFile file){
		if(checkHaveDataByPath(file.getPath())){
			ContentValues values = new ContentValues();
			values.put(CollectColumns.DATE, file.getDate());
			values.put(CollectColumns.NAME, file.getName());
			values.put(CollectColumns.PATH, file.getPath());
			values.put(CollectColumns.TYPE, file.getType());
			int i = database.update(TABLE_NAME, values, "path = '"+file.getPath()+"'", null);
			return i<=0 ? false:true;
		}
		return insert(file);
	}

	public boolean deleteByName(String name){
//		String sql = "delete from "+TABLE_NAME+" where "+CollectColumns.NAME+"="+name;
		int result = database.delete(TABLE_NAME, CollectColumns.NAME +"='"+name+"'", null);
		return result<=0 ? false:true;
	}
	
	public boolean deleteByPath(String path){
		int result = database.delete(TABLE_NAME, CollectColumns.PATH+"='"+path+"'", null);
		return result<=0 ? false:true;
	}
	
	public int deleteAll(){
		return database.delete(TABLE_NAME, "1", null);
	}
}
