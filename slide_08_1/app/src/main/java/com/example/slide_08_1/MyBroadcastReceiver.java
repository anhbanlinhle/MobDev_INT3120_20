package com.example.slide_08_1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static String message;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            message = intent.getStringExtra(Intent.EXTRA_TEXT);
        }
        else {
            message = "cc";
        }
    }
}
