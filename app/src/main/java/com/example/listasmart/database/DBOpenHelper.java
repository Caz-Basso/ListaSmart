package com.example.listasmart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.database.model.ListaCompra;
import com.example.listasmart.database.model.ItemLista;
import com.example.listasmart.database.model.Supermercado;
import com.example.listasmart.database.model.PrecoSupermercado;
import com.example.listasmart.database.model.Categoria;
import com.example.listasmart.database.model.HistoricoAnalise;
import com.example.listasmart.database.model.Endereco;

import com.example.listasmart.database.model.Usuario;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "listasmart.db";
    private static final int VERSAO_BANCO = 5;

    public DBOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Usuario.CREATE_TABLE);
        db.execSQL(Categoria.CREATE_TABLE);
        db.execSQL(Produto.CREATE_TABLE);
        db.execSQL(ListaCompra.CREATE_TABLE);
        db.execSQL(ItemLista.CREATE_TABLE);
        db.execSQL(Supermercado.CREATE_TABLE);
        db.execSQL(PrecoSupermercado.CREATE_TABLE);
        db.execSQL(HistoricoAnalise.CREATE_TABLE);
        db.execSQL(Endereco.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL(PrecoSupermercado.DROP_TABLE);
        db.execSQL(Supermercado.DROP_TABLE);
        db.execSQL(ItemLista.DROP_TABLE);
        db.execSQL(ListaCompra.DROP_TABLE);
        db.execSQL(Produto.DROP_TABLE);
        db.execSQL(Categoria.DROP_TABLE);
        db.execSQL(Usuario.DROP_TABLE);
        db.execSQL(HistoricoAnalise.DROP_TABLE);
        db.execSQL(Endereco.DROP_TABLE);
        onCreate(db);
    }
}