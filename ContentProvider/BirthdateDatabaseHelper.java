package com.example.contentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BirthdateDatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "BirthdayReminder";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "BirthdateInfo";
	private final String CREATE_TABLE_BIRTHDATE = 
		      " CREATE TABLE " + TABLE_NAME +
		      " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
		      " name TEXT NOT NULL, " +
		      " birthdate TEXT NOT NULL);";
	
	public BirthdateDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.d("SID:","in onCreate() of "+this.getClass().getSimpleName());
		db.execSQL(CREATE_TABLE_BIRTHDATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("SID:","in onUpgrade() of "+this.getClass().getSimpleName());
	}

}
