package com.example.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class BirthdateProvider extends ContentProvider {

	private static final String BASE_URI = "com.example.contentproviderdemo.provider";
	private static final String TABLE_NAME = "BirthdateInfo";

	// fields for the database table
	private static final String _ID = "_id";
	private static final String NAME = "name";
	// private static final String BIRTHDATE = "birthdate";

	// integer values used in content URI
	private static final int FRIENDS = 1;
	private static final int FRIENDS_ID = 2;

	private BirthdateDatabaseHelper dbHelper;
	// private SQLiteDatabase database;

	// maps content URI "patterns" to the integer values that were set above
	static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(BASE_URI, TABLE_NAME, FRIENDS);
		uriMatcher.addURI(BASE_URI, TABLE_NAME + "/#", FRIENDS_ID);
	}

	@Override
	public boolean onCreate() {
		
		Log.d("SID:","in onCreate() of "+this.getClass().getSimpleName());
		
		Context context = getContext();
		dbHelper = new BirthdateDatabaseHelper(context);

		return true;
	}

	@Override
	public synchronized Cursor query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		Log.d("SID:","About to get DB in query()");
		
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		// maps all database column names
		case FRIENDS:
			if (sortOrder == null || sortOrder == "") {
				sortOrder = NAME + " ASC";
			}
			break;

		case FRIENDS_ID:
			selection = selection + _ID + " = " + uri.getLastPathSegment();
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		Cursor cursor = database.query(TABLE_NAME, projection, selection,
				selectionArgs, null, null, sortOrder);

		return cursor;
	}

	@Override
	public synchronized Uri insert(Uri uri, ContentValues values) {
		Log.d("SID:","About to get DB in insert()");
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		long row = database.insert(TABLE_NAME, "", values);

		// If record is added successfully
		if (row > 0) {
			
			//Log.d("SID:","row  ID = "+row);
			
			Uri newUri = ContentUris.withAppendedId(BirthdateContract.BirthdateInfo.CONTENT_URI, row);
			
			/*if(newUri!=null)
				Log.d("SID:","in insert(), URI = "+newUri.toString());
			else
				Log.d("SID:","in insert(), newUri is null");*/
			
			return newUri;
		}
		throw new SQLException("Fail to add a new record into " + uri);
	}

	@Override
	public synchronized int update(Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {
		Log.d("SID:","About to get DB in update()");
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		int count = 0;

		switch (uriMatcher.match(uri)) {
		case FRIENDS:
			count = database.update(TABLE_NAME, values, selection,
					selectionArgs);
			break;

		case FRIENDS_ID:
			count = database.update(
					TABLE_NAME,
					values,
					_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI " + uri);
		}
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d("SID:","About to get DB in delete()");
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		int count = 0;

		switch (uriMatcher.match(uri)) {
		case FRIENDS:
			// delete all the records of the table
			count = database.delete(TABLE_NAME, selection, selectionArgs);
			break;

		case FRIENDS_ID:
			String id = uri.getLastPathSegment(); // gets the id
			count = database.delete(TABLE_NAME,
					_ID
							+ " = "
							+ id
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI " + uri);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {

		switch (uriMatcher.match(uri)) {
		// Get all friend-birthday records
		case FRIENDS:
			return "vnd.android.cursor.dir/vnd.com.example.contentproviderdemo.birthdateinfo";
			// Get a particular friend
		case FRIENDS_ID:
			return "vnd.android.cursor.item/vnd.com.example.contentproviderdemo.birthdateinfo";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
}
