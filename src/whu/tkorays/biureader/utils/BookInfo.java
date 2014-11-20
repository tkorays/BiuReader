package whu.tkorays.biureader.utils;

public class BookInfo {
	private String name;
	private String path;
	
	public BookInfo(String n,String p){
		name = n;
		path = p;
	}
	public String getName(){
		return name;
	}
	public String getPath(){
		return path;
	}
}
