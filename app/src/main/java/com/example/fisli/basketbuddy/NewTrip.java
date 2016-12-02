package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewTrip extends ListActivity {
    private static final String TAG = NewTrip.class.getName();
    private List<String> listValues;
    private DBOpenHelper dbOpenHelper;
    private String currentTrip;

    @Override
    //.........................................................................
    // on create
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,">>>Start of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        setCurrentTrip();
        populateStoresList();

        Log.d(TAG,"<<<End of onCreate");
    }
    //.........................................................................
    // sets the current trip
    // "are you a new trip or an existing trip?"
    void setCurrentTrip() {
        Log.d(TAG,">>>Start of setCurrentTrip");
        Intent intent = getIntent();
        String newTrip = intent.getStringExtra("newTrip");
        String establishedTrip = intent.getStringExtra("establishedTrip");
        if (newTrip != null) {
            // new trips are put into the data base
            Log.d(TAG, "Processing a newTrip: " + newTrip);
            setTripTitle(newTrip);
            insertNewTrip(newTrip);
        } else if (establishedTrip != null) {
            // established trips do not go into the data base
            Log.d(TAG, "Processing an establishedTrip: " + establishedTrip);
            setTripTitle(establishedTrip);
        } else {
            throw new UnsupportedOperationException("###Expected intent extras are null###");
        }
        Log.d(TAG,"<<<End of setCurrentTrip");
    }
    //.........................................................................
    // sets as the currentTrip data member and the activity title
    void setTripTitle(String trip) {
        Log.d(TAG,">>>Start of setTripTitle");
        currentTrip = trip;
        TextView title = (TextView)findViewById(R.id.newtripTitle);
        title.setText(trip);
        Log.d(TAG, "Title and currentTrip set as: " + trip);
        Log.d(TAG,">>>End of setTripTitle");
    }
    //.........................................................................
    // inserts a new trip into the trip table
    void insertNewTrip(String newTrip) {
        Log.d(TAG, ">>>Start of insertNewTrip");
        final SQLiteDatabase db;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            dbOpenHelper.insertTripName(db, newTrip);
            db.setTransactionSuccessful();
            Log.d(TAG, "Inserted a new trip: " + newTrip);
        }
        finally {
            db.endTransaction();
        }
        db.close();
        Log.d(TAG, "<<<End of insertNewTrip");
    }
    //.........................................................................
    // sets list with stores relevant to a specific trip
    void populateStoresList(){
        Log.d(TAG,">>>Start of populateStoresList");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        Log.d(TAG,"populating with currentTrip: " + currentTrip);
        listValues = new ArrayList<String>();
        listValues = dbOpenHelper.getStores(currentTrip);
        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.row_layout, R.id.listText, listValues);
        setListAdapter(myAdapter);
        Log.d(TAG,"<<End of populateStoresList");
    }
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        Log.d(TAG,">>>Start of onListItemClick");
        super.onListItemClick(list, view, position, id);
        String selectedStore = (String) getListView().getItemAtPosition(position);
        //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(NewTrip.this, EditStore.class);
        intent.putExtra("selectedStore", selectedStore);
        intent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to EditStore with establishedStore:" +
                selectedStore + " in trip " + currentTrip);
        NewTrip.this.startActivity(intent);
        Log.d(TAG,"<<<End of onListItemClick");
    }
    //.........................................................................
    public void startNewStore(View v) {
        Log.d(TAG,">>>Start of startNewStore");
        Intent intent = new Intent(NewTrip.this, EditStore.class);
        intent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to new store with currentTrip: " + currentTrip);
        NewTrip.this.startActivity(intent);
        Log.d(TAG,"<<<End of startNewStore");
    }
    //.........................................................................
    public void startMyTrip(View v) {
        Log.d(TAG,">>>Start of startMyTrip");
        Intent myIntent = new Intent(NewTrip.this, TripMenu.class);
        myIntent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to TripMenu currentTrip: " + currentTrip);
        NewTrip.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of startMyTrip");
    }
    //.........................................................................
    public void deleteTrip(View v){
        Log.d(TAG,">>>Start of deleteTrip");
        final SQLiteDatabase db;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.deleteTripName(db, currentTrip);
        Intent myIntent = new Intent(NewTrip.this, Home.class);
        NewTrip.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of deleteTrip");
    }
    //.........................................................................
    public void deleteStores(View v){
        //TODO need to reload view -> invalidate does nothing
        Log.d(TAG, ">>>Start deleteStores");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        dbOpenHelper.deleteStoreRecords();
        findViewById(android.R.id.content).invalidate();
        Log.d(TAG, "<<<End deleteStores");
    }
}
