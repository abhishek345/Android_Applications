package com.example.accelerometerdemo;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class AccelerometerService extends Service{

	private Sensor sensor;
	private SensorManager sensorManager;
	private List<Sensor> sensors;
	/** indicates whether or not Accelerometer Sensor is supported */
	private boolean supported;
	/** indicates whether or not Accelerometer Sensor is running */
	private boolean running = false;
	private int noShakes;
	private Context aContext;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		aContext = this;
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// Get all sensors in device
		sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		supported = sensors.size() > 0;
		
		if (supported && sensors.size() > 0) {

			sensor = sensors.get(0);

			// Register Accelerometer Listener
			running = sensorManager.registerListener(sensorEventListener,sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		Toast.makeText(aContext, "Service Started", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("Sensor", "Service  distroy");

		// Check device supported Accelerometer senssor or not
		if (running) {

			// Stop Accelerometer Listening
			running = false;
			try {
				if (sensorManager != null && sensorEventListener != null) {
					sensorManager.unregisterListener(sensorEventListener);
				}
			} catch (Exception e) {
			}
		}
		Toast.makeText(aContext, "Service Stopped", Toast.LENGTH_LONG).show();
	}
	
	public void onShake(float force) {

		// Do your stuff here
		noShakes++;

		// Called when 3 Motion Detected
		if (noShakes == 2) {
			// Toast.makeText(getBaseContext(), "Motion detected",
			// Toast.LENGTH_SHORT).show();
			noShakes = 0;
			Log.d("SID", "Motion Detected");
			
			PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
			if(pm.isScreenOn())
			{
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(ContactsContract.Contacts.CONTENT_URI);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		}

		Log.d("SID", "noShakes :" + noShakes);
	}
	
	/**
	 * The listener that listen to events from the accelerometer listener
	 */
	private SensorEventListener sensorEventListener = new SensorEventListener() {
		private long now = 0;
		private long timeDiff = 0;
		private long lastUpdate = 0;
		private long lastShake = 0;

		private float x = 0;
		private float y = 0;
		private float z = 0;
		private float lastX = 0;
		private float lastY = 0;
		private float lastZ = 0;
		private float force = 0;

		/** Accuracy configuration */
		private float threshold = 25.0f;
		private int interval = 200;

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			// use the event timestamp as reference
			// so the manager precision won't depends
			// on the AccelerometerListener implementation
			// processing time
			now = event.timestamp;

			x = event.values[0];
			y = event.values[1];
			z = event.values[2];

			// if not interesting in shake events
			// just remove the whole if then else block
			if (lastUpdate == 0) {
				lastUpdate = now;
				lastShake = now;
				lastX = x;
				lastY = y;
				lastZ = z;
				//Toast.makeText(aContext, "No Motion detected",
					//	Toast.LENGTH_SHORT).show();

			} else {
				timeDiff = now - lastUpdate;

				if (timeDiff > 0) {

					/*
					 * force = Math.abs(x + y + z - lastX - lastY - lastZ) /
					 * timeDiff;
					 */
					force = Math.abs(x + y + z - lastX - lastY - lastZ);

					if (Float.compare(force, threshold) > 0) {
						// Toast.makeText(Accelerometer.getContext(),
						// (now-lastShake)+"  >= "+interval, 1000).show();

						if (now - lastShake >= interval) {

							// trigger shake event
							onShake(force);
						} else {
							//Toast.makeText(aContext, "No Motion detected",
								//	Toast.LENGTH_SHORT).show();

						}
						lastShake = now;
					}
					lastX = x;
					lastY = y;
					lastZ = z;
					lastUpdate = now;
				} else {
					//Toast.makeText(aContext, "No Motion detected",
						//	Toast.LENGTH_SHORT).show();

				}
			}
		}

	};
	

}
