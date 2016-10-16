package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TripSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);
    }

    public void backToHome(View v) {
        Intent myIntent = new Intent(TripSummary.this, Home.class);
        TripSummary.this.startActivity(myIntent);
    }
}
