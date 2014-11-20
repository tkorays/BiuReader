package whu.tkorays.biureader.utils;

public class BookContent {
	private String title;
	private String content;
	
	public BookContent(String t,String c){
		this.title = t;
		this.content = c;
	}
	public String getTitle(){
		return this.title;
	}
	public String getContent(){
		return this.content;
	}
	public void setTitle(String t){
		this.title = t;
	}
	public void setContent(String c){
		this.content = c;
	}
}
