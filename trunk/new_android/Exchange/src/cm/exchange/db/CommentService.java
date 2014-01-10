package cm.exchange.db;

import java.util.ArrayList;
import java.util.List;

import cm.exchange.entity.Comment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class CommentService extends DBService<Comment>{
	Context context;
	public final static String TABLE_NAME = "comment";
	public final static String CREATE_TABLE_SQL = "create table "+TABLE_NAME+" ("+
		CommentColumns._ID + " integer primary key autoincrement,"+
		CommentColumns.UID + " integer not null,"+
		CommentColumns.USER_NAME + "  text not null,"+
		CommentColumns.TIME + " datetime not null,"+
		CommentColumns.CONTENT + " text)";
	private final static class CommentColumns implements BaseColumns{
		public final static String UID = "uid";
		public final static String USER_NAME = "username";
		public final static String TIME = "time";
		public final static String CONTENT = "content";
	}
	
	/**
	 * Constructor of the user table. Note: please invoke the method
	 * {@link #open()} before you use the database and {@link #close()} before
	 * the activity has been destroy, recommending invoke in the method
	 * Activity.onStop.
	 * 
	 * @param context
	 */
	public CommentService(Context context) {
		super(context, TABLE_NAME);
		this.context = context;
	}

	@Override
	public boolean insert(Comment t) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(CommentColumns.CONTENT, t.getContent());
		values.put(CommentColumns.TIME, t.getTime());
		values.put(CommentColumns.UID, t.getUid());
		values.put(CommentColumns.USER_NAME, t.getUsername());
		return getDatabase().insert(TABLE_NAME, null, values) > -1;
	}

	@Override
	public List<Comment> getAllData() {
		// TODO Auto-generated method stub
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = null;
		Cursor cursor = getDatabase().query(TABLE_NAME, new String[]{
				CommentColumns.UID, CommentColumns.USER_NAME,
				CommentColumns.TIME, CommentColumns.CONTENT
		}, null, null, null, null, CommentColumns.TIME+" desc");
		if(cursor.moveToFirst()){
			do{
				comment = new Comment();
				comment.setUid(cursor.getInt(0));
				comment.setUsername(cursor.getString(1));
				comment.setTime(cursor.getString(2));
				comment.setContent(cursor.getString(3));
				list.add(comment);
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
		Cursor cursor = getDatabase().rawQuery("select "+CommentColumns.UID+" from "+TABLE_NAME+" where uid = "+id, null);
		//if have data return true, otherwise false
		boolean b = cursor.moveToLast();    
        cursor.close();
		return b;
	}
	
	/**
	 * update
	 * @param c
	 * @return
	 */
	public boolean update(Comment c){
		if(checkHaveDataById(c.getUid())){
			ContentValues values = new ContentValues();
			values.put(CommentColumns.UID, c.getUid());
			values.put(CommentColumns.USER_NAME, c.getUsername());
			values.put(CommentColumns.TIME, c.getTime());
			values.put(CommentColumns.CONTENT, c.getContent());
			int i = getDatabase().update(TABLE_NAME, values, "uid = "+c.getUid(), null);
			return i<0 ? false:true;
		}
		return insert(c);
	}
	
	
}
