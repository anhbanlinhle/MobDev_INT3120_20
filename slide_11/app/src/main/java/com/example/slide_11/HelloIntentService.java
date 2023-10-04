package com.example.slide_11;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class HelloIntentService extends IntentService {
    public HelloIntentService() {
        super("HelloIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
