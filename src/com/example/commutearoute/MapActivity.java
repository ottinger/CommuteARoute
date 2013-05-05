package com.example.commutearoute;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MapActivity extends Activity {

	private GoogleMap mMap;
	private String report_type;
	private boolean report = false;
	private String destination;
	private String mode;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Get the destination if coming from Home screen 'Go' button
		Intent hIntent = getIntent();
		String dest = hIntent.getStringExtra(MainActivity.DESTINATION);
		if (dest != null) {
			destination = dest;
		}
		
		// Get the mode of transportation
		String tempmode = hIntent.getStringExtra(MainActivity.TRANSPORT_MODE);
		if (tempmode != null) {
			mode = tempmode;
		}
		
		if (mode.equals("Transit")) {
			ImageButton btn = (ImageButton) findViewById(R.id.btn_directions);
			btn.setBackground(getResources().getDrawable(R.drawable.directions_transit));
		}

		// check intent to see if coming from report
		Intent rIntent = getIntent();
		String type = rIntent.getStringExtra(ReportAdvancedFragment.REPORT_TYPE);
		if (type != null && !type.isEmpty()) {
			report_type = type;
			report = true;
		}
		
		setUpMapIfNeeded();
		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		// Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = mLocManager.getBestProvider(criteria, true);
		LocationListener mLocListener = new MyLocationListener();
		boolean isGPS = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!isGPS){
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			sendBroadcast(intent);
		}
		mLocManager.requestLocationUpdates(provider, 20000, 0, mLocListener);

	}

	/**
	 * MyLocationListener class will give the current GPS location 
	 */
	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {

			if (location != null) {
				// ---Get current location latitude, longitude
				LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
				
				//This shows marker if reporting advanced
				if (report) {
					mMap.addMarker(new MarkerOptions().position(currentLocation).title(report_type));
				}
				// move camera to current location instantly
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));         
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
	         Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
	         Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
			mMap.getUiSettings().setMyLocationButtonEnabled(true);
		}
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
	
	public void goToDirections(View v) {
		Intent intent = new Intent(this, DirectionsActivity.class);
		intent.putExtra(MainActivity.DESTINATION, destination);
		intent.putExtra(MainActivity.TRANSPORT_MODE, mode);
		startActivity(intent);
	}

}
