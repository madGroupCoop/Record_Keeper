package com.example.recordkeeper;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract() {
    }
    public static final class RecordsEntry implements BaseColumns {
        public static final String TABLE_NAME = "recordList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}

