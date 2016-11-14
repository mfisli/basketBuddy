package com.example.fisli.basketbuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Fisli on 2016-11-12.
 */

public class DBhelper extends SQLiteOpenHelper {
    //TODO Trip methods: isUnique(newTrip), deleteTrip();
    // If you change the database schema, you must increment the database version.
    private static final String TAG = DBhelper.class.getName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "basketBuddy3.db";

    // Trip Table
    private static final String TABLE_TRIP = "TRIP";
    public static final String COLUMN_TRIP_ID = "_id";
    public static final String COLUMN_TRIP_NAME = "TRIPNAME";

    // Store Table
    private static final String TABLE_STORE = "STORE";
    public static final String COLUMN_STORE_ID = "_id";
    public static final String COLUMN_STORE_NAME = "STORENAME";
    public static final String COLUMN_STORE_TRIP = "STORETRIP";
    public static final String COLUMN_STORE_ADDRESS = "STOREADDRESS";
    public static final String COLUMN_STORE_HOURS = "STOREHOURS";


    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, " Entering onCreate");
        String CREATE_TRIP_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_TRIP + "(" +
                COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRIP_NAME + " TEXT" + ")";
        String CREATE_STORE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_STORE + "(" +
                COLUMN_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STORE_TRIP + " TEXT, " +
                COLUMN_STORE_NAME + " TEXT, " +
                COLUMN_STORE_ADDRESS + " TEXT, " +
                COLUMN_STORE_HOURS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_STORE_TRIP + ") REFERENCES "
                + TABLE_STORE + "(" + COLUMN_TRIP_NAME + "))";
        db.execSQL(CREATE_TRIP_TABLE);
        Log.d(TAG, "CREATE_TRIP_TABLE");
        db.execSQL(CREATE_STORE_TABLE);
        Log.d(TAG, "CREATE_STORE_TABLE");
        Log.d(TAG, "Exiting onCreate");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        onCreate(db);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setTrip(String newTrip){
        ///TODO need a isUnique() guard to stop duplicate tripnames
        Log.d(TAG, "Entering setTrip");
        SQLiteDatabase db = this.getWritableDatabase();
        String query="INSERT INTO " + TABLE_TRIP + "(" + COLUMN_TRIP_NAME + ") VALUES (\'" + newTrip + "\' )";
        db.execSQL(query);
        Log.d(TAG, "Exiting setTrip");
    }

    public ArrayList<String> getTrips() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_TRIP_NAME + " FROM " + TABLE_TRIP;
        ArrayList<String> termsArray = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            termsArray.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return termsArray;
    }
    public void logDTripTable(){
        Log.d(TABLE_TRIP, "Logging all records");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRIP;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Log.d(TABLE_TRIP + " record: ", cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }
    public void deleteTripRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_TRIP);
        db.close();
        Log.d(TAG, "deleteTripRecords");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setStore(String trip, String name, String address, String hours){
        ///TODO need a isUnique() guard to stop duplicate tripnames
        Log.d(TAG, "Entering setStore");
        SQLiteDatabase db = this.getWritableDatabase();
        String query="INSERT INTO " +
                TABLE_STORE + "(" +
                COLUMN_STORE_TRIP + ", " +
                COLUMN_STORE_NAME + ", " +
                COLUMN_STORE_ADDRESS + ", " +
                COLUMN_STORE_HOURS + ") VALUES (" +
                "\'" + trip + "\', " + "\'" + name + "\', " +
                "\'" + address + "\', " + "\'" + hours + "\')";
        db.execSQL(query);
        Log.d(TAG, "Exiting setStore");
    }
    public ArrayList<String> getStores() {
        Log.d(TAG, "Entering getStores");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_STORE_NAME + " FROM " + TABLE_STORE;
        Log.d(TAG, "Input query: " + query);
        ArrayList<String> termsArray = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            termsArray.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Exiting getStores");
        return termsArray;
    }
    public void deleteStoreRecords(){
        Log.d("dropTripTable", "Dropping Table");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_STORE);
        db.close();
        Log.d(TAG, "deleteStoreRecords");
    }
}

