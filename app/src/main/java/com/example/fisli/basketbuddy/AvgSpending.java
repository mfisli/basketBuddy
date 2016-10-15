package com.example.fisli.basketbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AvgSpending extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg_spending);
    }

    public void backToHome(View v) {
        Intent myIntent = new Intent(AvgSpending.this, Home.class);
        AvgSpending.this.startActivity(myIntent);
    }
}
