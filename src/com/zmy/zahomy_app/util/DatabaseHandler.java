package com.zmy.zahomy_app.util;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zmy.zahomy_app.model.Item;
import com.zmy.zahomy_app.service.task.GetItemsTask;
import com.zmy.zahomy_app.src.MainZahomyActivity;
//import com.br.timetabler.src.SingleLessonActivity1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
			public class DatabaseHandler extends SQLiteOpenHelper {
			 
			    // All Static variables
			    // Database Version
			    private static final int DATABASE_VERSION = 2;
			 
			    // Database Name
			    private static final String DATABASE_NAME = "timetablerN27.db";
			 
			 // Login table name
			    private static final String TABLE_LOGIN = "user";
			    private static final String TABLE_TODAY_LESSONS = "List_Lessons";
			    private static final String TABLE_GRID_LESSONS = "grid_Lessons";
			    private static final String TABLE_LESSONS = "Lessons";
			    private static final String TABLE_COMMENTS = "Comments";
			 // Login table name
			    private static final String TABLE_UNI_PREFS = "student_pref";
			    //The Android's default system path of your application database.
			    private static String DB_PATH = "/data/data/com.br.timetabler/databases/";
			     
			    private SQLiteDatabase myDataBase;
			     
			    private final Context myContext;
			 
			    // Login Table Columns names
			    private static final String KEY_ID = "_id";
			    private static final String KEY_FNAME = "fname";
			    private static final String KEY_LNAME = "lname";
			    private static final String KEY_EMAIL = "email";
			    private static final String KEY_PASSWORD = "password";
			    private static final String KEY_UID = "uid";
			    private static final String KEY_CREATED_AT = "created_at";
			    
			    public DatabaseHandler(Context context) {
			        super(context, DATABASE_NAME, null, DATABASE_VERSION);
			        this.myContext = context;
			    }
			 
			    /**
			     * Creates a empty database on the system and rewrites it with your own database.
			     * */
			    public void createDataBase() throws IOException{
			    
			    	boolean dbExist = checkDataBase();
			    
			    	if(dbExist){
			    		 //do nothing - database already exist
			    	} else {
			    	  
			    		//By calling this method and empty database will be created into the default system path
					 	//of your application so we are gonna be able to overwrite that database with our database.
			    		this.getReadableDatabase();
			    		this.close(); 
			    		try {		  
			    			copyDataBase();		  
			    		} catch (IOException e) {
					  
			    			throw new Error(e.toString());
					  
			    		}
			    	}    
			    }
			    
			    /**
			     * Check if the database already exist to avoid re-copying the file each time you open the application.
			     * @return true if it exists, false if it doesn't
			     */
			    private boolean checkDataBase(){
			    
			    	File dbFile = new File(DB_PATH + DATABASE_NAME);
			        return dbFile.exists();
			    }
			    
			    /**
			     * Copies your database from your local assets-folder to the just created empty database in the
			     * system folder, from where it can be accessed and handled.
			     * This is done by transfering bytestream.
			     * */
			    private void copyDataBase() throws IOException{
			    
			    	//Open your local db as the input stream
			    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
			    
			    	// Path to the just created empty db
			    	String outFileName = DB_PATH + DATABASE_NAME;
			    
			    	//Open the empty db as the output stream
			    	OutputStream myOutput = new FileOutputStream(outFileName);
			    
			    	//transfer bytes from the inputfile to the outputfile
			    	byte[] buffer = new byte[1024];
			    	int length;
			    	while ((length = myInput.read(buffer))>0){
			    		myOutput.write(buffer, 0, length);
			    	}
			    
			    	//Close the streams
			    	myOutput.flush();
			    	myOutput.close();
			    	myInput.close();
			    
			    }
			    
			    public void openDataBase() throws SQLException{
			    
			    	//Open the database
			    	String myPath = DB_PATH + DATABASE_NAME;
			    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			    
			    }
			    
			   	@Override
			   	public synchronized void close() {
			    
			   		if(myDataBase != null)
			   			myDataBase.close();
			    
			   		super.close();
			    
			   	}
			    
			
			    // Creating Tables
			    @Override
			    public void onCreate(SQLiteDatabase db) {
			        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
			                + KEY_ID + " INTEGER PRIMARY KEY,"
			                + KEY_FNAME + " TEXT,"
			                + KEY_LNAME + " TEXT,"
			                + KEY_EMAIL + " TEXT UNIQUE,"
			                + KEY_PASSWORD + " TEXT,"
			                + KEY_UID + " TEXT,"
			                + KEY_CREATED_AT + " TEXT)";
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
			    public void addUser(String fname, String lname, String email, String password, String uid) {
			        SQLiteDatabase db = this.getWritableDatabase();
			 
			        ContentValues values = new ContentValues();
			        values.put(KEY_UID, uid); // user id
			        values.put(KEY_FNAME, fname); // First Name
			        values.put(KEY_LNAME, lname); // Last Name
			        values.put(KEY_EMAIL, email); // Email
			        values.put(KEY_PASSWORD, password); // Email
			 
			        // Inserting Row , 
			        db.insert(TABLE_LOGIN, null, values);
			        db.close(); // Closing database connection
			    }
			 
			    /**
			     * Getting user data from database
			     * */
			    public HashMap<String, String> getUserDetails(){
			        HashMap<String,String> user = new HashMap<String,String>();
			        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
			 
			        SQLiteDatabase db = this.getReadableDatabase();
			        Cursor cursor = db.rawQuery(selectQuery, null);
			        // Move to first row
			        cursor.moveToFirst();
			        if(cursor.getCount() > 0){
			        	user.put("uid", cursor.getString(5));
			        	user.put("email", cursor.getString(3));
			        	user.put("password", cursor.getString(4));
			        	user.put("fname", cursor.getString(1));
			        	user.put("lname", cursor.getString(2));
			        	
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
			     * Re crate database
			     * Delete all tables and create them again
			     * */
			    public void resetTables(){
			        SQLiteDatabase db = this.getWritableDatabase();
			        // Delete All Rows
			        db.delete(TABLE_LOGIN, null, null);
			        db.close();
			    }
			 
}