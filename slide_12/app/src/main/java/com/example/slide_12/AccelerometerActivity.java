package com.example.slide_12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    ConstraintLayout screen;
    SensorManager manager;
    Sensor accelerometer;
    Button enableAccelerometer;
    int prevColor = Color.WHITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        screen = findViewById(R.id.screen);

        enableAccelerometer = findViewById(R.id.enableAccelerometer);
        enableAccelerometer.setEnabled(false);

        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            enableAccelerometer.setEnabled(true);
        }
    }

    public void triggerAccelerometer(View view) {
        if (enableAccelerometer.getText().equals("DISABLED") ) {
            enableAccelerometer.setText("ENABLED");
        }
        else {
            enableAccelerometer.setText("DISABLED");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}