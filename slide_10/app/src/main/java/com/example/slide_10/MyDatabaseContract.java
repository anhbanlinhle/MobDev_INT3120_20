package com.example.slide_10;

import android.provider.BaseColumns;

public final class MyDatabaseContract {
    private MyDatabaseContract() {}

    public static class DbEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PHONE = "phone";

    }
}
