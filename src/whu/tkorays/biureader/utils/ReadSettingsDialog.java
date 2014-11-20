package whu.tkorays.biureader.utils;

import whu.tkorays.biureader.R;
import whu.tkorays.biureader.ReadActivity;
import whu.tkorays.biureader.db.SPDB;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/*
 * ‘ƒ∂¡…Ë÷√∂‘ª∞øÚ
 * 
 */

public class ReadSettingsDialog extends Dialog {
	private int font_size;
	private boolean black_mode;
	private Context context;

	public ReadSettingsDialog(Context context,int theme) {
		super(context,theme);
		this.context = context;
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_read_settings);
		
		SeekBar sb = (SeekBar)findViewById(R.id.sb_fontsize);
		CheckBox cb = (CheckBox)findViewById(R.id.select_black_mode);
		final TextView fs = (TextView)findViewById(R.id.fontsize_label);
		Button set_ok = (Button)findViewById(R.id.settings_ok);
		
		sb.setProgress(24);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(progress<18){
					progress = 18;
					seekBar.setProgress(progress);
				}
				fs.setText(""+progress);
				font_size = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
		});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				black_mode = isChecked;
			}
			
		});
		
		set_ok.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				SPDB spdb = new SPDB(context);
				spdb.setFontSize(font_size);
				spdb.setBlackMode(black_mode);
				ReadSettingsDialog.this.dismiss();
			}
		});
		
	}

}
