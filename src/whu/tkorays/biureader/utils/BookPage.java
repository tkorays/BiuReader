package whu.tkorays.biureader.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import whu.tkorays.biureader.R;
import whu.tkorays.biureader.ReadActivity;
import whu.tkorays.biureader.db.Book;
import whu.tkorays.biureader.db.SPDB;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.format.Time;
import android.view.View;


@SuppressLint("UseValueOf")
public class BookPage {
	
	private int screenWidth; // ��Ļ���
	private int screenHeight; // ��Ļ�߶�
	private int fontSize; // �����С
	private int lineHeight;	//ÿ�еĸ߶�
	private int marginWidth = 15; // �������Ե�ľ���
	private int marginHeight = 15; // �������Ե�ľ���
	private int textColor; // ������ɫ
	private Bitmap bgBitmap; // ����ͼƬ
	private int bgColor; // ������ɫ

	private Paint paint;
	private Paint paintBottom;
	private int visibleWidth; // ��Ļ�п���ʾ�ı��Ŀ��
	private int visibleHeight;
	private BookContent bc; 
	// ��Ҫ������½ڶ���
	private Vector<String> linesVe; // ���½ڃ��ݷֳ��У�����ÿҳ���д洢��vector������
	private int lineCount; // һ���½��ڵ�ǰ������һ���ж�����

	private String content;
	private int contentLen; // �½ڵĳ���
	// private int curCharPos; // ��ǰ�ַ����½�������λ��
	private int charBegin; // ÿһҳ��һ���ַ����½��е�λ��
	private int charEnd; // ÿһҳ���һ���ַ����½��е�λ��
	private boolean isfirstPage;
	private boolean islastPage;
	private IOHelper helper;
	private PageContent pc;
	private Vector<Vector<String>> pagesVe;
	int pageNum;
	private String bookName;
	//private Vector<PageContent> saved_pc;
	
	private Vector<PageContent> prev_pc;
	private Vector<PageContent> next_pc;
	private ArrayList<Long> page_num;
	private int curPageIndex;
	private boolean is_black;
	private Context context;
	
	

	public BookPage(int screenWidth,int screenHeight,String title){
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.bookName=title;
		helper = new IOHelper();
		pc  = new PageContent(); // current page
		
		prev_pc = new Vector<PageContent>(); // previous pages
		next_pc = new Vector<PageContent>(); // next pages
		page_num = new ArrayList<Long>();
		curPageIndex= -1;
		init();
		//init_page();
	}
	public void resetStyle(Context context){
		
		this.context = context;
		SPDB spdb = new SPDB(this.context);
		boolean font_size_changed = fontSize==spdb.getFontSize();
		is_black = spdb.getBlackMode();
		
		fontSize = spdb.getFontSize();
		if(spdb.getFontSize()<0 || spdb.getFontSize()>40){
			fontSize = 24;
		}
		if(is_black){
			bgColor = Color.BLACK;
			textColor=Color.WHITE;
		}else{
			textColor = Color.BLACK;
			bgColor = 0xffff9e85;
		}
		paint.setTextSize(fontSize);
		paint.setColor(textColor);
		if(!is_black){
			setBgBitmap(BitmapFactory.decodeResource(context.getResources(),
					R.drawable.read_bg));
		}
		int word_count = 0;
		int index = 0;
		// ��������С�ı���
		if(font_size_changed){
			if(page_num.size()==0){
				return;
			}
			
			word_count = (int) pc.getCount(); 
			page_num.clear();
			pc.setCount(0);
			init_page();
			for(Long l:page_num){
				index++;
				if(word_count<=l.intValue()){
					break;
				}
			}
			this.curPageIndex=index-1;
			pc.setCount(page_num.get(this.curPageIndex));
			this.nextPage();
		}
	}
	
	public boolean is_black(){
		return is_black;
	}
	protected void init() {
		bgBitmap = null;
		
		textColor = Color.BLACK;
		bgColor = 0xffff9e85;
		
		charBegin = 0;
		charEnd = 0;
		fontSize = 30;
		lineHeight = fontSize + 8;
		linesVe = new Vector<String>();

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Align.LEFT);
		paint.setTextSize(fontSize);
		paint.setColor(textColor);
		
		paintBottom = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintBottom.setTextAlign(Align.LEFT);
		paintBottom.setTextSize(fontSize / 2);
		paintBottom.setColor(textColor);

		visibleWidth = screenWidth - marginWidth * 2;
		visibleHeight = screenHeight - marginHeight * 2;
		lineCount = visibleHeight / lineHeight - 2;
		isfirstPage = true;
		islastPage = false;
		pagesVe = new Vector<Vector<String>>();
		pageNum = -1;
		
	}
	public void init_page(){
		PageContent tmp;
		page_num.add(new Long(0));
		do{
			tmp = pc;
			try {
				pc = helper.ReadText(this.bookName , tmp.getCount(),(int)(visibleWidth/paint.getTextSize()), lineCount);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pc.getLinesVc().size()!=0){
				page_num.add(new Long((long) pc.getCount()));
			}
		}while(pc.getLinesVc().size()!=0);	
		
	}
	
	public Vector<String> getCurPage() {
		return linesVe;
	}
	
	public void setBgBitmap(Bitmap bMap) {
		bgBitmap = Bitmap.createScaledBitmap(bMap, screenWidth, screenHeight,
				true);
	}
	protected void slicePage() {
		pagesVe.clear();
		int curPos = 0;
		while (curPos < contentLen) {
			
		}
	}

	public boolean isLastPage(){
		
		return false;
	}
	
	// �����ҳ�����ڣ�����false
	// �����ҳ���ڣ���ȡʮҳ��������ܶ����پͶ�����
	public boolean nextPage(){
		if(curPageIndex>=page_num.size()-1){
			return false;
		}
		try {
			pc = helper.ReadText(this.bookName , page_num.get(curPageIndex+1).intValue(),(int)(visibleWidth/paint.getTextSize()), lineCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pc.getLinesVc().size()==0){
			return false;
		}
		curPageIndex++;
		return true;
	}
	public boolean prevPage(){
		if(curPageIndex<=0){
			return false;
		}
		try {
			pc = helper.ReadText(this.bookName , page_num.get(curPageIndex-1).intValue(),(int)(visibleWidth/paint.getTextSize()), lineCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pc.getLinesVc().size()==0){
			return false;
		}
		curPageIndex--;
		return true;
	}
	public void draw(Canvas c){
		
		if (bgBitmap == null || this.is_black)
			c.drawColor(bgColor);
		else
			c.drawBitmap(bgBitmap, 0, 0, null);
		int y = marginHeight;
		
		for (String line : pc.getLinesVc()) {
			y += lineHeight;
			c.drawText(line, marginWidth, y, paint);
		}
		
		
		
		float percent = curPageIndex/(float)page_num.size();
		DecimalFormat df = new DecimalFormat("#0.0");
		String percetStr = df.format(percent * 100) + "%";

		Time time = new Time();
		time.setToNow();
		String timeStr;
		if (time.minute < 10)
			timeStr = "" + time.hour + " : 0" + time.minute;
		else
			timeStr = "" + time.hour + " : " + time.minute;

		int pSWidth = (int) paintBottom.measureText("99.9%") + 2;
		int titWidth = (int) paintBottom.measureText(this.bookName);

		
		c.drawText(timeStr, marginWidth / 2, screenHeight - 5, paintBottom);
		c.drawText(this.bookName, screenWidth / 2 - titWidth / 2,
				screenHeight - 5, paintBottom);
		c.drawText(percetStr, screenWidth - pSWidth, screenHeight - 5, paintBottom);
	}
	
	
	
}
