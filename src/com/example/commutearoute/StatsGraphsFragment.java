package com.example.commutearoute;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class StatsGraphsFragment extends Fragment implements OnItemSelectedListener, View.OnClickListener {
	
	private View v;
	
	public StatsGraphsFragment() {
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstancedStance) {
		v = inflater.inflate(R.layout.fragment_stats_graphs,  container, false);

		Spinner spinner = (Spinner) v.findViewById(R.id.graph_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.graph_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
	
		Button b = (Button) v.findViewById(R.id.stats_change_btn);
		b.setOnClickListener(this);
		return v;	
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
	//	TextView tv = (TextView) v.findViewById(R.id.graph);
	//	tv.setText(parent.getItemAtPosition(pos).toString());
		ImageView iv = (ImageView) v.findViewById(R.id.graph);
		switch (pos) {
		case 0: 
			iv.setBackground(getResources().getDrawable(R.drawable.graphmoneyfinal));
			break;
		case 1: 
			iv.setBackground(getResources().getDrawable(R.drawable.graphtimefinal));
			break;
		case 2:
			iv.setBackground(getResources().getDrawable(R.drawable.graphspeedfinal));
			break;
		case 3:
			iv.setBackground(getResources().getDrawable(R.drawable.graphdistancefinal));
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Handles clicking of "Change" button. Starts new MapActivity with selected mode of transportation.
	 */
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getActivity(), MapActivity.class);

		RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radio_graphs);
		int selected = radioGroup.getCheckedRadioButtonId();
		RadioButton button = (RadioButton) v.findViewById(selected);
		intent.putExtra(MainActivity.TRANSPORT_MODE, button.getTag().toString());

		SharedPreferences pref = this.getActivity().getSharedPreferences(Constants.USER_DETAILS, getActivity().MODE_PRIVATE);
		String dest = pref.getString("work", "");	
		intent.putExtra(MainActivity.DESTINATION, dest);
		startActivity(intent);
	}
	
	

}
