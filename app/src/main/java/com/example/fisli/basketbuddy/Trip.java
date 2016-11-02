package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Trip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
    }

    public void endTrip(View v) {
        Intent myIntent = new Intent(Trip.this, TripSummary.class);
        Trip.this.startActivity(myIntent);
    }

    public void startShopping(View v) {
        Intent myIntent = new Intent(Trip.this, ItemChecklist.class);
        Trip.this.startActivity(myIntent);
    }

}
