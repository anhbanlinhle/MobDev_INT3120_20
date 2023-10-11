package com.example.slide_12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.slide_12.accelerometer.AccelerometerActivity;
import com.example.slide_12.magnetometer.MagnetometerActivity;
import com.example.slide_12.telephony.CallActivity;
import com.example.slide_12.wifi.WifiActivity;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void accelerometer(View view) {
        intent = new Intent(MainActivity.this, AccelerometerActivity.class);
        startActivity(intent);
    }

    public void magnetometer(View view) {
        intent = new Intent(MainActivity.this, MagnetometerActivity.class);
        startActivity(intent);
    }

    public void wifi(View view) {
        intent = new Intent(MainActivity.this, WifiActivity.class);
        startActivity(intent);
    }

    public void call(View view) {
        intent = new Intent(MainActivity.this, CallActivity.class);
        startActivity(intent);
    }

    public void sms(View view) {
        intent = new Intent(MainActivity.this, SmsActivity.class);
        startActivity(intent);
    }

    public void camera(View view) {
        intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public void bluetooth(View view) {
        intent = new Intent(MainActivity.this, BluetoothActivity.class);
        startActivity(intent);
    }
}