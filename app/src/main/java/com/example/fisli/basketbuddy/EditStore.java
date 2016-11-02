package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditStore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
    }

    public void createNewItem(View v) {
        Intent myIntent = new Intent(EditStore.this, ItemEdit.class);
        EditStore.this.startActivity(myIntent);
    }

    public void backToNewTrip(View v) {
        Intent myIntent = new Intent(EditStore.this, NewTrip.class);
        EditStore.this.startActivity(myIntent);
    }
}
