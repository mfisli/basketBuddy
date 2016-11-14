package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditStore extends AppCompatActivity {
    private static final String TAG = EditStore.class.getName();
    private static String TRIPNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        DBhelper db = new DBhelper(this, null, null, 1); // class access
        Intent intent = getIntent();
        String tripName = intent.getStringExtra("tripName");
        TRIPNAME = tripName;   // global variable = new very bad :(

    }

    public void createNewItem(View v) {
        Intent myIntent = new Intent(EditStore.this, ItemEdit.class);
        EditStore.this.startActivity(myIntent);
    }

    public void backToNewTrip(View v) {
        DBhelper db = new DBhelper(this, null, null, 1); // class access


        // insert values into store table
        EditText storeNameEditText   = (EditText)findViewById(R.id.storeNameInput);
        EditText storeHoursEditText   = (EditText)findViewById(R.id.storeHoursInput);
        EditText storeAddressEditText   = (EditText)findViewById(R.id.storeAddressInput);
        String storeName      =  storeNameEditText.getText().toString();
        String storeHours      =  storeHoursEditText.getText().toString();
        String storeAddress      =  storeAddressEditText.getText().toString();
        db.setStore(TRIPNAME,storeName,storeHours,storeAddress);

        Intent myIntent = new Intent(EditStore.this, NewTrip.class);
        EditStore.this.startActivity(myIntent);
    }

}
