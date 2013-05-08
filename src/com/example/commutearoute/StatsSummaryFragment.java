package com.example.commutearoute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsSummaryFragment extends Fragment {
	
	public StatsSummaryFragment() {
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstancedStance) {
		View v = inflater.inflate(R.layout.fragment_stats_summary,  container, false);
		
		retrieveData(v);
		return v;
	}

	private void retrieveData(View v) {
		// TODO Auto-generated method stub
		TextView data = (TextView) v.findViewById(R.id.stats_data);
		// get money spent
		double money = 37.99;
		// get commute time
		int time = 45;		
		// get speed
		int speed = 58;
		// get distance
		double distance = 19.5;
		String dataString = "";
		dataString += "<b>Total money spent:</b> $" 
				+ Double.toString(money) + "<br>"
				+ "<b>Average commute time:</b> " 
				+ Integer.toString(time) + " min<br>"
				+ "<b>Average speed:</b> " 
				+ Integer.toString(speed) + " mph<br>"
				+ "<b>Average distance:</b> " 
				+ Double.toString(distance) + " miles";
		data.setText(Html.fromHtml(dataString));
				
	}
	
}
