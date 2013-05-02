package com.example.commutearoute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReportAdvancedFragment extends Fragment {
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
		return v;
	}

}
