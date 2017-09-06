package com.example.socketclient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ClientActivity extends Activity {

	Context context;
	EditText etClient, etServer;
	
	String SERVER_IP;
	String SERVER_PORT;
	
	private boolean isConnected = false;
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	
	String TAG = "SID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		context = this;
		
		Intent intent = getIntent();
		SERVER_IP = intent.getStringExtra("ip");
		SERVER_PORT = intent.getStringExtra("port");
		
		etClient = (EditText)findViewById(R.id.etClient);
		etServer = (EditText)findViewById(R.id.etServer);
		
		ConnectPhoneTask connectPhoneTask = new ConnectPhoneTask();
		connectPhoneTask.execute(this.SERVER_IP, this.SERVER_PORT); // try to connect to
														// server in another
														// thread
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(!isConnected)
			onDestroy();
	}

	public void onSend(View v)
	{
		new SendReceiveMessage().execute(etClient.getText().toString());
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isConnected && out != null) {
			try {
				out.writeUTF("exit"); // tell server to exit
				socket.close(); // close socket
			} catch (IOException e) {
				Log.e(TAG, "Error in closing socket", e);
			}
		}
	}
	
	public class ConnectPhoneTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = true;
			try {
				InetAddress serverAddr = InetAddress.getByName(params[0]);
				socket = new Socket(serverAddr, Integer.parseInt(params[1]));// Open socket on server IP and port
			} catch (IOException e) {
				Log.e(TAG, "Error while connecting", e);
				result = false;
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			isConnected = result;
			Toast.makeText(
					context,
					isConnected ? "Connected to server!"
							: "Error while connecting", Toast.LENGTH_LONG)
					.show();
			try {
				if (isConnected) {
					out = new DataOutputStream(socket.getOutputStream()); // create output stream to send data to server
					in = new DataInputStream(socket.getInputStream());
				}
			} catch (IOException e) {
				Log.e(TAG, "Error while creating OutWriter", e);
				Toast.makeText(context, "Error while connecting",
						Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public class SendReceiveMessage extends AsyncTask<String,Void,String>
	{

		@Override
		protected String doInBackground(String... params) {
			
			String result = null;
			if (isConnected && out != null) {
				try {
					out.writeUTF(params[0]);
				}catch(Exception e){
					Log.e(TAG, "Error while writing to server", e);
				}
				
				try{
					result = in.readUTF();
				}catch(Exception e)
				{
					Log.e(TAG, "Error while reading from server", e);
				}
			}
			else
			{
				Toast.makeText(context,"There is no connection", Toast.LENGTH_LONG).show();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			etServer.setText(result);
		}
		
	}
}
