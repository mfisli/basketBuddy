package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TripMenu extends ListActivity {
    private static final String TAG = ItemChecklist.class.getName();
    private DBOpenHelper dbOpenHelper;
    private List<String> listValues;
    private String currentTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, ">>> Start of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        setTripTitle();
        populateStoresList();
        Log.d(TAG, "End of onCreate <<<");
    }

    void setTripTitle() {
        Intent intent = getIntent();
        String currentTrip = intent.getStringExtra("currentTrip");
        this.currentTrip = currentTrip;
        TextView title = (TextView) findViewById(R.id.tripTitle);
        title.setText(currentTrip);
    }

    //.........................................................................
    // sets list with stores relevant to a specific trip
    void populateStoresList() {
        Log.d(TAG, ">>>Start of populateStoresList");
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        Log.d(TAG, "populating with currentTrip: " + currentTrip);
        listValues = new ArrayList<String>();
        listValues = dbOpenHelper.getStores(currentTrip);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                R.layout.row_layout, R.id.listText, listValues);
        setListAdapter(myAdapter);
        Log.d(TAG, "<<End of populateStoresList");
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        Log.d(TAG, ">>>Start of onListItemClick");
        super.onListItemClick(list, view, position, id);
        String selectedStore = (String) getListView().getItemAtPosition(position);
        //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TripMenu.this, ItemChecklist.class);
        intent.putExtra("selectedStore", selectedStore);
        intent.putExtra("currentTrip", currentTrip);
        Log.d(TAG, "Going to ItemChecklist with establishedStore:" +
                selectedStore + " in trip " + currentTrip);
        TripMenu.this.startActivity(intent);
        Log.d(TAG, "<<<End of onListItemClick");
    }

    public void goHome(View v) {
        Intent myIntent = new Intent(TripMenu.this, Home.class);
        TripMenu.this.startActivity(myIntent);
    }
}