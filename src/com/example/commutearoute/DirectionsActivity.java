package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class DirectionsActivity extends Activity {

	private String destination, mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions);
		// Show the Up button in the action bar.
		setupActionBar();

		// Get the destination 
		Intent intent = getIntent();
		String dest = intent.getStringExtra(MainActivity.DESTINATION);
		if (dest != null) {
			destination = dest;
		}
		
		// Get the mode of transportation
		String tempmode = intent.getStringExtra(MainActivity.TRANSPORT_MODE);
		if (tempmode != null) {
			mode = tempmode;
		}
		
		// Populate the heading and directions list fields
		TextView heading = (TextView) findViewById(R.id.direction_heading);
		TextView list = (TextView) findViewById(R.id.direction_list);
		heading.setText("Directions to " + destination + " by " + mode + ":");
		list.setText("1. direction 1\n2. direction 2\n3. direction 3\n4. last direction");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

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
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}



