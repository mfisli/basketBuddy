package com.example.fisli.basketbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class EditStore extends ListActivity {
    private static final String TAG = EditStore.class.getName();
    private DBOpenHelper dbOpenHelper;
    private List<String> listValues;
    private String currentTrip;
    private String currentStore = "New Store";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,">>>Start of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        setCurrentTripAndStore();
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        listValues = dbOpenHelper.getItem(currentStore);
        Log.d(TAG, "listValues: " + listValues);
        if (listValues != null) {
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                    R.layout.row_layout, R.id.listText, listValues);
            // assign the list adapter
            setListAdapter(myAdapter);
        }

        Log.d(TAG,"<<<End of onCreate");
    }
    //.........................................................................
    // sets the current store
    // "are you a new store or an existing store?"
    void setCurrentTripAndStore() {
        Log.d(TAG,">>>Start of setCurrentTripAndStore");
        Intent intent = getIntent();
        String currentTrip = intent.getStringExtra("currentTrip");
        String currentStore = intent.getStringExtra("selectedStore");
        TextView storeTitle   = (TextView)findViewById(R.id.storeTitleLabel);
        if (currentStore != null){
            storeTitle.setText(currentStore);
        }
        if (currentStore != null && currentTrip != null) {
            // if established store then populate
            Log.d(TAG, "Processing an establishedStore: " + currentStore);
            Log.d(TAG, "Processing a currentTrip: " + currentTrip);
            this.currentTrip = currentTrip;
            this.currentStore = currentStore;
            populateTextFields();
        } else if (currentTrip != null) {
            // established trips do not go into the data base
            Log.d(TAG, "Processing a new store in trip: " + currentTrip);
            this.currentTrip = currentTrip;
        } else {
            Log.d(TAG, "#######Expected intent extras are null - you did something dumb!");
            throw new UnsupportedOperationException("Should not be here!");
        }
        Log.d(TAG,"<<<End of setCurrentTrip");
    }
    void populateTextFields(){
        Log.d(TAG,">>>Start of populateTextFields");
        final String hours;
        final String address;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        hours = dbOpenHelper.getStoreHours(currentTrip, currentStore);
        address = dbOpenHelper.getStoreAddress(currentTrip, currentStore);
        EditText storeNameEditText   = (EditText)findViewById(R.id.storeNameInput);
        EditText storeHoursEditText   = (EditText)findViewById(R.id.storeHoursInput);
        EditText storeAddressEditText   = (EditText)findViewById(R.id.storeAddressInput);
        Log.d(TAG,"Setting fields with :" + currentStore + " " + hours + " " + address);
        storeNameEditText.setText(currentStore);
        storeHoursEditText.setText(hours);
        storeAddressEditText.setText(address);
        Log.d(TAG,"<<<End of populateTextFields");
    }
    //.........................................................................
    // TODO send intent with current trip
    public void createNewItem(View v) {
        Log.d(TAG,">>> Start of createNewItem");
        EditText storeNameEditText   = (EditText)findViewById(R.id.storeNameInput);
        String storeName      =  storeNameEditText.getText().toString();
        Intent myIntent = new Intent(EditStore.this, ItemEdit.class);
        this.currentStore = storeName;
        myIntent.putExtra("currentStore",currentStore);
        myIntent.putExtra("currentTrip",currentTrip);
        Log.d(TAG,"Going to ItemEdit with currentStore: " + currentStore
                + " in trip: " + currentTrip);
        EditStore.this.startActivity(myIntent);
        Log.d(TAG,"End of createNewItem <<<");
    }
    //.........................................................................
    public void backToNewTrip(View v) {
        Log.d(TAG,">>>Start of backToNewTrip (inserting store)");
        final SQLiteDatabase db;

        // get values required for inserting into Store table
        EditText storeNameEditText   = (EditText)findViewById(R.id.storeNameInput);
        EditText storeHoursEditText   = (EditText)findViewById(R.id.storeHoursInput);
        EditText storeAddressEditText   = (EditText)findViewById(R.id.storeAddressInput);
        String storeName      =  storeNameEditText.getText().toString();
        String storeHours      =  storeHoursEditText.getText().toString();
        String storeAddress      =  storeAddressEditText.getText().toString();

        // insert the values into the store table
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            dbOpenHelper.insertStoreValues(db, currentStore, currentTrip, storeName, storeAddress,storeHours);
            currentStore = storeName;
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        db.close();
        Intent myIntent = new Intent(EditStore.this, NewTrip.class);
        myIntent.putExtra("establishedTrip", currentTrip);
        Log.d(TAG,"Going to newTrip with establishedTrip:" + currentTrip);
        EditStore.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of backToNewTrip (inserting store)");
    }
    public void deleteStore(View v){
        Log.d(TAG,">>>Start of deleteStore");
        final SQLiteDatabase db;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.deleteStoreName(db, currentStore);
        Intent myIntent = new Intent(EditStore.this, NewTrip.class);
        myIntent.putExtra("establishedTrip", currentTrip);
        EditStore.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of deleteTrip");
    }
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        Log.d(TAG,">>>Start of onListItemClick");
        super.onListItemClick(list, view, position, id);
        String selectedItem = (String) getListView().getItemAtPosition(position);
        //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(EditStore.this, ItemEdit.class);
        myIntent.putExtra("selectedItem", selectedItem);
        myIntent.putExtra("currentStore", currentStore);
        myIntent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to newTrip with selectedItem; " +  selectedItem);
        Log.d(TAG,"Going to newTrip with currentStore; " +  currentStore);
        Log.d(TAG,"Going to newTrip with currentTrip; " +  currentTrip);
        EditStore.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of onListItemClick");
    }
}