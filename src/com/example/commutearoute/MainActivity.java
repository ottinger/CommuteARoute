package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.KeyEvent;
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

		populateUsername();
				
		// TODO: lookup level from past use -- OR from username database
		Integer currentLevel = 1;
		setLevel(currentLevel);
		
	}
	
	/** 
	 * Populate the username field.
	 */
	private void populateUsername() {
		// get the username from sharedpreferences
		SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
		String username = userDetails.getString("username", "");
		
		// create the text view
		TextView textView = (TextView) findViewById(R.id.username_field);
		textView.setText(username);
	}

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
	  /*      case R.id.home:
	        	// app icon in action bar clicked; go home
	        	intent = new Intent(this, MainActivity.class);
	        	startActivity(intent);
	        	return true;
	  */      case R.id.stats:
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
    	Intent intent = new Intent(this, MapActivity.class);
    	
    	// Add destination address
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
    
    /**
     *  Called when user clicks the profile picture, sending them to profile screen
     */
    public void goToProfile(View view) {
    	Intent intent = new Intent(this, ProfileActivity.class);
    	startActivity(intent);
    }
    
    /** Gives the current level of the user */
    public void setLevel(Integer currentLevel) {
    	TextView textView = (TextView) findViewById(R.id.level);
    	textView.setText(Html.fromHtml(LEVEL + "<b>" + Integer.toString(currentLevel)));
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// if user hits back on home screen, prompt them if they want to exit
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		exitWithBack();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    	
    }

	protected void exitWithBack() {
		AlertDialog exitAlert = new AlertDialog.Builder(this)
			.setMessage("Exit CommuteARoute?")
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			})
			.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {					
				}
			})
			.show();
	}
}
