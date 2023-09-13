package com.example.lesson_4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.order_base);
//        setContentView(R.layout.order_linear);
//        setContentView(R.layout.order_relative);
        setContentView(R.layout.order_constraint);
//        setContentView(R.layout.order_table);
    }
}