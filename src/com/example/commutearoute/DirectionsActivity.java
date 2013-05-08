package com.example.commutearoute;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.format.DateFormat;

public class DirectionsActivity extends Activity {

	private String destination = "";
	private String mode = "";

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

		// Get start latitude and longitude
		double lat = intent.getDoubleExtra(Constants.LAT, 0);
		double lon = intent.getDoubleExtra(Constants.LONG, 0);

		String urlString = "";
		if (mode.equals("transit")) {
			// Get time for departure_time field if transit
			Date d = new Date();
			long datems = d.getTime();
			String dateStr = String.valueOf(datems);
			dateStr = dateStr.substring(0,dateStr.length()-3);
			long date = Long.parseLong(dateStr);
			urlString += "http://maps.googleapis.com/maps/api/directions/xml?origin=" +lat + "," + lon  + "&destination=" 
					+ destination.replaceAll("\\s", "") + "&sensor=true&units=imperial" + "&mode=" + mode + "&departure_time=" + date;
		} else {
			urlString += "http://maps.googleapis.com/maps/api/directions/xml?origin=" +lat + "," + lon  + "&destination=" 
					+ destination.replaceAll("\\s","") + "&sensor=true&units=imperial" + "&mode=" + mode;
		}
		System.out.println(urlString);
		new RetrieveDirections(this, urlString).execute(); 

	}

	private class RetrieveDirections extends AsyncTask<String, Void, ArrayList<String>> {

		private Exception exception;
		private String urlString;
		private DirectionsActivity activity;
		public RetrieveDirections(DirectionsActivity activity, String urlString) {
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
			TextView heading = (TextView) findViewById(R.id.direction_heading);
			TextView listView = (TextView) findViewById(R.id.direction_list);
			heading.setText("Directions to " + destination + " by " + mode + ":");
			String list = "";
			for (int i = 0 ; i < directions.size()-1; i++) {
				list += (i+1) + ". " + directions.get(i) + "<br>";
			}
		//	list += "Duration: " + directions.get(directions.size()-1) + " mins";
			// TODO: figure out how to put in spaces with this dumb html
			listView.setText(Html.fromHtml(list));
		}
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
		Intent intent;
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
        	// app icon in action bar clicked; go home
        	intent = new Intent(this, MapActivity.class);
        	intent.putExtra(MainActivity.DESTINATION, destination);
			// Add mode of transportation
			intent.putExtra(MainActivity.TRANSPORT_MODE, mode);
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
			intent.putExtra(MainActivity.DESTINATION, destination);
			// Add mode of transportation
			intent.putExtra(MainActivity.TRANSPORT_MODE, mode);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}



