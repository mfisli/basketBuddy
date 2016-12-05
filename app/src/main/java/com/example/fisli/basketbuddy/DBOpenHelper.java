package com.example.fisli.basketbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Fisli on 2016-11-19.
 */
//.........................................................................
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DBOpenHelper.class.getName();
    private static DBOpenHelper instance;

    public static final int SCHEMA_VERSION = 1;
    public static final String DATABASE_NAME = "basketBuddy7.db";

    // Trip Table
    private static final String TABLE_TRIP = "TRIP";
    private static final String COLUMN_TRIP_ID = "_id";
    private static final String COLUMN_TRIP_NAME = "TRIPNAME";
    // Store Table
    private static final String TABLE_STORE = "STORE";
    public static final String COLUMN_STORE_ID = "_id";
    public static final String COLUMN_STORE_NAME = "STORENAME";
    public static final String COLUMN_STORE_TRIP = "STORETRIP";
    public static final String COLUMN_STORE_ADDRESS = "STOREADDRESS";
    public static final String COLUMN_STORE_HOURS = "STOREHOURS";
    // Item Table
    private static final String TABLE_ITEM = "ITEM";
    private static final String COLUMN_ITEM_ID = "_id";
    private static final String COLUMN_ITEM_STORE = "ITEMSTORE";
    private static final String COLUMN_ITEM_NAME = "ITEMNAME";
    private static final String COLUMN_ITEM_QUANTITY = "ITEMQUANTITY";
    private static final String COLUMN_ITEM_AISLE = "ITEMAISLE";
    private static final String COLUMN_ITEM_CHECKED = "ITEMCHECK";

    private DBOpenHelper(final Context ctx) {
        super(ctx, DATABASE_NAME, null, SCHEMA_VERSION);
    }
    //.........................................................................
    // Gets the instance of a helper or constructs one if none exist
    public synchronized static DBOpenHelper getInstance(final Context context) {
        if(instance == null) {
            instance = new DBOpenHelper(context.getApplicationContext());
        }
        return instance;
    }
    //.........................................................................
    // Drops tables and re-creates them
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db);
    }
    //.........................................................................
    // Creates tables
    @Override
    public void onCreate(final SQLiteDatabase db) {
        Log.d(TAG, " Entering onCreate");
        final String  CREATE_TRIP_TABLE;
        final String  CREATE_STORE_TABLE;
        final String  CREATE_ITEM_TABLE;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        // Trip Table
        CREATE_TRIP_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_TRIP + "(" +
                COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRIP_NAME + " TEXT NOT NULL" + ")";
                db.execSQL(CREATE_TRIP_TABLE);
                Log.d(TAG, "CREATE_TRIP_TABLE");
        db.execSQL(CREATE_TRIP_TABLE);
        // Store Table
        CREATE_STORE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_STORE + "(" +
                COLUMN_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STORE_TRIP + " TEXT NOT NULL, " +
                COLUMN_STORE_NAME + " TEXT NOT NULL, " +
                COLUMN_STORE_ADDRESS + " TEXT, " +
                COLUMN_STORE_HOURS + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_STORE_TRIP + ") REFERENCES "
                + TABLE_TRIP + "(" + COLUMN_TRIP_NAME + "))";
        db.execSQL(CREATE_STORE_TABLE);
        // Item Table
        CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_STORE + " TEXT NOT NULL, " +
                COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                COLUMN_ITEM_QUANTITY + " TEXT, " +
                COLUMN_ITEM_AISLE + " TEXT, " +
                COLUMN_ITEM_CHECKED + " BOOLEAN NOT NULL DEFAULT 0 CHECK ( " + COLUMN_ITEM_CHECKED + " IN (0,1)), " +
                "FOREIGN KEY(" + COLUMN_ITEM_STORE + ") REFERENCES "
                + TABLE_STORE + "(" + COLUMN_STORE_NAME + "))";
        db.execSQL(CREATE_ITEM_TABLE);
        Log.d(TAG, " Exiting onCreate");
    }
    ///////////////////////////////////////////////////////////////////////////
    // Trip accessors and modifiers
    //.........................................................................
    // inserts a trip name into the trip table
    public void insertTripName(final SQLiteDatabase db, final String tripName) {
        Log.d(TAG, " Entering insertTripName");
        final ContentValues contentValues;

        contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_NAME, tripName);
        db.insert(TABLE_TRIP, null, contentValues);
        Log.d(TAG, " inserting new trip name: " + tripName);
        Log.d(TAG, " Exiting insertTripName");
    }
    //.........................................................................
    // gets all of the records in the Trip table
    public ArrayList<String> getTrips() {
        Log.d(TAG, " Entering getTrips");
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
        Log.d(TAG, " Exiting getTrips");
        return termsArray;
    }
    //.........................................................................
    // deletes a single trip record
    public void deleteTripName(final SQLiteDatabase db, final String tripName){
        final int rows;
        rows = db.delete(TABLE_TRIP,
                COLUMN_TRIP_NAME + " = ?",
                new String[]
                        {
                                tripName,
                        });

        Log.d(TAG, "Deleted " + tripName + " With " + rows + "rows");
    }
    //.........................................................................
    // deletes all trip records
    public void deleteTripRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_TRIP);
        db.close();
        Log.d(TAG, "deleteTripRecords");
    }
    //.........................................................................
    // returns true if trip name is unique
    public boolean isTripNameUnique(final String tripName){
        boolean result;
        Log.d(TAG, ">>> Entering isTripNameUnique");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_TRIP_NAME +
                " FROM " + TABLE_TRIP
                + " WHERE " + COLUMN_TRIP_NAME + "= \'" + tripName + "\'";
        Cursor cursor = db.rawQuery(query, null);
        result = cursor.getCount() == 0;
        cursor.close();
        db.close();
        Log.d(TAG, "Exiting isTripNameUnique <<<");
        return result;


    }
    ///////////////////////////////////////////////////////////////////////////
    // Store accessors and modifiers
    //.........................................................................
    // inserts a store record
    public void insertStoreValues(final SQLiteDatabase db, String oldStoreName, String trip, String name,
                                  String address, String hours) {
        Log.d(TAG, " Entering insertStoreValues");
        name = name.replace("\'",""); // 's  = sql syntax error
        if(trip == null){
            Log.d(TAG, "###>>>Trip is null you did something dumb");
        }
        Log.d(TAG, " inserting new values: " + trip + " " + name + " " + address + " " + hours);
        // "upsert" aka insert or update statement (I am very sorry)
        String query;
        query = "INSERT OR REPLACE INTO "+  TABLE_STORE + " (" +
                COLUMN_STORE_ID + "," + COLUMN_STORE_TRIP + "," +
                COLUMN_STORE_NAME + "," + COLUMN_STORE_ADDRESS + "," +
                COLUMN_STORE_HOURS + ")" + " VALUES (" +
                // id insert
                "( SELECT " + COLUMN_STORE_ID +
                " FROM " + TABLE_STORE + " WHERE " +
                COLUMN_STORE_NAME + " = " + "\'" + oldStoreName + "\')," +
                // trip name insert
                "\'" + trip + "\'," +
                // store name insert
                "\'" + name + "\'," +
                // address insert
                "\'" + address + "\'," +
                // store hours insert
                "\'" + hours + "\')";
        Log.d(TAG, "     UPSERT: " + query);
        db.execSQL(query);
        Log.d(TAG, " Exiting insertStoreValues");
    }
    //.........................................................................
    // gets the address from a store given the trip and store name
    public String getStoreAddress(String trip, String name){
        Log.d(TAG, ">>>Start getStoreAddress");
        String result;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_STORE_ADDRESS +
                " FROM " + TABLE_STORE +
                " WHERE " + COLUMN_STORE_NAME +
                " = " + "\'" + name + "\'" +
                " AND " + COLUMN_STORE_TRIP +
                " = " + "\'" + trip + "\'";
        Log.d(TAG,query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            Log.d(TAG, "###Cursor did not find the address.");
            cursor.close();
            db.close();
            return " ";
        }
        result = cursor.getString(0);
        Log.d(TAG, " found ADDRS" + result);
        cursor.close();
        db.close();
        Log.d(TAG, "<<<End getStoreAddress");
        return result;
    }
    //.........................................................................
    // gets the address from a store given the trip and store name
    public String getStoreHours(String trip, String name){
        String result;
        Log.d(TAG, ">>>Start getStoreHours");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_STORE_HOURS +
                " FROM " + TABLE_STORE +
                " WHERE " + COLUMN_STORE_NAME +
                " = " + "\'" + name + "\'" +
                " AND " + COLUMN_STORE_TRIP +
                " = " + "\'" + trip + "\'";
        Log.d(TAG,query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            Log.d(TAG, "###Cursor did not find any store hours.");
            cursor.close();
            db.close();
            return " ";
        }
        result = cursor.getString(0);
        Log.d(TAG, " found HRS " + result);
        cursor.close();
        db.close();
        Log.d(TAG, "<<<End getStoreHours");
        return result;
    }

    //.........................................................................
    // gets all of the store in the Store table of a specific trip
    public ArrayList<String> getStores(String tripName) {
        Log.d(TAG, " Entering getStores");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_STORE_NAME
                + " FROM " + TABLE_STORE +
                " WHERE " + COLUMN_STORE_TRIP
                + " = " + "\'" + tripName + "\'";
        //SELECT * FROM Customers
        //WHERE Country='Mexico';
        ArrayList<String> termsArray = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        Log.d(TAG, " Selecting stores from trip " + tripName);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            termsArray.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        Log.d(TAG, " Exiting getStores");
        return termsArray;
    }
    //.........................................................................
    // deletes all records from the Store table
    public void deleteStoreRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_STORE);
        db.close();
        Log.d(TAG, "deleteStoreRecords");
    }
    //.........................................................................
    // deletes a single store record
    public void deleteStoreName(final SQLiteDatabase db, final String store){
        final int rows;
        rows = db.delete(TABLE_STORE,
                COLUMN_STORE_NAME + " = ?",
                new String[]
                        {
                                store,
                        });

        Log.d(TAG, "Deleted " + store + " with " + rows + "rows");
    }
    ///////////////////////////////////////////////////////////////////////////
    // Items accessors and modifiers
    //.........................................................................
    // inserts values into items table
    public void insertItemValues(final SQLiteDatabase db, String oldItemName, String store, String name,
                                  String quantity, String aisle) {
        Log.d(TAG, " Entering insertItemValues");
        if(store == null){
            Log.d(TAG, "###>>>Store is null you did something dumb");
        }
        Log.d(TAG, " inserting new values: " + store + " " + name + " " + quantity + " " + aisle);
        // "upsert" aka insert or update statement (I am very sorry)
        final String query;
        query = "INSERT OR REPLACE INTO "+  TABLE_ITEM + " (" +
                COLUMN_ITEM_ID + "," + COLUMN_ITEM_STORE + "," +
                COLUMN_ITEM_NAME + "," + COLUMN_ITEM_QUANTITY + "," +
                COLUMN_ITEM_AISLE + ")" + " VALUES (" +
                // id insert
                "( SELECT " + COLUMN_ITEM_ID +
                " FROM " + TABLE_ITEM + " WHERE " +
                COLUMN_ITEM_NAME + " = " + "\'" + oldItemName + "\')," +
                // trip name insert
                "\'" + store + "\'," +
                // store name insert
                "\'" + name + "\'," +
                // address insert
                "\'" + quantity + "\'," +
                // store hours insert
                "\'" + aisle + "\')";
        Log.d(TAG, "     UPSERT: " + query);
        db.execSQL(query);
        Log.d(TAG, " Exiting insertItemValues");
    }
    //.........................................................................
    // Updates the checkboxes
    public void updateCheckBox(final SQLiteDatabase db, String store, String itemname, int mode){
        Log.d(TAG, ">>> Entering updateCheckBox");
        if(store == null || itemname == null){
            Log.d(TAG, "### Expected parameters are null ###");
            return;
        } else {
            Log.d(TAG, "Updating checkbox for "+ itemname + "item in " + store + " store");
        }
        final String query;
        query = "UPDATE " + TABLE_ITEM
                + " SET " + COLUMN_ITEM_CHECKED + " = \'"+mode+"\' "
                + " WHERE " + COLUMN_ITEM_STORE + " = \'"+store+"\' "
                + " AND " + COLUMN_ITEM_NAME + " = \'"+itemname+"\' ";
        db.execSQL(query);
        Log.d(TAG, "Query: " +  query);
        Log.d(TAG, "Exiting updateCheckBox <<<");
    }
    //.........................................................................
    // gets the items for a given store
    public ArrayList<String> getItem(String storeName) {
        Log.d(TAG, " Entering getItem");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_NAME
                + " FROM " + TABLE_ITEM
                + " WHERE " + COLUMN_ITEM_STORE
                + " = " + "\'" + storeName + "\'";
        ArrayList<String> termsArray = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        Log.d(TAG, " Selecting items from store " + storeName);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            termsArray.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        Log.d(TAG, " Exiting getItem");
        return termsArray;
    }
    //.........................................................................
    // gets the quantity for an item given
    public String getItemQuantity(String store, String itemName){
        String result;
        Log.d(TAG, ">>>Start getItemQuantity");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_QUANTITY +
                " FROM " + TABLE_ITEM +
                " WHERE " + COLUMN_ITEM_NAME +
                " = " + "\'" + itemName + "\'" +
                " AND " + COLUMN_ITEM_STORE +
                " = " + "\'" + store + "\'";
        Log.d(TAG,query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            Log.d(TAG, "###Cursor did not find anything.");
            return " ";
        }
        result = cursor.getString(0);
        Log.d(TAG, " found QNTY " + result);
        cursor.close();
        db.close();
        Log.d(TAG, "<<<End getItemQuantity");
        return result;
    }
    //.........................................................................
    // gets the aisle for an item given
    public String getItemAisle(String store, String itemName){
        String result;
        Log.d(TAG, ">>>Start getItemAisle");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_AISLE +
                " FROM " + TABLE_ITEM +
                " WHERE " + COLUMN_ITEM_NAME +
                " = " + "\'" + itemName + "\'" +
                " AND " + COLUMN_ITEM_STORE +
                " = " + "\'" + store + "\'";
        Log.d(TAG,query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            Log.d(TAG, "###Cursor did not find anything.");
            return " ";
        }
        result = cursor.getString(0);
        Log.d(TAG, " found ASL " + result);
        cursor.close();
        db.close();
        Log.d(TAG, "<<<End getItemAisle");
        return result;
    }
    //.........................................................................
    // gets the quantity for an item given
    public boolean getChecked(String store, String itemName){
        Log.d(TAG, ">>>Start getChecked");
        int result;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_CHECKED +
                " FROM " + TABLE_ITEM +
                " WHERE " + COLUMN_ITEM_NAME +
                " = " + "\'" + itemName + "\'" +
                " AND " + COLUMN_ITEM_STORE +
                " = " + "\'" + store + "\'";
        Log.d(TAG,query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0){
            Log.d(TAG, "###Cursor did not find anything.");
        }
        result = cursor.getInt(0);
        Log.d(TAG, " found CHECKED " + result);
        cursor.close();
        db.close();
        Log.d(TAG, "<<<End getItemQuantity");
        return result == 1 ? true:false;
    }
    //.........................................................................
    // deletes a single item record
    public void deleteItemName(final SQLiteDatabase db, final String store){
        final int rows;
        rows = db.delete(TABLE_ITEM,
                COLUMN_ITEM_STORE + " = ?",
                new String[]
                        {
                                store,
                        });

        Log.d(TAG, "Deleted " + store + " with " + rows + " rows");
    }
    //.........................................................................
    // deletes all records from the Item table
    public void deleteItemRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_ITEM);
        db.close();
        Log.d(TAG, "deleteItemRecords");
    }
}