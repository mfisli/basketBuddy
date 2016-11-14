package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class TripHistory extends ListActivity {
    private List<String> listValues;
    private static final String TAG = TripHistory.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        DBhelper db = new DBhelper(this, null, null, 1); // class access
        listValues = new ArrayList<String>();
        listValues = db.getTrips();
        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.row_layout, R.id.listText, listValues);
        // assign the list adapter
        setListAdapter(myAdapter);
        Log.d(TAG, "Exiting onCreate");
    }

    public void deleteTrips(View v){
        //TODO need to reload view -> invalidate does nothing
        Log.d(TAG, "Entering deleteTrips");
        DBhelper db = new DBhelper(this, null, null, 1); // class access
        db.deleteTripRecords();
        findViewById(android.R.id.content).invalidate();
        Log.d(TAG, "Exiting deleteTrips");
    }



}
