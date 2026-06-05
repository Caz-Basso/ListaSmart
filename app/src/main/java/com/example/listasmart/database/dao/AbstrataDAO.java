package com.example.listasmart.database.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.listasmart.database.DBOpenHelper;

public abstract class AbstrataDAO {

    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected final void Open() {
        db = db_helper.getWritableDatabase();
    }

    protected final void Close() {
        db_helper.close();
    }
}