package com.example.slide_10;

import static com.example.slide_10.UserProvider.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    UserProvider provider;
    EditText name;
    EditText phone;
    Button post;
    Button get;
    Button put;
    Button delete;
    TextView result;
    FloatingActionButton contact;
    FloatingActionButton addcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        post = findViewById(R.id.create);
        get = findViewById(R.id.read);
        put = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        result = findViewById(R.id.result);
        contact = findViewById(R.id.contact);
        addcontact = findViewById(R.id.addcontact);

        provider = new UserProvider();


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read();
            }
        });

        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContacts();
            }
        });

        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });
    }

    public void create() {
        ContentValues values = new ContentValues();

        values.put(UserProvider.name, ((EditText) name).getText().toString());
        values.put(UserProvider.phone, ((EditText) phone).getText().toString());

        getContentResolver().insert(UserProvider.URI, values);

        Toast.makeText(getBaseContext(), "New record inserted", Toast.LENGTH_LONG).show();
        read();
    }

    public void read() {
        Cursor cursor = getContentResolver().query(Uri.parse(URL), null, null, null, null);

        if(cursor.moveToFirst()) {
            StringBuilder strBuild = new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))
                        + " - "+ cursor.getString(cursor.getColumnIndex("name"))
                        + " - "+ cursor.getString(cursor.getColumnIndex("phone")));
                cursor.moveToNext();
            }

            result.setText(strBuild);
        }
        else {
            result.setText("No Records Found");
        }
    }

    public void update() {
        String byName = name.getText().toString();
        String byPhone = phone.getText().toString();

        ContentValues values = new ContentValues();
        values.put(UserProvider.name, byName);
        values.put(UserProvider.phone, byPhone);

        if (byName != null) {
            String selection = UserProvider.name + " = ?";
            String[] selectionArgs = {byName};

            getContentResolver().update(UserProvider.URI, values, selection, selectionArgs);
        }
        if (byPhone != null) {
            String selection = UserProvider.phone + " = ?";
            String[] selectionArgs = {byPhone};

            getContentResolver().update(UserProvider.URI, values, selection, selectionArgs);
        }
        read();
    }

    public void delete() {
        String byName = name.getText().toString();
        String byPhone = phone.getText().toString();
        if (byName != null) {
            String selection = UserProvider.name + " = ?";
            String[] selectionArgs = {byName};

            int rowsDeleted = getContentResolver().delete(UserProvider.URI, selection, selectionArgs);

            if (rowsDeleted > 0) {
                Toast.makeText(getBaseContext(), rowsDeleted + " name row(s) deleted: " + byName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "No name row deleted", Toast.LENGTH_LONG).show();
            }
        }
        if (byPhone != null) {
            String selection = UserProvider.phone + " = ?";
            String[] selectionArgs = {byPhone};

            int rowsDeleted = getContentResolver().delete(UserProvider.URI, selection, selectionArgs);

            if (rowsDeleted > 0) {
                Toast.makeText(getBaseContext(), rowsDeleted + " phone row(s) deleted: " + byPhone, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "No phone row deleted", Toast.LENGTH_LONG).show();
            }
        }
        read();
    }

    public void showContacts() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, 0);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            String allContacts = "";
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                allContacts += name + " - " + number + "\n";
            }
            result.setText(allContacts);
        }
    }

    public void addContact() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_CONTACTS}, 0);
//        }
//        else {
//            String byName = name.getText().toString();
//            String byPhone = phone.getText().toString();
//            ContentValues values = new ContentValues();
//            values.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, byName);
//            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, byPhone);
//
//            ContentResolver contentResolver = getContentResolver();
//            Uri uri = contentResolver.insert(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, values);
//
//            if (uri != null) {
//                Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "Unable to add", Toast.LENGTH_LONG).show();
//            }
//        }
    }
}