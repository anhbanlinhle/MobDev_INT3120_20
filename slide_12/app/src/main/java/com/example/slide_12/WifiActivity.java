package com.example.slide_12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class WifiActivity extends AppCompatActivity {
    public static Activity wifiActivity;
    private WifiManager mWifiManager;
    private TextView networkInfoTextView, wifiInfoTextView, status;
    public static ListView listView;
    private ConnectivityManager mConnectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
    }
}