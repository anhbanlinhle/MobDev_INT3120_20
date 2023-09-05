package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    private int donate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Custom tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Donation.1.5");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.toolbar_menu);


    }
}