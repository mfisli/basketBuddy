package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ItemEdit extends AppCompatActivity {
    private static final String TAG = ItemEdit.class.getName();
    private DBOpenHelper dbOpenHelper;
    private String currentStore;
    private String currentTrip;
    private String currentItem = "New Item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,">>> Start of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);
        setCurrentStoreAndItem();
        Log.d(TAG,"<<< End of onCreate");

    }
    //.........................................................................
    // sets the current item
    // "are you a new item or an existing item?"
    void setCurrentStoreAndItem() {
        Log.d(TAG, ">>>Start of setCurrentStoreAndItem");
        Intent intent = getIntent();
        String currentStore = intent.getStringExtra("currentStore");
        String currentTrip = intent.getStringExtra("currentTrip");
        String currentItem = intent.getStringExtra("selectedItem");  // from list activity

        TextView itemTitle = (TextView) findViewById(R.id.itemEditTitle);
        if (currentItem != null) {
            itemTitle.setText(currentStore);
        }
        if (currentItem != null && currentStore != null && currentTrip != null) {   // from list activity
            // established items get populated
            Log.d(TAG, "Processing an establishedItem: " + currentItem);
            Log.d(TAG, "Processing a currentStore: " + currentStore);
            Log.d(TAG, "Processing a currentTrip: " + currentTrip);
            this.currentStore = currentStore;
            this.currentItem = currentItem;
            this.currentTrip = currentTrip;

            populateTextFields();
        } else if (currentStore != null && currentTrip != null) {
            // a new item
            Log.d(TAG, "Processing a new item in store: " + currentStore + " of trip: " + currentTrip);
            this.currentStore = currentStore;
            this.currentTrip = currentTrip;
        } else {
            throw new UnsupportedOperationException("#### Expected intents are null! ###");
        }
        Log.d(TAG, "<<<End of setCurrentTrip");
    }
    void populateTextFields(){
        Log.d(TAG,">>>Start of populateTextFields");
        final String quantity;
        final String aisle;
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        quantity = dbOpenHelper.getItemQuantity(currentStore, currentItem);
        aisle = dbOpenHelper.getItemAisle(currentStore, currentItem);
        EditText itemNameEditText   = (EditText)findViewById(R.id.itemEditInputName);
        EditText itemQuantityEditText   = (EditText)findViewById(R.id.itemEditInputQuantity);
        EditText itemAisleEditText   = (EditText)findViewById(R.id.itemEditInputAisle);
        Log.d(TAG,"Setting fields with :" + currentStore + " " + quantity + " " + aisle);
        itemNameEditText.setText(currentItem);
        itemQuantityEditText.setText(quantity);
        itemAisleEditText.setText(aisle);
        Log.d(TAG,"<<<End of populateTextFields");
    }

    public void backToStore(View v) {
        Log.d(TAG,">>>Start of backToStore (inserting item)");
        final SQLiteDatabase db;

        // get values required for inserting into Store table
        EditText itemNameEditText   = (EditText)findViewById(R.id.itemEditInputName);
        EditText itemQuantityEditText   = (EditText)findViewById(R.id.itemEditInputQuantity);
        EditText itemAisleEditText   = (EditText)findViewById(R.id.itemEditInputAisle);
        String itemName       =  itemNameEditText.getText().toString();
        String itemQuantity   =  itemQuantityEditText.getText().toString();
        String itemAisle      =  itemAisleEditText.getText().toString();

        // insert the values into the store table
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            dbOpenHelper.insertItemValues(db, currentItem, currentStore, itemName, itemQuantity,itemAisle);

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        db.close();
        Intent myIntent = new Intent(ItemEdit.this, EditStore.class);
        myIntent.putExtra("selectedStore", currentStore);
        myIntent.putExtra("currentTrip", currentTrip);
        Log.d(TAG,"Going to EditStore with selectedStore:" + currentStore);
        Log.d(TAG,"Going to EditStore with currentTrip:" + currentTrip);
        ItemEdit.this.startActivity(myIntent);
        Log.d(TAG,"<<<End of backToStore (inserting item)");
    }

}
