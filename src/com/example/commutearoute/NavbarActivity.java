package com.example.commutearoute;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class NavbarActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
    }
      /*  RadioButton radioButton;
        radioButton = (RadioButton) findViewById(R.id..home_button);
        radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
        radioButton = (RadioButton) findViewById(R.id.btnPicture);
        radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
        radioButton = (RadioButton) findViewById(R.id.btnVideo);
        radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
        radioButton = (RadioButton) findViewById(R.id.btnFile);
        radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
        radioButton = (RadioButton) findViewById(R.id.btnMore);
        radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
    }
*/
    private CompoundButton.OnCheckedChangeListener btnNavBarOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(NavbarActivity.this, buttonView.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    };


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navbar, menu);
		return true;
	}
	
}
