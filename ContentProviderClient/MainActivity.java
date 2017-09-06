package com.example.contentproviderclient;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentproviderdemo.BirthdateContract;

public class MainActivity extends Activity {

	EditText etName, etBirthdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etName = (EditText)findViewById(R.id.etName);
		etBirthdate = (EditText)findViewById(R.id.etBirthdate);
	}

	public void onClickAdd(View v)
	{
		
	      ContentValues values = new ContentValues();

	      values.put(BirthdateContract.BirthdateInfo.NAME, etName.getText().toString());
	      values.put(BirthdateContract.BirthdateInfo.BIRTHDATE, etBirthdate.getText().toString());

	      Uri uri = getContentResolver().insert(BirthdateContract.BirthdateInfo.CONTENT_URI, values);
	      if(uri==null)
	      {
	    	  Log.d("SID:","uri is null");
	      }
	      else
	      {
	    	  Toast.makeText(this, uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
	    	  etName.setText("");
	    	  etBirthdate.setText("");
	      }
	      
	}
	
	public void onClickGet(View v)
	{
		String name = etName.getText().toString();
		
		Cursor cursor = getContentResolver().query(BirthdateContract.BirthdateInfo.CONTENT_URI, null,
													BirthdateContract.BirthdateInfo.NAME+" LIKE ?", 
													new String[] {name},
													null);
		if(cursor.moveToFirst())
		{
			etBirthdate.setText(cursor.getString(2));
		}
	}
}
