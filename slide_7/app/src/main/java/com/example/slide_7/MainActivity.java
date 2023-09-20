package com.example.slide_7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button sendMsg;
    Button call;
    Button search;
    Button send;
    Button map;
    Button music;
    EditText editTextFullName;
    TextView textFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMsg = findViewById(R.id.sendMsg);
        call = findViewById(R.id.btnCall);
        search = findViewById(R.id.btnSearch);
        send = findViewById(R.id.btnSend);
        map = findViewById(R.id.geo);
        music = findViewById(R.id.music);

        editTextFullName = findViewById(R.id.editText);
        textFeedback = findViewById(R.id.feedback);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText phoneNumber = findViewById(R.id.editCall);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.getText().toString()));
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchTerm = findViewById(R.id.editSearch);
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, searchTerm.getText().toString());
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText sendContent = findViewById(R.id.editSend);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, sendContent.getText().toString());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoCode = "geo:41.5020952,-81.6789717";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoCode));
                startActivity(intent);
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                startActivity(intent);
            }
        });
    }

    public void sendMessage() {
        String fullName = editTextFullName.getText().toString();
        String message = "Hello, Please say hello to me!";

        Intent intent = new Intent(this, GreetingActivity.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("message", message);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == 100) {
            String feedback = data.getStringExtra("feedback");
            textFeedback.setText(feedback);
        }
        else {
            textFeedback.setText("??");
        }
    }
}