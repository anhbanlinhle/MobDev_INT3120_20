package com.example.slide_12.telephony.call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.slide_12.R;

public class CallActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private Button callButton;
    private TextView phoneInfo;
    private PhoneStateChangedReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        phoneNumber = findViewById(R.id.editTextPhoneNumber);
        callButton = findViewById(R.id.buttonCall);
        phoneInfo = findViewById(R.id.phoneInfo);

        callButton.setOnClickListener(v -> {
            String phoneNumberStr = phoneNumber.getText().toString();
            if (!phoneNumberStr.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumberStr));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String srvcName = Context.TELEPHONY_SERVICE;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(srvcName);
        String networkOperatorName = telephonyManager.getNetworkOperatorName();
        String simOperatorName = telephonyManager.getSimOperatorName();
        int phoneType = telephonyManager.getPhoneType();
        String info = "Network Operator Name: " + networkOperatorName + "\n" +
                "SIM Operator Name: " + simOperatorName + "\n" +
                "Phone Type: " + phoneType;
        this.phoneInfo.setText(info);

        receiver = new PhoneStateChangedReceiver();
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);
    }
}