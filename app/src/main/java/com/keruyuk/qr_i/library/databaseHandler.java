package com.keruyuk.qr_i.library;;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashMap;

public class databaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "qri_login";
    // Login table name
    private static final String TABLE_LOGIN = "login";
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_UID = "uid";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "uname";
    private static final String KEY_FIRSTNAME = "fname";
    private static final String KEY_LASTNAME = "lname";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DOB = "dob";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_CITY = "city";
    private static final String KEY_JOIN_DATE = "join_date";
    private static final String KEY_SALT = "salt";
    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_UID + " TEXT UNIQUE,"
            + KEY_EMAIL + " TEXT UNIQUE,"
            + KEY_USERNAME + " TEXT UNIQUE,"
            + KEY_FIRSTNAME + " TEXT,"
            + KEY_LASTNAME + " TEXT,"
            + KEY_PHONE + " TEXT,"
            + KEY_DOB + " TEXT,"
            + KEY_GENDER + " TEXT,"
            + KEY_CITY + " TEXT,"
            + KEY_SALT + " TEXT,"
            + KEY_JOIN_DATE + " TEXT" + ")";
    
    public databaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(CREATE_LOGIN_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        // Create tables again
        onCreate(db);
    }
    /**
     * Storing user details in database
     * */
    public void addUser(String id, String uid, String email, String uname, String fname, String lname,  String phone, 
    		String dob, String gender, String city, String join_date, String salt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id); // Id
        values.put(KEY_UID, uid); // UniqueId
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_USERNAME, uname); // UserName
        values.put(KEY_FIRSTNAME, fname); // FirstName
        values.put(KEY_LASTNAME, lname); // LastName
        values.put(KEY_PHONE, phone); // Phone
        values.put(KEY_DOB, dob); // DateOfBirth
        values.put(KEY_GENDER, gender); //Gender
        values.put(KEY_CITY, city); // City
        values.put(KEY_JOIN_DATE, join_date); // Created At
        values.put(KEY_SALT, salt); //Salt
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
    /**
     * Getting user data from database
     * */
    public HashMap getMemberDetails(){
        HashMap user = new HashMap();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	user.put("id", cursor.getString(0));
        	user.put("uid", cursor.getString(1));
        	user.put("email", cursor.getString(2));
        	user.put("uname", cursor.getString(3));
        	user.put("fname", cursor.getString(4));
            user.put("lname", cursor.getString(5));
            user.put("phone", cursor.getString(6));
            user.put("dob", cursor.getString(7));
            user.put("gender", cursor.getString(8));
            user.put("city", cursor.getString(9));
            user.put("join_date", cursor.getString(11));
            user.put("salt", cursor.getString(10));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }
    /**
     * Re create database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
     // Create tables again if not exist
        
        db.execSQL(CREATE_LOGIN_TABLE);
        
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
                
        db.close();
        
    }
}