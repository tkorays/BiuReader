package whu.tkorays.biureader.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;



public class BiuDB {
	private SQLiteDatabase sqliteDb;
	private DBHelper dbHelper;
	private Context context;
	
	public BiuDB(Context context){
		this.context=context;
		sqliteDb = null;
		dbHelper = new DBHelper(this.context);
		sqliteDb = dbHelper.getWritableDatabase();
	}
	/* 插入一本书 */
	public void addBook(String name,String path){
		sqliteDb.execSQL("INSERT INTO books(bookname,path) values('"+name+"','"+path+"')");
	}
	public void deleteBook(String name){
		sqliteDb.delete("books", "bookname=?", new String[]{name});	
	}
	public void updateBook(String name,String path){
		ContentValues cv = new ContentValues();
		cv.put("bookname", name);
		cv.put("path", path);
		sqliteDb.update("books", cv, "bookname=?", new String[]{name});
	}
	/*
	 * 获得书本的地址
	 */
	public String getBookPath(String name){
		Cursor c = sqliteDb.rawQuery("SELECT * FROM books WHERE bookname=?", new String[]{name});
		if(c.moveToFirst()){
			return c.getString(c.getColumnIndex("path"));
		}else{
			return "";
		}
	}
	public void addBookMark(String name,int wordcount){
		Time time = new Time();
		time.setToNow();
		sqliteDb.execSQL("INSERT INTO bookmarks(bookname,wordcount,datetime) values('"+name+"','"+wordcount+"','"+time.toString()+"')");
		//sqliteDb.rawQuery("INSERT INTO bookmarks(bookname,wordcount,datetime) values(?,?,?)", new String[]{name,""+wordcount,time.toString()});
	}
	public void deleteBookMark(String name,String datetime){
		sqliteDb.delete("bookmarks", "bookname=? and datetime=?", new String[]{name,datetime});
	}
	public ArrayList<BookMark> getAllBookMarks(String name){
		ArrayList<BookMark> bookmarks = new ArrayList<BookMark>();
		int count=-1;
		String datetime="";
		
		Cursor c = sqliteDb.rawQuery("SELECT * FROM bookmarks WHERE bookname=?", new String[]{name});
		if(c.moveToFirst()){
			for(int i=0;i<c.getCount();i++){
				c.move(i);
				count = c.getInt(c.getColumnIndex("wordcount"));
				datetime = c.getString(c.getColumnIndex("datetime"));
				bookmarks.add(new BookMark(count,datetime));
			}
		}
		return bookmarks;
	}
	public void close(){
		sqliteDb.close();
	}
	
}
