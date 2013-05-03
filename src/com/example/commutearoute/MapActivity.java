package com.example.commutearoute;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// Show the Up button in the action bar.
		setupActionBar();
		/*
		// Get the destination from the intent
		Intent intent = getIntent();
		String destination = intent.getStringExtra(MainActivity.DESTINATION);
		
		// Get the mode of transportation from the intent
		String mode = intent.getStringExtra(MainActivity.TRANSPORT_MODE);
		
		// Create the text view
		TextView textView = (TextView) findViewById(R.id.destination);
		textView.setTextSize(40);
		textView.setText(destination + "\n" + mode);
		*/
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	// Use same options menu as MainActivity
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

}
