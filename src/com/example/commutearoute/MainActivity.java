package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String DESTINATION = "com.example.commutearoute.DESTINATION";
	public final static String TRANSPORT_MODE = "com.example.commutearoute.TRANSPORT_MODE";
	public final static String LEVEL = "LEVEL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		/* For first time user (or until they click "Do not show again",	
		 * give tutorial with dialog boxes of what to do on main screen.
		 */
		SharedPreferences tutorialPref = getSharedPreferences(Constants.TUTORIAL, MODE_PRIVATE);
		boolean tutorial = tutorialPref.getBoolean(Constants.TUTORIAL, true);
		if (tutorial) {
			startTutorial();
		}

		populateUsername();

		// Set up "End" field with work address
		populateEnd();

		// TODO: lookup level from past use -- OR from SharedPreferences
		Integer currentLevel = 1;
		setLevel(currentLevel);

	}

	private void populateEnd() {
		EditText end = (EditText) findViewById(R.id.edit_end);
		SharedPreferences pref = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
		String work = pref.getString("work", "");
		end.setHint(work);
	}

	/**
	 * Tutorial goes through several steps for initial users.
	 * 1. CommuteARoute helps you find the best route to work and home. Enter your 
	 *    home and work address. [Skip Tutorial | Continue]. Skip brings up 'Do not show again'. 
	 *    Continue brings you to home page and step 2.
	 * 2. Manage your profile by clicking here, highlighting profile pic. [Skip Tutorial | Continue]
	 * 3. Check your commuting stats here, highlighting stats button. [Skip Tutorial | Continue]
	 * 4. Report a traffic incident here, highlighting report button. [Skip Tutorial | Continue]
	 * 5. View map here, highlighting map button. [Done]. Done brings up 'Do not show again'.
	 */
	private void startTutorial() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.dialog_tutorial, null);
		dialog.setView(layout);
		dialog.setMessage("CommuteARoute helps you find the best route to work and home." +
				" Enter your home and work address.")
				.setPositiveButton(R.string.skip, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startDoNotShowTutorialDialog();
					}
				})
				.setNegativeButton(R.string.cont, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {	
						SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
						SharedPreferences.Editor editor = userDetails.edit();
						EditText hView = (EditText) layout.findViewById(R.id.tut_home);
						String home = hView.getText().toString();
						if (!home.isEmpty()) {
							editor.putString("home",home);
						}
						EditText wView = (EditText) layout.findViewById(R.id.tut_work);
						String work = wView.getText().toString();
						if (!work.isEmpty()) {
							editor.putString("work", work);
						}
						editor.commit();
						startProfileDialog();
					}
				});
		dialog.show();
	}

	protected void startDoNotShowTutorialDialog() {
		// TODO Auto-generated method stub
		AlertDialog skipDialog = new AlertDialog.Builder(this)
		.setMessage("Never show tutorial again?") 
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferences tutorialPref = getSharedPreferences(Constants.TUTORIAL, MODE_PRIVATE);
				SharedPreferences.Editor editor = tutorialPref.edit();
				editor.putBoolean(Constants.TUTORIAL, false);
				editor.commit();
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {				
			}
		}).show();
	}

	protected void startProfileDialog() {
		final ImageView v = (ImageView) findViewById(R.id.prof_circle);

		AlertDialog dialog = new AlertDialog.Builder(this)
		.setMessage("Manage your profile by clicking here.")
		.setPositiveButton(R.string.skip, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startDoNotShowTutorialDialog();
			}
		})
		.setNegativeButton(R.string.cont, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startStatsDialog();
			}
		}).show(); 

		v.setVisibility(v.VISIBLE);
	}

	protected void startStatsDialog() {
		final ImageView v = (ImageView) findViewById(R.id.arrow1);

		AlertDialog dialog = new AlertDialog.Builder(this)
		.setMessage("Check your commuting stats here.")
		.setPositiveButton(R.string.skip, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startDoNotShowTutorialDialog();
			}
		})
		.setNegativeButton(R.string.cont, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startReportDialog();
			}
		}).show(); 
		v.setVisibility(v.VISIBLE);

	}

	protected void startReportDialog() {
		final ImageView v = (ImageView) findViewById(R.id.arrow2);

		AlertDialog dialog = new AlertDialog.Builder(this)
		.setMessage("Report a traffic incident here.")
		.setPositiveButton(R.string.skip, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startDoNotShowTutorialDialog();
			}
		})
		.setNegativeButton(R.string.cont, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startMapDialog();
			}
		}).show(); 
		v.setVisibility(v.VISIBLE);
	}

	protected void startMapDialog() {
		final ImageView v = (ImageView) findViewById(R.id.arrow3);

		AlertDialog dialog = new AlertDialog.Builder(this)
		.setMessage("View map here.")
		.setPositiveButton(R.string.skip, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startDoNotShowTutorialDialog();
			}
		})
		.setNegativeButton(R.string.cont, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				v.setVisibility(v.INVISIBLE);
				startDoNotShowTutorialDialog();
			}
		}).show(); 
		v.setVisibility(v.VISIBLE);
	}
	/** 
	 * Populate the username field.
	 */
	private void populateUsername() {
		// get the username from sharedpreferences
		SharedPreferences userDetails = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
		String username = userDetails.getString("username", "");

		// create the text view
		TextView textView = (TextView) findViewById(R.id.username_field);
		textView.setText(username);
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

	/** Called when user clicks the Go button */
	public void goToMap(View view) {
		Intent intent = new Intent(this, MapActivity.class);

		// Add destination address
		EditText editText = (EditText) findViewById(R.id.edit_end);
		String destination = editText.getText().toString();
		if (destination.isEmpty()) {
			destination = editText.getHint().toString();
		}
		intent.putExtra(DESTINATION, destination);

		// Add mode of transportation
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_transport_mode);
		int selected = radioGroup.getCheckedRadioButtonId();
		RadioButton button = (RadioButton) findViewById(selected);
		intent.putExtra(TRANSPORT_MODE, button.getTag().toString());

		startActivity(intent);
	}

	/**
	 *  Called when user clicks the profile picture, sending them to profile screen
	 */
	public void goToProfile(View view) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}

	/** Gives the current level of the user */
	public void setLevel(Integer currentLevel) {
		TextView textView = (TextView) findViewById(R.id.level);
		textView.setText(Html.fromHtml(LEVEL + "<b>" + Integer.toString(currentLevel)));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if user hits back on home screen, prompt them if they want to exit
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitWithBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	protected void exitWithBack() {
		AlertDialog exitAlert = new AlertDialog.Builder(this)
		.setMessage("Exit CommuteARoute?")
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {					
			}
		})
		.show();
	}
}
