package com.example.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class FillFormActivity extends Activity {

	EditText nameET, numberET;
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_form);
		
		nameET = (EditText)findViewById(R.id.editName);
		numberET = (EditText)findViewById(R.id.editNumber);
	}

	public void onClickAdd(View v)
	{
		db = new DatabaseHandler(this);
		
		String name = nameET.getText().toString();
		String number = numberET.getText().toString();
		db.addContact(new Contact(name, number));
		nameET.setText("");
		numberET.setText("");
	}
	
	public void onClickBack(View v)
	{
		db.close();
		finish();
	}
}
