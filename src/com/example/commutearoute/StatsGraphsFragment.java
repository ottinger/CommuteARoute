package com.example.commutearoute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatsGraphsFragment extends Fragment {
	public StatsGraphsFragment() {
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstancedStance) {
		View v = inflater.inflate(R.layout.fragment_stats_graphs,  container, false);

		return v;
	}

}
