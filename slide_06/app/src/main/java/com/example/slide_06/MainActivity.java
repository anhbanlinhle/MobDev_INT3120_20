package com.example.slide_06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button context;
    Button popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = findViewById(R.id.context_button);
        registerForContextMenu(context);

        popup = findViewById(R.id.popup_button);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.demo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item2 || id == R.id.item3 || id == R.id.item4 || id == R.id.item7 || id == R.id.item8) {
            Toast.makeText(MainActivity.this, item.getTitle() + " selected", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.item1 || id == R.id.item6) {
            Toast.makeText(MainActivity.this, item.getTitle() + " opened", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        menu.setHeaderTitle("Context Menu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.demo_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item2 || id == R.id.item3 || id == R.id.item4 || id == R.id.item7 || id == R.id.item8) {
            Toast.makeText(MainActivity.this, item.getTitle() + " selected", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.item1 || id == R.id.item6) {
            Toast.makeText(MainActivity.this, item.getTitle() + " opened", Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, this.popup);
        popupMenu.inflate(R.menu.demo_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item2 || id == R.id.item3 || id == R.id.item4 || id == R.id.item7 || id == R.id.item8) {
                    Toast.makeText(MainActivity.this, item.getTitle() + " selected", Toast.LENGTH_LONG).show();
                }
                else if (id == R.id.item1 || id == R.id.item6) {
                    Toast.makeText(MainActivity.this, item.getTitle() + " opened", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        popupMenu.show();
    }
}