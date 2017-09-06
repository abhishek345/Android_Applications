package com.example.socketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText etIP, etPort;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etIP = (EditText)findViewById(R.id.etIP);
		etPort = (EditText)findViewById(R.id.etPort);
		
	}

	public void onConnect(View v) {
		String ip = etIP.getText().toString();
		String port = etPort.getText().toString();
		
		Intent i = new Intent(this,ClientActivity.class);
		i.putExtra("ip", ip);
		i.putExtra("port", port);
		startActivity(i);
	}
}
