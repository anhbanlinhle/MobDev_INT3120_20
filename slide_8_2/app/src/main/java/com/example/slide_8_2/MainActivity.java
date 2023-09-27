package com.example.slide_8_2;

import static com.example.slide_8_2.MyBroadcastReceiver.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.slide_8_2.MyBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.example.action.MY_SENDER");
        registerReceiver(receiver, intentFilter);
        text.setText(message);
    }
}