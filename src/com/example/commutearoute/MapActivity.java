package com.example.commutearoute;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MapActivity extends Activity {

	private static GoogleMap mMap;
	private String report_type;
	private boolean report = false;
	private String destination;
	private String mode;
	private boolean firstEntry = true;
	// Make currentLocation accessible by all methods
	private LatLng currentLocation;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// Show the Up button in the action bar.
		setupActionBar();

		// Get the destination if coming from Home screen 'Go' button
		Intent intent = getIntent();
		String dest = intent.getStringExtra(MainActivity.DESTINATION);
		if (dest != null) {
			destination = dest;
		}

		// Get the mode of transportation
		String tempmode = intent.getStringExtra(MainActivity.TRANSPORT_MODE);
		if (tempmode != null) {
			mode = tempmode;
			if (mode.equals("transit")) {
				ImageButton btn = (ImageButton) findViewById(R.id.btn_directions);
				btn.setBackground(getResources().getDrawable(R.drawable.directions_transit));
			}
		}

		// check intent to see if coming from report
		String type = intent.getStringExtra(ReportActivity.REPORT_TYPE);
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

		// TODO: update the current provider (probably in MyLocationListener) if it's no longer accessible.
		// (eg: we lose sight of GPS satellites, but can still get a wifi/cell signal)
		mLocManager.requestLocationUpdates(provider, 2000, 0, mLocListener);
		currentLocation = new LatLng(39,-77);
	}

	/**
	 * MyLocationListener class will give the current GPS location 
	 */
	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {

			if (location != null) {
				// ---Get current location latitude, longitude
				// CHANGED: made currentLocation accessible by all methods (eg making the directions intent)
				currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
				Toast.makeText( getApplicationContext(), currentLocation.toString(), Toast.LENGTH_SHORT).show(); // DEBUG
				// move camera to current location instantly
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));  

				//This shows marker if reporting advanced
				if (report && firstEntry) {
					firstEntry = false;
					Marker marker;
					if (report_type.equals("Pothole")) {
						marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title(report_type)
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pothole))
								.draggable(true));
					} else if (report_type.equals("Bus Overcrowded")){
						marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title(report_type)
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bus_overcrowded)));
					} else if (report_type.equals("Late Bus")){
						marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title(report_type)
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_late)));
					} else if (report_type.equals("Accident")) {
						marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title(report_type)
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_accident)));
					} else {
						marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title(report_type));
					}
				}

			} else {
				// CHANGED: handle case of no location available. I think this only happens if there's no
				// GPS, cell network, or wifi by which we can get coordinates. It shows you're in North
				// Korea if this is the case.
				currentLocation = new LatLng(39,125.75); // hardcoded coordinates for Pyongyang, DPRK
				Toast.makeText( getApplicationContext(), "Cannot find location", Toast.LENGTH_SHORT).show();
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
		Toast.makeText( getApplicationContext(), mode, Toast.LENGTH_SHORT).show(); // DEBUG
		
		// CHANGED: use current location for lat/long
		intent.putExtra(Constants.LAT, currentLocation.latitude);
		intent.putExtra(Constants.LONG, currentLocation.longitude);
		startActivity(intent);
	}

}
