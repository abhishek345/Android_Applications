package com.example.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GetContactDetailsActivity extends Activity {

	EditText ID;
	TextView details;
	
	DatabaseHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_contact_details);
		ID = (EditText)findViewById(R.id.editID);
		details = (TextView)findViewById(R.id.textDetails);
	}
	
	public void onClickGet(View v)
	{
		db = new DatabaseHandler(this);
		Contact contact = db.getContact(Integer.parseInt(ID.getText().toString()));
		details.setText("ID : "+contact.getID()+"\nName : "+contact.getName()+"\nPhone Number : "+contact.getPhoneNumber());	
	}

	public void onClickBack(View v)
	{
		db.close();
		finish();
	}
}
