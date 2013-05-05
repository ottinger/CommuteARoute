package com.example.commutearoute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReportAdvancedFragment extends Fragment implements View.OnClickListener {
	
	public final static String REPORT_TYPE = "com.example.commutearoute.REPORT_TYPE";

	public ReportAdvancedFragment() {
        setRetainInstance(true);
	}

	/**
	 * TODO: create button listeners that bring up map screen to pinpoint the report
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_report_advanced,
				container, false);
		
		// make buttons clickable. Will send you to map page with placemark.
		ImageButton pothole = (ImageButton) v.findViewById(R.id.btn_pothole);
		ImageButton badRoad = (ImageButton) v.findViewById(R.id.btn_bad_road);
		ImageButton construction = (ImageButton) v.findViewById(R.id.btn_construction);
		pothole.setOnClickListener(this);
		badRoad.setOnClickListener(this);
		construction.setOnClickListener(this);
		
		
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), MapActivity.class);
		ReportAdvancedFragment.this.startActivity(intent);
		
		// Add type of report 
		String type = "";
		switch (v.getId()) {
		case R.id.btn_pothole: 
			type = "Pothole";
			break;
		case R.id.btn_bad_road:
			type = "Bad Road";
			break;
		case R.id.btn_construction:
			type = "Construction";
			break;
		default:
			break;
		}
		intent.putExtra(REPORT_TYPE, type);
	}
	
	

}
