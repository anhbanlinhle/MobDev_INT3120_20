package com.example.slide_08_1;

import static com.example.slide_08_1.MyBroadcastReceiver.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MyBroadcastReceiver receiver;
    TextView text;
    Button refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        refresh = findViewById(R.id.f5);

        update();
        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.example.action.MY_SENDER");
        registerReceiver(receiver, intentFilter);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });


    }
    public void update() {
//        receiver = new MyBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter("com.example.action.MY_SENDER");
//        registerReceiver(receiver, intentFilter);
        if (receiver != null) {
            text.setText(message);
        }
        else {
            text.setText("unregistered");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
            Log.i("Regist", "unregisted");
        }
    }
}