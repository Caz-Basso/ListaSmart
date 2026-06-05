package com.example.listasmart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listasmart.database.model.Usuario;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "listasmart.db";
    private static final int VERSAO_BANCO = 1;

    public DBOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Usuario.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

    }
}