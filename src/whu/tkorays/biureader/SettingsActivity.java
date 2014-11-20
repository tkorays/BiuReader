package whu.tkorays.biureader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	private Spinner fontSizeSp;
	private CheckBox black_mod;
	private int font_size;
	private boolean is_black;
	private Button settings_ok;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.settings);
		
		intent = this.getIntent();
		font_size = 14;
		is_black = false;
		
		fontSizeSp = (Spinner)findViewById(R.id.font_size_spinner);
		String[] mItems = getResources().getStringArray(R.array.fontsizespinner);
		ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		fontSizeSp.setAdapter(_Adapter);
		fontSizeSp.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String str=parent.getItemAtPosition(position).toString();
				font_size = Integer.parseInt(str);
				intent.putExtra("fontsize", font_size);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		black_mod = (CheckBox)findViewById(R.id.black_mod);
		black_mod.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton g, boolean v) {
				is_black = v;
				intent.putExtra("isblack",v);
			}
			
		});
		settings_ok = (Button)findViewById(R.id.settings_ok);
		settings_ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				intent.putExtra("fontsize", font_size);
				intent.putExtra("isblack",is_black);
				setResult(RESULT_OK,intent);
				finish();
			}
			
		});
	}

}
