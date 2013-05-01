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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String DESTINATION = "com.example.commutearoute.DESTINATION";
	public final static String TRANSPORT_MODE = "com.example.commutearoute.TRANSPORT_MODE";
	public final static String LEVEL = "LEVEL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setLevel();
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
		Intent intent;
		// Handle item selection
	    switch (item.getItemId()) {
	        case R.id.home:
	        	// app icon in action bar clicked; go home
	        	intent = new Intent(this, MainActivity.class);
	        	startActivity(intent);
	        	return true;
	        case R.id.stats:
	        	// stats icon clicked; go to stats page
	        	intent = new Intent(this, StatsActivity.class);
	            startActivity(intent);
	            return true;
	        case R.id.report:
	        	// report button clicked; go to report page
	        	intent = new Intent(this, ReportActivity.class);
	        	startActivity(intent);
	        	return true;
	        case R.id.map:
	        	// show map button clicked; go to map
	        	intent = new Intent(this, MapActivity.class);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	 /** Called when user clicks the Go button */
    public void goToMap(View view) {
    	// Add destination address
    	Intent intent = new Intent(this, MapActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_end);
    	String destination = editText.getText().toString();
    	intent.putExtra(DESTINATION, destination);
    	
    	// Add mode of transportation
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_transport_mode);
    	int selected = radioGroup.getCheckedRadioButtonId();
    	RadioButton button = (RadioButton) findViewById(selected);
    	intent.putExtra(TRANSPORT_MODE, button.getTag().toString());
    	
    	startActivity(intent);
    }
    
    /** Gives the current level of the user */
    public void setLevel() {
    	String level = LEVEL + Integer.toString(1);
    	TextView textView = (TextView) findViewById(R.id.level);
    	textView.setText(level);
    }
}
