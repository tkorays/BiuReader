package whu.tkorays.biureader.db;

import android.annotation.SuppressLint;

import java.util.ArrayList;



public class Book {
	private String bookname;
	private ArrayList<Long> bookmarks;
	private ArrayList<Long> allpagenum;
	public Book(){
		this.allpagenum = new ArrayList<Long>();
	}
	public Book(String name){
		this.bookname = name;
		this.allpagenum = new ArrayList<Long>();
	}
	public void setBookName(String name){
		this.bookname = name;
	}
	public String getBookName(){
		return this.bookname;
	}
	@SuppressLint("UseValueOf")
	public void addBookMark(long i){
		if(!bookmarks.contains(new Long(i))){
			bookmarks.add(new Long(i));
		}
	}
	public ArrayList<Long> getAllBookMarks(){
		return this.bookmarks;
	}
	public void setAllPageNum(ArrayList<Long> p){
		this.allpagenum.addAll(p);
		
	}
	public ArrayList<Long> getAllPageNum(){
		return this.allpagenum;
	}
}
