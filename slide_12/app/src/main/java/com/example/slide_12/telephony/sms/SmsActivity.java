package com.example.slide_12.telephony.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.slide_12.R;

public class SmsActivity extends AppCompatActivity {
    private EditText phone, message;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);


        phone = findViewById(R.id.phoneNumber);
        message = findViewById(R.id.message);
        send = findViewById(R.id.sendMessage);

        if (ActivityCompat.checkSelfPermission(SmsActivity.this, android.Manifest.permission.SEND_SMS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SmsActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 1);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString();
                String messageContent = message.getText().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", messageContent);
                startActivity(intent);
            }
        });
    }
}