package com.example.slide_09;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.slider.Slider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Slider volume;
    Switch silent;
    Button commit;
    Button show;
    Button fileStream;
    EditText albumName;
    Button fileStream2;
    Button fileStream3;
    Button dbInsert;
    Button dbShow;
    Button dbDelete;
    EditText key;
    EditText value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volume = findViewById(R.id.volume);
        silent = findViewById(R.id.silent);
        commit = findViewById(R.id.sharedPrefCommit);
        show = findViewById(R.id.sharedPrefShow);
        fileStream = findViewById(R.id.fileStream);
        albumName = findViewById(R.id.albumName);
        fileStream2 = findViewById(R.id.fileStream2);
        fileStream3 = findViewById(R.id.fileStream3);
        key = findViewById(R.id.key);
        value = findViewById(R.id.value);
        dbInsert = findViewById(R.id.insert);
        dbShow = findViewById(R.id.show);
        dbDelete = findViewById(R.id.delete);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("silentMode", silent.isChecked());
                editor.putInt("volume", (int) (volume.getValue()*100));
                editor.commit();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("settings", 0);
                Boolean silentMode = sharedPref.getBoolean("silentMode", false);
                Integer volume = sharedPref.getInt("volume", 75);

                String message = (silentMode ? "Device muted. " : "Device unmuted. ")
                        + "Volume: " + volume + "%";

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        fileStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                startActivityForResult(intent, 7979);
            }
        });

        fileStream2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sdcard = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                File dirs = new File(sdcard.getAbsolutePath());

                if(dirs.exists()) {
                    File[] files = dirs.listFiles();
                    String path = "";
                    for (File f: files) {
                         String full = f.getAbsolutePath();
                        path += full.substring(29) + "\n";
                    }
                    TextView text = findViewById(R.id.pathText);
                    text.setText(path);
                }
            }
        });

        fileStream3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String album = albumName.getText().toString();
                File img = getAlbumStorageDir(album);
                deleteExternalStoragePrivateFile();

                File test = getExternalFilesDir(null);
                String path = test.getAbsolutePath();

                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();

            }
        });

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = key.getText().toString();
                String subtitle = value.getText().toString();
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
            }
        });

        dbShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String[] projection = {
                        FeedReaderContract.FeedEntry._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
                };

                String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";

                String[] selectionArgs = { key.getText().toString() };

                String sortOrder =
                        FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

                Cursor cursor = db.query(
                        FeedReaderContract.FeedEntry.TABLE_NAME,   // tables
                        projection,             // columns
                        selection,              // columns for the WHERE clause
                        selectionArgs,          // values for the WHERE clause
                        null,                   // group by
                        null,                   // filter by row groups
                        sortOrder               // The sort order
                );

                String item = "";

                while(cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                    String itemTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
                    String itemSubtitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));
                    item += itemId + " - " + itemTitle + " - " + itemSubtitle + "\n";
                }

                Log.i("items", item);
            }
        });

        dbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
                String[] selectionArgs = { key.getText().toString() };
                int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 7979 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    if (inputStream != null) {
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(isr);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        inputStream.close();
                        String fileContent = sb.toString();
                        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "File not readable", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error reading file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("TAG", "MSG");
        }
        return file;
    }

    void deleteExternalStoragePrivateFile() {
        File file = new File(getExternalFilesDir(null), "DemoFile.jpg");
        if (file != null) {
            file.delete();
        }
    }
}