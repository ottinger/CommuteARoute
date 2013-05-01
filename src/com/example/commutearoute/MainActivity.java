package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String DESTINATION = "com.example.commutearoute.DESTINATION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// This was code for old navbar on bottom before discovering ActionBar/OptionsMenu
	/*
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
*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, MainActivity.class);
	            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	 /** Called when user clicks the Go button */
    public void goToMap(View view) {
    	// Do something in response to button
    	Intent intent = new Intent(this, MapActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_end);
    	String destination = editText.getText().toString();
    	intent.putExtra(DESTINATION, destination);
    	startActivity(intent);
    }
}
