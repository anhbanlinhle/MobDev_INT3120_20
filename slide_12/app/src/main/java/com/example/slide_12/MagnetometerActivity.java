package com.example.slide_12;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MagnetometerActivity extends AppCompatActivity implements SensorEventListener {
    TextView degree;
    ImageView compass;
    SensorManager manager;
    Sensor magnetometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);

        degree = findViewById(R.id.degree);
        compass = findViewById(R.id.compass);

        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        magnetometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer == null) {
            degree.setText("No magnetometer found!!");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    public void handleSensor(float azimuth) {
        if (azimuth < 0) {
            azimuth += 360;
        }
        compass.setRotation(azimuth);
        degree.setText(String.format("%.2f°", azimuth));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float azimuth = (float) sensorEvent.values[0];
        handleSensor(azimuth);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}