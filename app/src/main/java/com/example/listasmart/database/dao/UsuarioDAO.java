package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Usuario;


public class UsuarioDAO extends AbstrataDAO {

    public UsuarioDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(Usuario usuario) {

        long idInserido;

        try {

            Open();

            ContentValues values = new ContentValues();

            values.put(
                    Usuario.COLUNA_NOME_USUARIO,
                    usuario.getNomeUsuario()
            );

            values.put(
                    Usuario.COLUNA_NOME_COMPLETO,
                    usuario.getNomeCompleto()
            );

            values.put(
                    Usuario.COLUNA_EMAIL,
                    usuario.getEmail()
            );

            values.put(
                    Usuario.COLUNA_SENHA,
                    usuario.getSenha()
            );

            idInserido = db.insert(
                    Usuario.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idInserido;
    }
    public boolean validarLogin(String email, String senha) {

        boolean loginValido = false;

        try {
            Open();

            String sql = "SELECT * FROM " + Usuario.NOME_TABELA +
                    " WHERE " + Usuario.COLUNA_EMAIL + " = ? AND " +
                    Usuario.COLUNA_SENHA + " = ?";

            Cursor cursor = db.rawQuery(sql, new String[]{email, senha});

            if (cursor.moveToFirst()) {
                loginValido = true;
            }

            cursor.close();

        } finally {
            Close();
        }

        return loginValido;
    }
}