package com.example.slide_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toMedia(View view) {
        intent = new Intent(MainActivity.this, MediaActivity.class);
    }

    public void toLocation(View view) {
        intent = new Intent(MainActivity.this, LocationActivity.class);
    }

    public void toMap(View view) {
        intent = new Intent(MainActivity.this, MapActivity.class);
    }

}