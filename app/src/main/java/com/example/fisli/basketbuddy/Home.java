package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home extends AppCompatActivity {
    // hello, my name is Maks and I do not like Android Studio
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //starts New TripMenu Activity
    public void startNewTrip(View v) {
        Intent myIntent = new Intent(Home.this, NewTrip.class);
        Home.this.startActivity(myIntent);
    }

    //starts TripMenu History Activity
    public void startMyTrips(View v) {
        Intent myIntent = new Intent(Home.this, TripHistory.class);
        Home.this.startActivity(myIntent);
    }

    //starts Average Spending Activity
    public void startAvgSpending(View v) {
        Intent myIntent = new Intent(Home.this, AvgSpending.class);
        Home.this.startActivity(myIntent);
    }

}
