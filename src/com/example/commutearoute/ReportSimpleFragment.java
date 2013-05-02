package com.example.commutearoute;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This class is a fragment for the Report -> Simple tab.
 */
public class ReportSimpleFragment extends Fragment{

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
		return v;
	}
}

