package whu.tkorays.biureader.utils;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Vector;

import whu.tkorays.biureader.R;
import android.content.Context;
import android.content.res.Resources;

public class IOHelper {
	private static Resources res;
	private static String[] booksName;
	private static String[] booksPath;
	
	public static ArrayList<BookInfo> getBooks(Context context){
		res = context.getResources();
		
		ArrayList<BookInfo> books = new ArrayList<BookInfo>();
		String booksName[] = res.getStringArray(R.array.books);
		String booksPath[] = res.getStringArray(R.array.books_path);
		int size = booksName.length>=booksPath.length?booksName.length : booksPath.length;
		for(int i=0;i<size;i++){
			books.add(new BookInfo(booksName[i],booksPath[i]));
		}
		return books;
	}
	public static String getBookPath(Context context,String name){
		res = context.getResources();
		booksName = res.getStringArray(R.array.books);
		booksPath = res.getStringArray(R.array.books_path);
		int size = booksName.length>=booksPath.length?booksName.length : booksPath.length;
		int i;
		for(i=0;i<size;i++){
			if(booksName[i].equals(name)){
				return booksPath[i];
			}
		}
		return "";
	}
	public static BookContent getBookContent(String title,int offset,int length){
		
		BookContent bc;
		InputStream is;
		InputStreamReader isr; 
		BufferedReader br;
		StringBuffer strBuffer = new StringBuffer();
		int i;
		int size = booksName.length>=booksPath.length?booksName.length : booksPath.length;
		for(i=0;i<size;i++){
			if(booksName[i].equals(title)){
				break;
			}
		}
		if(i>=size){
			return null;
		}
		try{
			String path =  booksPath[i];
			is = res.getAssets().open(path);
			
			isr = new InputStreamReader(is,"GBK");
			br = new BufferedReader(isr);
			
			String str;
			int j=0;
			
			while((str=br.readLine())!=null){
				j++;
				if(j>5){break;}
				strBuffer.append(str+'\n');
			}
			
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		bc = new BookContent(title,strBuffer.toString());
		
		return bc;
	}
	public PageContent ReadText(String title,long pos,int w,int lines) throws IOException{
		
		int i;
		int size = booksName.length>=booksPath.length?booksName.length : booksPath.length;
		for(i=0;i<size;i++){
			if(booksName[i].equals(title)){
				break;
			}
		}
		if(i>=size){
			return null;
		}
		
		String path =  booksPath[i];

		Reader reader;
		int c;
		int count = 0;
		PageContent pc;
		pc = new PageContent();
		int lineCount = 0;
		StringBuffer sb = new StringBuffer();
		reader = new InputStreamReader(res.getAssets().open(path),"GBK");
		reader.skip(pos);
		while((c= reader.read())!=-1 && lineCount <= lines){
			sb.append((char)c);
			if((count+1)%w==0 || (char)c=='\n'){
				if((char)c=='\n'){
					sb.deleteCharAt(sb.length()-1);
				}
				pc.add(sb.toString());
				sb.delete(0,sb.length());
				lineCount++;
			}
			count++;
		}
		pc.setCount(pos+count);
		
		return pc;
	}
}
