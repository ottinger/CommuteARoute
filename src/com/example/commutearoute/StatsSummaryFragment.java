package com.example.commutearoute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
		double money = 149.99;
		// get commute time
		int time = 91;
		// get speed
		int speed = 55;
		// get distance
		double distance = 10;
		String dataString = "";
		dataString += "Total money spent: $" 
				+ Double.toString(money) + "\n"
				+ "Average commute time: " 
				+ Integer.toString(time) + " min\n"
				+ "Average speed: " 
				+ Integer.toString(speed) + " mph\n"
				+ "Average distance: " 
				+ Double.toString(distance) + " miles";
		data.setText(dataString);
				
	}
	
}
