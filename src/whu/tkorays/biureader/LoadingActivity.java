package whu.tkorays.biureader;


import whu.tkorays.biureader.db.DBHelper;
import whu.tkorays.biureader.db.SPDB;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class LoadingActivity extends Activity {
	private DBHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.loading);
		mHandle.sendEmptyMessage(0);
	}
	private Thread thd = new Thread() {
		public void run() {
			// 数据库初始化等
			try {
				
				
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandle.sendEmptyMessage(1);
		};
	};
	private Handler mHandle = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				thd.start();
				break;
			case 1:
				startActivity(new Intent(LoadingActivity.this,HomeActivity.class));
				finish();
				break;
			}
		}
	};
}
