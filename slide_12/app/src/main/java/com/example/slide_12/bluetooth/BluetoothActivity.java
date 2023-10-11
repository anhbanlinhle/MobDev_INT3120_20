package com.example.slide_12.bluetooth;

import static android.bluetooth.BluetoothAdapter.SCAN_MODE_CONNECTABLE;
import static android.bluetooth.BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE;
import static android.bluetooth.BluetoothAdapter.SCAN_MODE_NONE;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.slide_12.R;

import java.util.ArrayList;

public class BluetoothActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private Button scan;
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_SCAN}, 1);
                    return;
                }

                if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }

                if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

                if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 1);
                }

                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                int scanMode = bluetoothAdapter.getScanMode();

                switch (scanMode) {
                    case SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Toast.makeText(BluetoothActivity.this, "Connectable / Discoverable", Toast.LENGTH_SHORT).show();
                        break;
                    case SCAN_MODE_CONNECTABLE:
                        Toast.makeText(BluetoothActivity.this, "Connectable", Toast.LENGTH_SHORT).show();
                        break;
                    case SCAN_MODE_NONE:
                        Toast.makeText(BluetoothActivity.this, "None", Toast.LENGTH_SHORT).show();
                        break;
                }

                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }


                if (scanMode != SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    if (ContextCompat.checkSelfPermission(BluetoothActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(BluetoothActivity.this,
                                new String[]{android.Manifest.permission.BLUETOOTH_CONNECT},
                                1);
                    } else {
                        int requestCode = 1;
                        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivityForResult(discoverableIntent, requestCode);
                    }
                } else {
                    bluetoothAdapter.startDiscovery();

                    BluetoothReceiver receiver = new BluetoothReceiver();
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(receiver, filter);
                }
            }
        });
    }

    private class BluetoothReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.S)
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    return;
                }
                String deviceName = device.getName();

                ListView listView = findViewById(R.id.list_view);
                ArrayList<BluetoothDevice> devices = new ArrayList<>();
                devices.add(device);
                ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, devices);
                listView.setAdapter(adapter);

                Toast.makeText(context, "Found device: " + deviceName, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(new BluetoothReceiver());
    }
}