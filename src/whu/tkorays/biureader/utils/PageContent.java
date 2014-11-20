package whu.tkorays.biureader.utils;

import java.util.Vector;

public class PageContent {
	private long count;
	private Vector<String> linesVc;
	
	public PageContent(){
		count = 0;
		linesVc = new Vector<String>();
	}
	public void setCount(long l){
		this.count = l;
	}
	public long getCount(){
		return this.count;
	}
	public Vector<String> getLinesVc(){
		return this.linesVc;
	}
	public void add(String line){		
		linesVc.add(line);
	}
	public void add(Vector<String> v){
		linesVc.addAll(v);
	}
	
}
