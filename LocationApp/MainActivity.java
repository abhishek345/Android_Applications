package com.example.locationapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnShowLocation;

	// GPSTracker class
	GPSTracker gps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
	}

	public void onClick(View arg0) {
		// create class object
		gps = new GPSTracker(MainActivity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			// \n is for new line
			//Toast.makeText(getApplicationContext(),"Your Location is - \nLat: " + latitude + "\nLong: "
			//				+ longitude, Toast.LENGTH_LONG).show();
			
			TextView lat = (TextView)findViewById(R.id.latitude);
			TextView lon = (TextView)findViewById(R.id.longitude);
			
			lat.setText(""+latitude);
			lon.setText(""+longitude);
			
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}

}
