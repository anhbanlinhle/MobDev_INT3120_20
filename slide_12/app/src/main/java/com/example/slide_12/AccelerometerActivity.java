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
import android.widget.TextView;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    ConstraintLayout screen;
    SensorManager manager;
    Sensor accelerometer;
    Button enableAccelerometer;
    TextView xText, yText, zText;
    int prevColor = Color.WHITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        screen = findViewById(R.id.screen);
        enableAccelerometer = findViewById(R.id.enableAccelerometer);
        enableAccelerometer.setEnabled(false);
        xText = findViewById(R.id.x);
        yText = findViewById(R.id.y);
        zText = findViewById(R.id.z);

        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            enableAccelerometer.setEnabled(true);
        }
    }

    public void triggerAccelerometer(View view) {
        if (enableAccelerometer.getText().equals("DISABLED") ) {
            enableAccelerometer.setText("ENABLED");
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            enableAccelerometer.setText("DISABLED");
            manager.unregisterListener(this);
        }
    }

    private void handleSensor(float x, float y, float z) {
        xText.setText("x-a: " + x);
        yText.setText("y-a: " + y);
        zText.setText("z-a: " + z);

        int color = prevColor;

        if (x > 5 || y > 5 || z > 5) {
            color = Color.WHITE;
        } else if (x < -5 || y < -5 || z < -5) {
            color = Color.BLACK;
        }

        if (color != prevColor) {
            xText.setTextColor(prevColor);
            yText.setTextColor(prevColor);
            zText.setTextColor(prevColor);
            prevColor = color;
            screen.setBackgroundColor(color);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        handleSensor(x, y, z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}