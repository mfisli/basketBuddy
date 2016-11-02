package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ItemChecklist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_checklist);
    }

    public void startReceipt(View v) {
        Intent myIntent = new Intent(ItemChecklist.this, Receipt.class);
        ItemChecklist.this.startActivity(myIntent);
    }

    public void startStoreEdit(View v) {
        Intent myIntent = new Intent(ItemChecklist.this, EditStore.class);
        ItemChecklist.this.startActivity(myIntent);
    }
}
