package com.example.commutearoute;

import android.content.ClipData.Item;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class StatsGraphsFragment extends Fragment implements OnItemSelectedListener {
	
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
		
		return v;	
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) v.findViewById(R.id.graph);
		tv.setText(parent.getItemAtPosition(pos).toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
