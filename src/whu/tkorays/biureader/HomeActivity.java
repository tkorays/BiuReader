package whu.tkorays.biureader;
/*
 * Copyright tkorays 2014.All right reserved.
 * @author tkorays
 * @email tkorays@hotmain.com
 * 
 */

import java.util.ArrayList;

import whu.tkorays.biureader.db.BiuDB;
import whu.tkorays.biureader.db.DBHelper;
import whu.tkorays.biureader.utils.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private String [] books;
	private ListView listView;
	private TabHost tabHost;
	private TextView shelfBtn; 
	private TextView storeBtn;
	private TextView meBtn;
	private TextView historyBtn;
	private ImageView settingsBtn;
	
	private int font_size;
	private boolean is_black;
	
	
	/* 后台处理消息 */
	private Handler mHandler = new Handler() {
		  public void handleMessage(Message msg) {
		    switch (msg.what) {
		    	case 100:
		    		shelfBtn.setBackgroundColor(Color.parseColor("#BE2827"));
	        	break;
		    	case 101:
		    		storeBtn.setBackgroundColor(Color.parseColor("#BE2827"));
		        break;
		    	case 102:
		    		meBtn.setBackgroundColor(Color.parseColor("#BE2827"));
		        break;
		    	case 103:
		    		historyBtn.setBackgroundColor(Color.parseColor("#BE2827"));
		        break;  
		   }
		  }
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.home);
		
		updateBookList(); // 更新书单
		updateBottomBtn(); // bottomBtn的对象更新
		
		mHandler.sendEmptyMessage(100); // 第一个底部按钮颜色设置
        
		// 由初始状态（shelfBtn激活状态）进入底部按钮的状态机
		storeClickListen();
		meClickListen();
		historyClickListen();
        
		updateSettings();
		font_size=18;
		is_black=false;
	}
	private void updateSettings(){
		settingsBtn = (ImageView)findViewById(R.id.home_settings);
		settingsBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent it = new Intent(HomeActivity.this,SettingsActivity.class);
				startActivityForResult(it,0);
			}
			
		});
	}
	 
	private void updateBookList(){
		final IOHelper helper = new IOHelper();
		final Context context = this.getBaseContext();
		
		ArrayList<BookInfo> book_list =  helper.getBooks(context);
		String list[] = new String[book_list.size()];
		int i=0;
		for(BookInfo bi : book_list){
			list[i]=bi.getName();
			i++;
		}
		listView = (ListView)findViewById(R.id.books);
        listView.setAdapter(new ArrayAdapter<String>(HomeActivity.this, R.layout.list_item,list));
        
        listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView tv = (TextView)arg1;
				Intent intent = new Intent(HomeActivity.this,ReadActivity.class);
				intent.putExtra("path", helper.getBookPath(context, tv.getText().toString()));
				intent.putExtra("title", tv.getText().toString());
				intent.putExtra("fontsize", font_size);
				intent.putExtra("isblack", is_black);
				startActivity(intent);
			}
        	
        });
   
	}
	// and top
	private void updateBottomBtn(){
		shelfBtn = (TextView)findViewById(R.id.bookBtn1);
		storeBtn = (TextView)findViewById(R.id.bookBtn2);
		meBtn = (TextView)findViewById(R.id.bookBtn3);
		historyBtn = (TextView)findViewById(R.id.bookBtn4);
		updateSettings();
	}
	private void shelfClickListen(){
		shelfBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.home);
				mHandler.sendEmptyMessage(100);
				updateSettings();
				updateBookList();
				updateBottomBtn();
				storeClickListen();
				meClickListen();
				historyClickListen();
				//startActivity(new Intent(HomeActivity.this,StoreActivity.class));
			}
        });
	}
	private void storeClickListen(){
		storeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.store);
				mHandler.sendEmptyMessage(101);
				updateBottomBtn();
				shelfClickListen();
				meClickListen();
				historyClickListen();
			}
        });
	}
	private void meClickListen(){
		meBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.me);
				mHandler.sendEmptyMessage(102);
				updateBottomBtn();
				storeClickListen();
				shelfClickListen();
				historyClickListen();
			}
        });
	}
	private void historyClickListen(){
		historyBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setContentView(R.layout.history);
				mHandler.sendEmptyMessage(103);
				updateBottomBtn();
				storeClickListen();
				shelfClickListen();
				meClickListen();
			}
        });
	}


	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode==RESULT_OK){
			font_size = data.getIntExtra("fontsize", 14);
			is_black = data.getBooleanExtra("isblack", false);
		}
		
	}
}
