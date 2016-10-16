package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
    }

    public void createNewItem(View v) {
        Intent myIntent = new Intent(Store.this, ItemEdit.class);
        Store.this.startActivity(myIntent);
    }

    public void backToNewTrip(View v) {
        Intent myIntent = new Intent(Store.this, NewTrip.class);
        Store.this.startActivity(myIntent);
    }
}
