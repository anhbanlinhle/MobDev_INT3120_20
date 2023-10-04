package com.example.slide_10;

import static com.example.slide_10.MyDatabaseContract.DbEntry.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.UserDictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;


public class UserProvider extends ContentProvider {
    static final String AUTHORITY = "com.example.slide_10";
    static final String URL = "content://" + AUTHORITY + "/users";
    public static final Uri URI = Uri.parse(URL);
    static final String id = "id";
    static final String name = "name";
    static final String phone = "phone";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;
    private SQLiteDatabase db;

    static {

        // to match the content URI
        // every time user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // to access whole table
        uriMatcher.addURI(AUTHORITY, "users", uriCode);

        // to access a particular row
        // of the table
        uriMatcher.addURI(AUTHORITY, "users/*", uriCode);
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = id;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == uriCode) {
            return URL;
        }
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME, "", values);
        // check whether the record has been inserted or not by rowId value
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        // if rowId <= 0 then the record has not been inserted
        throw new SQLiteException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
