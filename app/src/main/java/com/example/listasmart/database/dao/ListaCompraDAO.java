package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.ListaCompra;

public class ListaCompraDAO extends AbstrataDAO {

    public ListaCompraDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long criarListaPadrao(Long idUsuario) {

        long idLista;

        try {

            Open();

            ContentValues values = new ContentValues();

            values.put(
                    ListaCompra.COLUNA_ID_USUARIO,
                    idUsuario
            );

            values.put(
                    ListaCompra.COLUNA_NOME_LISTA,
                    "Lista Rápida"
            );

            idLista = db.insert(
                    ListaCompra.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idLista;
    }

    public Long buscarListaUsuario(Long idUsuario) {

        Long idLista = null;

        try {

            Open();

            String sql =
                    "SELECT * FROM " +
                            ListaCompra.NOME_TABELA +
                            " WHERE " +
                            ListaCompra.COLUNA_ID_USUARIO +
                            " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{
                            String.valueOf(idUsuario)
                    }
            );

            if (cursor.moveToFirst()) {

                idLista =
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow(
                                        ListaCompra.COLUNA_ID
                                )
                        );
            }

            cursor.close();

        } finally {
            Close();
        }

        return idLista;
    }
}