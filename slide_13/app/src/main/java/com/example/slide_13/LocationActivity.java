package com.example.slide_13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}