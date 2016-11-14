package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewTrip extends ListActivity {
    private static final String TAG = NewTrip.class.getName();
    private List<String> listValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Start of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        //TextView txtView = (TextView) findViewById(R.id.textView1);
        DBhelper db = new DBhelper(this, null, null, 1); // class access
        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        TextView title = (TextView)findViewById(R.id.newtripTitle);
        title.setText(tripName);
        Log.d(TAG,"getStringExtra: " + tripName);
        db.setTrip(tripName);
        db.logDTripTable();
        ////////////////
        listValues = new ArrayList<String>();
        listValues = db.getStores();
        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.row_layout, R.id.listText, listValues);
        // assign the list adapter
        setListAdapter(myAdapter);

        Log.d(TAG,"End of onCreate");
    }

    public void startNewStore(View v) {
        Log.d(TAG,"Start of startNewStore");
        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        Intent myIntent = new Intent(NewTrip.this, EditStore.class);
        myIntent.putExtra("tripName", tripName);
        NewTrip.this.startActivity(myIntent);
        Log.d(TAG,"End of startNewStore");
    }

    public void startMyTrip(View v) {
        Log.d(TAG,"Start of startMyTrip");
        Intent myIntent = new Intent(NewTrip.this, TripMenu.class);
        NewTrip.this.startActivity(myIntent);
        Log.d(TAG,"End of startMyTrip");
    }
    public void deleteStores(View v){
        //TODO need to reload view -> invalidate does nothing
        Log.d(TAG, "Entering deleteStores");
        DBhelper db = new DBhelper(this, null, null, 1); // class access
        db.deleteStoreRecords();
        findViewById(android.R.id.content).invalidate();
        Log.d(TAG, "Exiting deleteStores");
    }
}
