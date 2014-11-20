package whu.tkorays.biureader;

import java.util.ArrayList;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import whu.tkorays.biureader.R; 
import whu.tkorays.biureader.db.Book;
import whu.tkorays.biureader.db.DBHelper;
import whu.tkorays.biureader.db.SPDB;
import whu.tkorays.biureader.utils.*;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ReadActivity extends Activity {
	private ImageView homeBtn;
	private PageWidget pageWidget;
	private Bitmap curBitmap, nextBitmap;
	private Canvas curCanvas, nextCanvas;
	private BookPage bookPage ;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int w = dm.widthPixels;
		int h = dm.heightPixels;
		curBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		nextBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		
		curCanvas = new Canvas(curBitmap);
		nextCanvas = new Canvas(nextBitmap);
		
		
		Intent intent = this.getIntent();
		
		//Toast.makeText(ReadActivity.this, "你点击的是:"+intent.getIntExtra("fontsize",14), 2000).show();

		bookPage = new BookPage(w,h,intent.getStringExtra("title"));
		bookPage.resetStyle(this);
		bookPage.init_page();
		bookPage.nextPage();
		bookPage.draw(curCanvas);
	
		pageWidget = new PageWidget(this, w, h);
		setContentView(pageWidget);
		pageWidget.setBitmaps(curBitmap, curBitmap);
		
		pageWidget.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent e) {
				boolean ret = false;
				if (v == pageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						pageWidget.abortAnimation();
						pageWidget.calcCornerXY(e.getX(), e.getY());
						
						bookPage.draw(curCanvas);
						if (pageWidget.DragToRight()) {
							if(bookPage.prevPage()){
								
								bookPage.draw(nextCanvas);
							}else{
								return false;
							}
							
						}else{
							if(bookPage.nextPage()){
								
								bookPage.draw(nextCanvas);
							}else{
								return false;
							}
						}
						pageWidget.setBitmaps(curBitmap, nextBitmap);
					}

					ret = pageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}
			
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "添加书签").setIcon(
		        android.R.drawable.ic_menu_add);
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "查看书签").setIcon(
		        android.R.drawable.ic_menu_search);
		menu.add(Menu.NONE, Menu.FIRST + 3, 3, "样式").setIcon(
		        android.R.drawable.ic_menu_manage);
		menu.add(Menu.NONE, Menu.FIRST + 4, 4, "开始").setIcon(
		        android.R.drawable.ic_menu_revert);
		menu.add(Menu.NONE, Menu.FIRST + 5, 5, "结束").setIcon(
		        android.R.drawable.ic_menu_directions);
		menu.add(Menu.NONE, Menu.FIRST + 6, 6, "跳转").setIcon(
		        android.R.drawable.ic_menu_mylocation);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case Menu.FIRST + 1:
				Toast.makeText(this, "删除菜单被点击了", Toast.LENGTH_LONG).show();
				break;

			case Menu.FIRST + 2:
				final ViewBookMarkDialog dialog = new ViewBookMarkDialog(ReadActivity.this,R.style.LoadingDialogTheme);
				dialog.show();
				break;

			case Menu.FIRST + 3:
				ReadSettingsDialog rd = new ReadSettingsDialog(ReadActivity.this,R.style.LoadingDialogTheme);
				rd.show();
				rd.setOnDismissListener(new OnDismissListener(){
					 @Override
			            public void onDismiss(DialogInterface arg0) {
						 	// 刷新界面
						 bookPage.resetStyle(ReadActivity.this);
						 bookPage.draw(curCanvas);
						pageWidget.setBitmaps(curBitmap, nextBitmap);
						pageWidget.postInvalidate();
			            }
				});
				break;

			case Menu.FIRST + 4:
				Toast.makeText(this, "添加菜单被点击了", Toast.LENGTH_LONG).show();
				break;

			case Menu.FIRST + 5:
				Toast.makeText(this, "详细菜单被点击了", Toast.LENGTH_LONG).show();
				break;

			case Menu.FIRST + 6:
				Toast.makeText(this, "发送菜单被点击了", Toast.LENGTH_LONG).show();
				break;

		}
		return false;
	}
}
