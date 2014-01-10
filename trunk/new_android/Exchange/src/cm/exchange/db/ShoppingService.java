package cm.exchange.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class ShoppingService extends DBService<Integer>{

	Context context;
	public final static String TABLE_NAME = "shopping";
	public final static String CREATE_TABLE_SQL = "create table "+TABLE_NAME+" ("+
				ShoppingColumns._ID + " integer primary key autoincrement,"+
				ShoppingColumns.GOODS_ID + " integer not null)";
	private final static class ShoppingColumns implements BaseColumns{
		public final static String GOODS_ID = "gid";
	}
	
	public ShoppingService(Context context){
		super(context, TABLE_NAME);
		this.context = context;
	}
	
	/**
	 * @return the item number of table
	 */
	public int getItemCount(){
		Cursor cursor = getDatabase().rawQuery("select count(*) from "+TABLE_NAME, null);
		cursor.moveToLast();    
        int totalNum = (int)cursor.getLong(0);
        cursor.close();
        return totalNum;
	}

	@Override
	public List<Integer> getAllData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Integer t) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(ShoppingColumns.GOODS_ID, t);
		return getDatabase().insert(TABLE_NAME, null, values) > -1;
	}
	
	public int[] getData(){
		int length = getItemCount();
		int[] ids = new int[length];
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{ShoppingColumns.GOODS_ID}, null, null, null, null, null);
		if(cursor.moveToFirst()){
			for(int i = 0 ; i<length; i++, cursor.moveToNext()){
				ids[i] = cursor.getInt(0);
			}
		}
		cursor.close();
		return ids;
	}
	
	/**
	 * 
	 * @param id
	 * @param username
	 * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. if id < 0, return -1.
	 */
	public int deleteById(int id){
		return getDatabase().delete(TABLE_NAME, ShoppingColumns.GOODS_ID+" = "+id, null);
	}
	
	public int deleteAll(){
		return getDatabase().delete(TABLE_NAME, "1", null);
	}
}
