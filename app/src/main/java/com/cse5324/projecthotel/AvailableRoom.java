package com.cse5324.projecthotel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AvailableRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_room);

        getSupportActionBar().setTitle("Available Rooms");
    }
}