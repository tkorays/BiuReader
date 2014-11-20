package whu.tkorays.biureader.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class SPDB {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	
	@SuppressLint("CommitPrefEdits")
	public SPDB(Context context){
		sp = context.getSharedPreferences("jx_biu_settings", 0);
		editor = sp.edit();
		if(!sp.getBoolean("valid", false)){
			this.setFontSize(24);
			this.setBlackMode(false);
			editor.putBoolean("valid", true);
			editor.commit();
		}
	}
	public void setFontSize(int size){
		editor.putInt("fontsize", size);
		editor.commit();
	}
	public int getFontSize(){
		return sp.getInt("fontsize", 24);
	}
	public void setBlackMode(boolean b){
		editor.putBoolean("blackmode", b);
		editor.commit();
	}
	public boolean getBlackMode(){
		return sp.getBoolean("blackmode", false);
	}
}
