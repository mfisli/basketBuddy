package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TripHistory extends ListActivity {
    private List<String> listValues;
    private static final String TAG = TripHistory.class.getName();
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        listValues = dbOpenHelper.getTrips();
        Log.d(TAG, "listValues: " + listValues);
        if (listValues != null) {
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                    R.layout.row_layout, R.id.listText, listValues);
            // assign the list adapter
            setListAdapter(myAdapter);
        }
        Log.d(TAG, "Exiting onCreate");
    }
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        Log.d(TAG,">>>Start of onListItemClick");
        super.onListItemClick(list, view, position, id);
        String selectedTrip = (String) getListView().getItemAtPosition(position);
        //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(TripHistory.this, NewTrip.class);
        myIntent.putExtra("establishedTrip", selectedTrip);
        Log.d(TAG,"Going to newTrip with establishedTrip:" + selectedTrip);
        TripHistory.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of onListItemClick");
    }

    public void deleteTrips(View v){
        //TODO need to reload view -> invalidate does nothing
        Log.d(TAG, "Entering deleteTrips");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        dbOpenHelper.deleteTripRecords();

        //DBhelper db = new DBhelper(this, null, null, 1); // class access
        //db.deleteTripRecords();
        findViewById(android.R.id.content).invalidate();
        Log.d(TAG, "Exiting deleteTrips");
    }
    public void killStores(View v){
        Log.d(TAG, ">>> Entering killStores");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        dbOpenHelper.deleteStoreRecords();

        //DBhelper db = new DBhelper(this, null, null, 1); // class access
        //db.deleteTripRecords();
        findViewById(android.R.id.content).invalidate();
        Log.d(TAG, "Exiting killStores <<<");
    }
    public void killItems(View v){
        Log.d(TAG, ">>> Entering killItems");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        dbOpenHelper.deleteItemRecords();

        //DBhelper db = new DBhelper(this, null, null, 1); // class access
        //db.deleteTripRecords();
        findViewById(android.R.id.content).invalidate();
        Log.d(TAG, "Exiting killItems <<<");
    }



}
