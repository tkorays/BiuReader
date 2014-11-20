package whu.tkorays.biureader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "jx_biu_reader.db";
	private static final int version = 1;
	
	public DBHelper(Context context){
		super(context, DB_NAME, null, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建book表，存储书本信息 IF NOT EXISTS
		db.execSQL("CREATE TABLE IF NOT EXISTS books "+
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, bookname TEXT,path TEXT)");
		// 创建书签表  IF NOT EXISTS
		db.execSQL("CREATE TABLE IF NOT EXISTS bookmarks "+
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT,bookname TEXT NOT NULL,wordcount INTEGER,datetime TEXT NOT NULL)");
		// 创建设置表 IF NOT EXISTS
		db.execSQL("CREATE TABLE IF NOT EXISTS settings "+
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT,fontsize INTEGER,blackmode INTEGER)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
}
