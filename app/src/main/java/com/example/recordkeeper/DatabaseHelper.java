package com.example.recordkeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.recordkeeper.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "recordKeeper.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RECORDITEMS_TABLE = "CREATE TABLE " +
                RecordsEntry.TABLE_NAME + " (" +
                RecordsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecordsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                RecordsEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_RECORDITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  RecordsEntry.TABLE_NAME);
        onCreate(db);
    }


}

