package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ProfileActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_profile);

		// get the user details from sharedpreferences
		SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
		String username = userDetails.getString("username", "");
		TextView uView = (TextView) findViewById(R.id.prof_username);
		uView.setText(username);

		String home = userDetails.getString("home", "");
		TextView hView = (TextView) findViewById(R.id.prof_home_field); 
		hView.setText(home);

		String work = userDetails.getString("work", "");
		TextView wView = (TextView) findViewById(R.id.prof_work_field);
		wView.setText(work);

		String car_make = userDetails.getString("carmake", "");
		TextView makeView = (TextView) findViewById(R.id.prof_car_make_field);
		makeView.setText(car_make);

		String car_model = userDetails.getString("carmodel", "");
		TextView modelView = (TextView) findViewById(R.id.prof_car_model_field);
		modelView.setText(car_model);

		Button btn_home = (Button) findViewById(R.id.change_home);
		Button btn_work = (Button) findViewById(R.id.change_work);
		Button btn_car_make = (Button) findViewById(R.id.change_car_make);
		Button btn_car_model = (Button) findViewById(R.id.change_car_model);
		btn_home.setOnClickListener(this);
		btn_work.setOnClickListener(this);
		btn_car_make.setOnClickListener(this);
		btn_car_model.setOnClickListener(this);


		// Show the Up button in the action bar.
		setupActionBar();
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Get the layout inflater
		LayoutInflater inflater = getLayoutInflater();

		// Add action buttons
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		}); 

		switch (v.getId()) {
		case R.id.change_home:
			final View home_layout = inflater.inflate(R.layout.dialog_home, null);
			builder.setView(home_layout);
			builder.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
					SharedPreferences.Editor editor = userDetails.edit();
					EditText newHomeView = (EditText) home_layout.findViewById(R.id.new_home);
					String newHome = newHomeView.getText().toString();
					if (!newHome.isEmpty()) {
						editor.putString("home",newHome);
						editor.commit();
						finish();
						startActivity(getIntent());
					}
				}
			});
			builder.show();
			break;
		case R.id.change_work:
			final View work_layout = inflater.inflate(R.layout.dialog_work, null);
			builder.setView(work_layout);
			builder.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
					SharedPreferences.Editor editor = userDetails.edit();
					EditText newWorkView = (EditText) work_layout.findViewById(R.id.new_work);
					String newWork = newWorkView.getText().toString().trim();
					if (!newWork.isEmpty()) {
						editor.putString("work", newWork);
						editor.commit();
						finish();
						startActivity(getIntent());
					}
				}
			});		
			builder.show();
			break;
		case R.id.change_car_make:
			final View make_layout = inflater.inflate(R.layout.dialog_car_make, null);
			builder.setView(make_layout);
			builder.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
					SharedPreferences.Editor editor = userDetails.edit();
					EditText newCarMakeView = (EditText) make_layout.findViewById(R.id.new_car_make);
					String newCarMake = newCarMakeView.getText().toString().trim();
					if (!newCarMake.isEmpty()) {
						editor.putString("carmake", newCarMake);
						editor.commit();
						finish();
						startActivity(getIntent());
					}
				}
			});
			builder.show();
			break;
		case R.id.change_car_model:
			final View model_layout = inflater.inflate(R.layout.dialog_car_model, null);
			builder.setView(model_layout);
			builder.setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
					SharedPreferences.Editor editor = userDetails.edit();
					EditText newCarModelView = (EditText) model_layout.findViewById(R.id.new_car_model);
					String newCarModel = newCarModelView.getText().toString().trim();
					if (!newCarModel.isEmpty()) {
						editor.putString("carmodel", newCarModel);
						editor.commit();
						finish();
						startActivity(getIntent());
					}
				}
			});	
			builder.show();
			break;
		default:
			break;
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		// Handle item selection
	    switch (item.getItemId()) {
	  /*      case R.id.home:
	        	// app icon in action bar clicked; go home
	        	intent = new Intent(this, MainActivity.class);
	        	startActivity(intent);
	        	return true;
	  */      case R.id.stats:
	        	// stats icon clicked; go to stats page
	        	intent = new Intent(this, StatsActivity.class);
	            startActivity(intent);
	            return true;
	        case R.id.report:
	        	// report button clicked; go to report page
	        	intent = new Intent(this, ReportActivity.class);
	        	startActivity(intent);
	        	return true;
	        case R.id.map:
	        	// show map button clicked; go to map
	        	intent = new Intent(this, MapActivity.class);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
