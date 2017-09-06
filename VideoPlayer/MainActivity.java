package com.example.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	SimpleCursorAdapter adapter;
	final Uri mediaSrc = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void start(View v) {
		Uri playableUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.krishna);
		
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, PlayerActivity.class);
		intent.setData(playableUri);
		startActivity(intent);
	}

}
