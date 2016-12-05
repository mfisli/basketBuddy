package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private static final String TAG = Home.class.getName();
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG,"onCreate");

    }

    //starts New TripMenu Activity
    public void startNewTrip(View v) {
        Log.d(TAG,">>>entering startNewTrip");
        EditText tripNameEditText   = (EditText)findViewById(R.id.inputNewTripName);
        String newTrip      =  tripNameEditText.getText().toString();
        dbOpenHelper = DBOpenHelper.getInstance(getApplicationContext());

        if( newTrip.trim().equals("")) {
            String text = "Trip name cannot be empty.";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        } else if(! dbOpenHelper.isTripNameUnique(newTrip)) {
            String text = "Trip name already exists in My Trips\n Trip names must be unique.";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Going to NewTrip.class with intent: " + newTrip);
            Intent myIntent = new Intent(Home.this, NewTrip.class);
            myIntent.putExtra("newTrip", newTrip);
            Home.this.startActivity(myIntent);
        }
        Log.d(TAG, "<<<exiting startNewTrip");
    }

    //starts TripMenu History Activity
    public void startTripHistory(View v) {
        Intent myIntent = new Intent(Home.this, TripHistory.class);
        Home.this.startActivity(myIntent);
    }
}
