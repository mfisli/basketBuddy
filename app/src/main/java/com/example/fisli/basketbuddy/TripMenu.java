package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TripMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
    }

    public void endTrip(View v) {
        Intent myIntent = new Intent(TripMenu.this, TripSummary.class);
        TripMenu.this.startActivity(myIntent);
    }

    public void startShopping(View v) {
        Intent myIntent = new Intent(TripMenu.this, ItemChecklist.class);
        TripMenu.this.startActivity(myIntent);
    }

}
