package com.example.contentproviderdemo;

import android.net.Uri;

public class BirthdateContract {

	 public static final String AUTHORITY = "com.example.contentproviderdemo.provider";
	 
	 private static final String URL = "content://" + AUTHORITY ;
	 public static final Uri CONTENT_URI = Uri.parse(URL);
	 
	 public static final class BirthdateInfo
	 {
		 private static final String PATH = "BirthdateInfo";
		 public static final Uri CONTENT_URI = Uri.withAppendedPath(BirthdateContract.CONTENT_URI, PATH);
		 
		 public static final String _ID = "_id";
		 public static final String NAME = "name";
		 public static final String BIRTHDATE = "birthdate";
		 
		 //public static final String[] ALL_COLUMNS = { _ID, NAME, BIRTHDATE };
	 }
}
