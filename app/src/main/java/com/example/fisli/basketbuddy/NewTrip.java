package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewTrip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
    }

    public void startNewStore(View v) {
        Intent myIntent = new Intent(NewTrip.this, Store.class);
        NewTrip.this.startActivity(myIntent);
    }

    //this screen doesnt exist yet
//    public void startMyTrip(View v) {
//        Intent myIntent = new Intent(NewTrip.this, MyTrip.class);
//        NewTrip.this.startActivity(myIntent);
//    }
}
