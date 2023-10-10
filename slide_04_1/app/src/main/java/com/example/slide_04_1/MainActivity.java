package com.example.slide_04_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    TextView selection;

    // Define an array of items for the Spinner
    String[] items = {"Android", "IPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_donate);
//        setContentView(R.layout.donate_linear);
//        setContentView(R.layout.donate_relative);
//        setContentView(R.layout.donate_table);
//        setContentView(R.layout.donate_constraint);


//        NumberPicker numberPicker = findViewById(R.id.numberPicker);
//        numberPicker.setMinValue(0);
//        numberPicker.setMaxValue(1000);
//        numberPicker.setValue(999);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selection = (TextView) findViewById(R.id.selection);
        selection.setText(items[0]);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                items);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        View mainLayout = findViewById(R.id.myLinearLayout);
        mainLayout.setOnClickListener(this);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spin.setSelection(0);
            }
        });
    }
    public void onItemSelected(AdapterView<?> parent,
                               View v, int position, long id) {
        selection.setText(items[position]);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        selection.setText(items[0]);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        selection.setText(items[0]);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main) {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        else if (id == R.id.linear_1) {
            setContentView(R.layout.donate_linear);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        else if (id == R.id.relative_1) {
            setContentView(R.layout.donate_relative);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        else if (id == R.id.table_1) {
            setContentView(R.layout.donate_table);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);}
        else if (id == R.id.constraint_1) {
            setContentView(R.layout.donate_constraint);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);}
        else if (id == R.id.linear_2) {
            setContentView(R.layout.order_linear);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);}
        else if (id == R.id.relative_2) {
            setContentView(R.layout.order_relative);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);}
        else if (id == R.id.table_2) {
            setContentView(R.layout.order_table);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);}
        else if (id == R.id.constraint_2) {
            setContentView(R.layout.order_constraint);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);}

        return super.onOptionsItemSelected(item);
    }
}