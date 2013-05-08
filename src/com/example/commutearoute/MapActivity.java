package com.example.commutearoute;


import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MapActivity extends Activity {

	private static GoogleMap mMap;
	private String report_type;
	private boolean report = false;
	private String destination;
	private String mode;
	private boolean firstEntry = true;
	private LatLng currentLocation;
	private Timer timer = new Timer();

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
		boolean isGPS = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// TODO: update the current provider (probably in MyLocationListener) if it's no longer accessible.
		// (eg: we lose sight of GPS satellites, but can still get a wifi/cell signal)
		if (!isGPS){
			// CHANGED: Use network provider rather than GPS if GPS not functioning. We'll get some
			// sort of location either from the cell tower or based on local wi-fi APs. (In the case of
			// poor GPS reception indoors, no map appears)
			mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 0, mLocListener);
		} else {
			mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, mLocListener);
		}

		// Get start latitude and longitude
		//	double lat = currentLocation.latitude;
		//	double lon = currentLocation.longitude;
		double lon = -76.94518;
		double lat = 38.986008;

		String urlString = null;
		if (mode != null) {
			if (destination != null) {
				if (mode.equals("transit")) {
					// Get time for departure_time field if transit
					Date d = new Date();
					long datems = d.getTime();
					String dateStr = String.valueOf(datems);
					dateStr = dateStr.substring(0,dateStr.length()-3);
					long date = Long.parseLong(dateStr);
					urlString = "http://maps.googleapis.com/maps/api/directions/xml?origin=" +lat + "," + lon  + "&destination=" 
							+ destination.replaceAll("\\s", "") + "&sensor=true&units=imperial" + "&mode=" + mode + "&departure_time=" + date;

				} else {
					urlString = "http://maps.googleapis.com/maps/api/directions/xml?origin=" +lat + "," + lon  + "&destination=" 
							+ destination.replaceAll("\\s","") + "&sensor=true&units=imperial" + "&mode=" + mode;
				}
			}
		}

		if (urlString != null) {
			System.out.println(urlString);
		//	Toast.makeText( getApplicationContext(), urlString, Toast.LENGTH_LONG).show();
			RetrieveDirections task = new RetrieveDirections(this, urlString);
			task.execute();

		}  

	}

	protected void eta() {
		int delay = 0; 
		int period = 10*6000; // repeat every minute.
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerMethod();
			}

		}, delay, period);	
	}

	/**
	 * Called by the timer, calls the CountDownTask in the UI thread
	 */
	private void timerMethod() {
		runOnUiThread(new CountDownTask());

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

				// move camera to current location instantly
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));  

				//This shows marker if reporting advanced
				showMarkers();

			} else {
				// CHANGED: handle case of no location available. I think this only happens if there's no
				// GPS, cell network, or wifi by which we can get coordinates. It shows you're in North
				// Korea if this is the case.
				currentLocation = new LatLng(39,125.75); // hardcoded coordinates for Pyongyang, DPRK
				Toast.makeText( getApplicationContext(), "Cannot find location", Toast.LENGTH_SHORT).show();
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));  
				showMarkers();
			}
		}

		private void showMarkers() {
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
		LocationManager mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();

		// Getting the name of the best provider
		String provider = mLocManager.getBestProvider(criteria, true);
		LocationListener mLocListener = new MyLocationListener();
		boolean isGPS = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// TODO: update the current provider (probably in MyLocationListener) if it's no longer accessible.
		// (eg: we lose sight of GPS satellites, but can still get a wifi/cell signal)
		if (!isGPS){
			// CHANGED: Use network provider rather than GPS if GPS not functioning. We'll get some
			// sort of location either from the cell tower or based on local wi-fi APs. (In the case of
			// poor GPS reception indoors, no map appears)
			mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 0, mLocListener);
		} else {
			mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, mLocListener);
		}
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

		}
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
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
			 intent.putExtra(MainActivity.DESTINATION, destination);
			 // Add mode of transportation
			 intent.putExtra(MainActivity.TRANSPORT_MODE, mode);
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

		/* 
		 * TODO: use lat and long for current location
		 * currently using my home address 
		 */
		/*double lat = 38.982702;
		double lon = -76.932685;
		intent.putExtra(Constants.LAT, lat);
		intent.putExtra(Constants.LONG, lon);

		 */
		// CHANGED: use current location for lat/long
		intent.putExtra(Constants.LAT, currentLocation.latitude);
		intent.putExtra(Constants.LONG, currentLocation.longitude);
		startActivity(intent);
	}

	public class RetrieveDirections extends AsyncTask<String, Void, ArrayList<String>> {

		private Exception exception;
		private String urlString;
		private MapActivity activity;
		public RetrieveDirections(MapActivity activity, String urlString) {
			this.urlString = urlString;
			this.activity = activity;
		}
		protected ArrayList<String> doInBackground(String ... urls) {

			String instr_tag = "html_instructions";
			String duration_tag = "duration";
			String tag = "text";
			ArrayList<String> instr_list = new ArrayList<String>();
			String durationStr = "";
			Integer duration = 0;
			HttpResponse response = null;
			try {
				URL url = new URL(urlString);
				InputStream in = url.openStream();
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = builder.parse(in);
				if (doc != null) {
					NodeList n1, durNodeList;
					n1 = doc.getElementsByTagName(instr_tag);
					durNodeList = doc.getElementsByTagName(duration_tag);
					if (n1.getLength() > 0) {
						for (int i = 0; i < n1.getLength(); i++) {
							Node node = n1.item(i);
							String instruction = node.getTextContent();
							instr_list.add(instruction);
						}
					} else {
						// No points found
					}
					if (durNodeList.getLength() > 0) {
						for (int i = 0; i < durNodeList.getLength(); i++) {
							Node node = durNodeList.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element element = (Element) node;
								durationStr = element.getElementsByTagName(tag).item(0).getTextContent();
								durationStr = durationStr.substring(0,durationStr.indexOf(" "));
								duration += Integer.parseInt(durationStr);

							}
						}
					}
				}
				// Last element in instr_list is the duration
				instr_list.add(duration.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return instr_list;
		}

		protected void onPostExecute(ArrayList<String> directions) {	
			TextView duration = (TextView) findViewById(R.id.duration);
			duration.setText(directions.get(directions.size()-1).replaceAll("<b>", "") + " MINS");

			TextView direction = (TextView) findViewById(R.id.direction1);
			direction.setText(Html.fromHtml(directions.get(0)));
			try {
				activity.eta();
			} catch (IllegalArgumentException iae) {
				System.out.println("Illegal Argument exception");
				iae.printStackTrace();
			} catch (IllegalStateException ise) {
				System.out.println("Illegal State Exception");
				ise.printStackTrace();
			}

		}
	}

	class CountDownTask extends TimerTask {
		public CountDownTask() {

		}

		@Override
		public void run() {
			TextView dView = (TextView) findViewById(R.id.duration);
			String durStr = dView.getText().toString();
			durStr = durStr.substring(0,durStr.indexOf(" "));
			int duration = Integer.parseInt(durStr);
			if (duration > 0) {
				duration -= 1;
			}
			dView.setText(String.valueOf(duration) + " MINS");
		};

	}

}
