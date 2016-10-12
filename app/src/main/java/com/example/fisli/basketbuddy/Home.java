package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //starts New Trip Activity
    public void openNewTrip() {
        //Intent myIntent = new Intent(Home.this, NewTrip.class);
        //Home.this.startActivity(myIntent);
    }

    //starts Trip History Activity
    public void openMyTrips() {
        Intent myIntent = new Intent(Home.this, TripHistory.class);
        Home.this.startActivity(myIntent);
    }

    //starts Average Spending Activity
    public void openAvgSpending() {
        Intent myIntent = new Intent(Home.this, AvgSpending.class);
        Home.this.startActivity(myIntent);
    }

}
