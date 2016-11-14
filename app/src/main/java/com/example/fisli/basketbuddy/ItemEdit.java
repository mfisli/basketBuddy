package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ItemEdit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

    }

    public void backToStore(View v) {
        Intent myIntent = new Intent(ItemEdit.this, EditStore.class);
        ItemEdit.this.startActivity(myIntent);
    }

}
