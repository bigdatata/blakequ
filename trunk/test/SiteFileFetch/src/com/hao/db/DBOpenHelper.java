package com.hao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context)   
    {  
        super(context, "upload.db", null, 1);  
    }  

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE uploadlog (_id integer primary key autoincrement, uploadfilepath varchar(100), sourceid varchar(10))");
		db.execSQL("CREATE TABLE SmartFileDownlog (_id integer primary key autoincrement, downpath varchar(100), threadid integer, downlength integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS uploadlog");  
		 db.execSQL("DROP TABLE IF EXISTS SmartFileDownlog");
	     onCreate(db); 
	}

}
