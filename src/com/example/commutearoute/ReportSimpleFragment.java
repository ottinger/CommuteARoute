package com.example.commutearoute;

import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This class is a fragment for the Report -> Simple tab.
 */
public class ReportSimpleFragment extends Fragment implements View.OnClickListener {

	public ReportSimpleFragment() {
		setRetainInstance(true);
	}


	/**
	 * TODO: create button listeners that bring up confirmation popup
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_report_simple,
				container, false);

		// make buttons clickable. Will send you to map page with placemark.
		ImageButton accident = (ImageButton) v.findViewById(R.id.btn_accident);
		ImageButton lateBus = (ImageButton) v.findViewById(R.id.btn_late);
		ImageButton overcrowded = (ImageButton) v.findViewById(R.id.btn_overcrowded);
		accident.setOnClickListener(this);
		lateBus.setOnClickListener(this);
		overcrowded.setOnClickListener(this);
		return v;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final int id = v.getId();
		AlertDialog confirmAlert = new AlertDialog.Builder(getActivity())
		.setMessage("Report the incident?")
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getActivity(), MapActivity.class);
				
				// Add type of report 
				String type = "";
				switch (id) {
				case R.id.btn_accident: 
					type = "Accident";
					break;
				case R.id.btn_late:
					type = "Late Bus";
					break;
				case R.id.btn_overcrowded:
					type = "Bus Overcrowded";
					break;
				default:
					break;
				}
				intent.putExtra(ReportActivity.REPORT_TYPE, type);
				intent.putExtra(MainActivity.DESTINATION, "");
				intent.putExtra(MainActivity.TRANSPORT_MODE, "");
				ReportSimpleFragment.this.startActivity(intent);
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {					
			}
		})
		.show();
	}
}

