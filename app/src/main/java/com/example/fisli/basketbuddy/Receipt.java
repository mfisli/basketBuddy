package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Receipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
    }

//    public void backToMyTrip(View v) {
//        Intent myIntent = new Intent(Receipt.this, Store.class);
//        Receipt.this.startActivity(myIntent);
//    }

    public void saveSpending(View v) {
        Intent myIntent = new Intent(Receipt.this, TripSummary.class);
        Receipt.this.startActivity(myIntent);
    }

}
