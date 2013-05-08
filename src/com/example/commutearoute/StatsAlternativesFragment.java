package com.example.commutearoute;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class StatsAlternativesFragment extends Fragment implements View.OnClickListener {

	private View v;

	public StatsAlternativesFragment() {
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstancedStance) {
		v = inflater.inflate(R.layout.fragment_stats_alternatives,  container, false);

		retrieveData(v);

		Button b = (Button) v.findViewById(R.id.stats_change_btn);
		b.setOnClickListener(this);
		return v;	
	}

	private void retrieveData(View v) {
		// TODO Auto-generated method stub
		TextView vCar = (TextView) v.findViewById(R.id.stats_alt_car);
		TextView vTransit = (TextView) v.findViewById(R.id.stats_alt_transit);
		TextView vBike = (TextView) v.findViewById(R.id.stats_alt_bike);
		TextView vWalk = (TextView) v.findViewById(R.id.stats_alt_walk);
		// get car data
		double carMoney = 35.15;
		int carTime = 50;
		String carString = "Car: $" + Double.toString(carMoney) + ", " + Integer.toString(carTime) + " mins";

		// get transit data
		double transitMoney = 18.50;
		int transitTime = 65;
		String transitString = "Transit: $" + Double.toString(transitMoney) + "0" + ", " + Integer.toString(transitTime) + " mins";

		// get biking data
		double bikeMoney = 0.00;
		int bikeTime = 117;
		String bikeString = "Cycling: $" + Double.toString(bikeMoney)+ "0" + ", " + Integer.toString(bikeTime) + " mins";

		// get walking data
		double walkMoney = 0.00;
		int walkTime = 139;
		String walkString = "Walking: $" + Double.toString(walkMoney) + "0" + ", " + Integer.toString(walkTime) + " mins";
		
		// set text fields
		vCar.setText(carString);
		vTransit.setText(transitString);
		vBike.setText(bikeString);
		vWalk.setText(walkString);
	}

	/**
	 * Handles clicking of "Change" button. Starts new MapActivity with selected mode of transportation.
	 */
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getActivity(), MapActivity.class);

		RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radio_alt_stats);
		int selected = radioGroup.getCheckedRadioButtonId();
		RadioButton button = (RadioButton) v.findViewById(selected);
		intent.putExtra(MainActivity.TRANSPORT_MODE, button.getTag().toString());

		startActivity(intent);
	}
}
