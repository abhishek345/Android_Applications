package com.example.sqlite;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//DatabaseHandler db = new DatabaseHandler(this);
		 
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        /*Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
 
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();       
 
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
        Log.d("Name: ", log);
    }*/
	}

	public void onClick(View v)
	{
		if (v.getId() == R.id.insertButton)
		{
			Intent i = new Intent(this, FillFormActivity.class);
			startActivity(i);
		}
		else if(v.getId() == R.id.retrieveButton)
		{
			Intent i = new Intent(this, GetContactDetailsActivity.class);
			startActivity(i);
		}
		else
		{
			Log.d("SID :","Invalid button pressed");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
