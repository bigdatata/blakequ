package com.hao.db;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UploadLogService {
	private SQLiteDatabase database = null;
	private DBOpenHelper dbOpenHelper;
	//给出上下文对象
	public UploadLogService(Context context)
	{
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	
	private boolean isOpen(){
		return database.isOpen();
	}
	
	/**
	 * open the database , if null, create it and open
	 */
	public final void open(){
		database = dbOpenHelper.getWritableDatabase();
	}
	
	/**
	 * close the database
	 */
	private final void close(){
		if(database.isOpen()){
			database.close();
		}
		dbOpenHelper.close();
	}
	
	/**
	 * 保存上传文件断点数据
	 * @param sourceid
	 * @param uploadFile
	 */
	public void save(String sourceid, File uploadFile)
	{
		this.open();
		database.execSQL("insert into uploadlog(uploadfilepath, sourceid) values(?,?)",
				new Object[]{uploadFile.getAbsolutePath(),sourceid});
		this.close();
	}
	/**
	 * 删除上传文件断点数据
	 * @param uploadFile
	 */
	public void delete(File uploadFile)
	{
		open();
		database.execSQL("delete from uploadlog where uploadfilepath=?", new Object[]{uploadFile.getAbsolutePath()});
		close();
	}
	/**
	 * 根据文件的上传路径得到绑定的id
	 * @param uploadFile
	 * @return
	 */
	public String getBindId(File uploadFile)
	{
		open();
		String file = null;
		Cursor cursor = database.rawQuery("select sourceid from uploadlog where uploadfilepath=?", 
				new String[]{uploadFile.getAbsolutePath()});
		if(cursor.moveToFirst())
		{
			file = cursor.getString(0);
		}
		cursor.close();
		close();
		return file;
	}
}
