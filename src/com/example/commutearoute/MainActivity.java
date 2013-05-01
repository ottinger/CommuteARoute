package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		RadioButton radioButton;
		radioButton = (RadioButton) findViewById(R.id.home_button);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
		radioButton = (RadioButton) findViewById(R.id.stats_button);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
		radioButton = (RadioButton) findViewById(R.id.report_button);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
		radioButton = (RadioButton) findViewById(R.id.show_map_button);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
	}

	private CompoundButton.OnCheckedChangeListener btnNavBarOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				Toast.makeText(MainActivity.this, buttonView.getText(), Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
