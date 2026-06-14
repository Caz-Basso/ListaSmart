package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Categoria;

public class CategoriaDAO extends AbstrataDAO {

    public CategoriaDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(Categoria categoria) {
        long idInserido;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(Categoria.COLUNA_NOME, categoria.getNome());

            idInserido = db.insert(
                    Categoria.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idInserido;
    }

    public int contarCategorias() {
        int total = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + Categoria.NOME_TABELA,
                    null
            );

            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }

            cursor.close();

        } finally {
            Close();
        }

        return total;
    }

    public void inserirCategoriasIniciais() {
        if (contarCategorias() > 0) {
            return;
        }

        inserirCategoria("Alimentos");
        inserirCategoria("Bebidas");
        inserirCategoria("Higiene");
        inserirCategoria("Limpeza");
    }

    private void inserirCategoria(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        insert(categoria);
    }
    public Long buscarIdPorNome(String nome) {
        Long idCategoria = null;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT " + Categoria.COLUNA_ID +
                            " FROM " + Categoria.NOME_TABELA +
                            " WHERE " + Categoria.COLUNA_NOME + " = ?",
                    new String[]{nome}
            );

            if (cursor.moveToFirst()) {
                idCategoria = cursor.getLong(
                        cursor.getColumnIndexOrThrow(Categoria.COLUNA_ID)
                );
            }

            cursor.close();

        } finally {
            Close();
        }

        return idCategoria;
    }
}