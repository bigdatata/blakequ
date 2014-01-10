package cm.exchange.db;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class HotWordService extends DBService<String> {

	Context context;
	public final static String TABLE_NAME = "hotword";
	public final static String CREATE_TABLE_SQL = "create table "+TABLE_NAME+" ("+
				HotWordColumns._ID+" integer primary key autoincrement,"+
				HotWordColumns.HOTWORD+" text not null)";
	private final static class HotWordColumns implements BaseColumns{
		public final static String HOTWORD = "hotword";
	}
	
	public HotWordService(Context context){
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

	/**
	 * get all data,no data will return null
	 */
	@Override
	public List<String> getAllData() {
		// TODO Auto-generated method stub
		List<String> list = null;
		String sql = "select "+HotWordColumns.HOTWORD+" from "+TABLE_NAME;
		Cursor cursor = getDatabase().rawQuery(sql, null);
		if(cursor.moveToFirst()){
			list = new ArrayList<String>();
			do{
				list.add(cursor.getString(0));
			}while(cursor.moveToNext());
		}
		cursor.close();
		return list;
	}

	@Override
	public boolean insert(String t) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(HotWordColumns.HOTWORD, t);
		return getDatabase().insert(TABLE_NAME, null, values) > -1;
	}
	
	/**
	 * insert list of words
	 * @param words
	 * @return
	 */
	public boolean insertList(String[] words){
		for(int i=0; i<words.length; i++){
			if(!insert(words[i])){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * update list
	 * @param word
	 * @return
	 */
	public boolean update(String word){
		ContentValues values = new ContentValues();
		values.put(HotWordColumns.HOTWORD, word);
		boolean b = checkHaveDataByString(word);
		if(b) return true;
		else{
			return insert(word);
		}
	}
	
	/**
	 * check have data which string is word
	 * @param word the key word
	 * @return
	 */
	public boolean checkHaveDataByString(String word){
		if(getItemCount() == 0)
			return false;
		Cursor cursor = getDatabase().rawQuery("select hotword from "+TABLE_NAME+" where hotword = "+"'"+word+"'", null);
		//if have data return true, otherwise false
		boolean b = cursor.moveToLast();    
        cursor.close();
		return b;
	}
	
	
}
