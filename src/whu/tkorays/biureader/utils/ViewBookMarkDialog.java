package whu.tkorays.biureader.utils;

import whu.tkorays.biureader.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/*
 * �����ǩ�Ի���
 * @author tkorays
 * @email tkorays@hotmain.com
 * 
 */

public class ViewBookMarkDialog extends Dialog {
	private Context context;
	public ViewBookMarkDialog(Context context) {
		super(context);
		this.context = context;
	}
	public ViewBookMarkDialog(Context context,int theme) {
		super(context,theme);
		this.context = context;
	}
	private View.OnClickListener clickListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
        	ViewBookMarkDialog.this.dismiss();
        }
	};
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_view_bookmark);
            
            ListView bookmark_list = (ListView)findViewById(R.id.book_mark_list);
            bookmark_list.setAdapter(new ArrayAdapter<String>(this.context,R.layout.bookmark_list_item,new String[]{"��˵������","ʲô","Ӣ����","�Ŵ��磬������"}));
            
            Button btn = (Button)findViewById(R.id.book_mark_back);
            btn.setOnClickListener(clickListener);
            
    }

}
