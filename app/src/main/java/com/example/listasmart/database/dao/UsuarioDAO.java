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
    public Long buscarIdPorLogin(String email, String senha) {

        Long idUsuario = null;

        try {
            Open();

            String sql = "SELECT " + Usuario.COLUNA_ID +
                    " FROM " + Usuario.NOME_TABELA +
                    " WHERE " + Usuario.COLUNA_EMAIL + " = ? AND " +
                    Usuario.COLUNA_SENHA + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{email, senha}
            );

            if (cursor.moveToFirst()) {
                idUsuario = cursor.getLong(
                        cursor.getColumnIndexOrThrow(Usuario.COLUNA_ID)
                );
            }

            cursor.close();

        } finally {
            Close();
        }

        return idUsuario;
    }
    public Usuario buscarPorId(Long idUsuario) {

        Usuario usuario = null;

        try {
            Open();

            String sql = "SELECT * FROM " + Usuario.NOME_TABELA +
                    " WHERE " + Usuario.COLUNA_ID + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idUsuario)}
            );

            if (cursor.moveToFirst()) {
                usuario = new Usuario();

                usuario.setId(cursor.getLong(
                        cursor.getColumnIndexOrThrow(Usuario.COLUNA_ID)
                ));

                usuario.setNomeUsuario(cursor.getString(
                        cursor.getColumnIndexOrThrow(Usuario.COLUNA_NOME_USUARIO)
                ));

                usuario.setNomeCompleto(cursor.getString(
                        cursor.getColumnIndexOrThrow(Usuario.COLUNA_NOME_COMPLETO)
                ));

                usuario.setEmail(cursor.getString(
                        cursor.getColumnIndexOrThrow(Usuario.COLUNA_EMAIL)
                ));

                usuario.setSenha(cursor.getString(
                        cursor.getColumnIndexOrThrow(Usuario.COLUNA_SENHA)
                ));
            }

            cursor.close();

        } finally {
            Close();
        }

        return usuario;
    }
    public int atualizarPerfil(Long idUsuario, String nomeCompleto, String nomeUsuario) {

        int linhasAfetadas;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(Usuario.COLUNA_NOME_COMPLETO, nomeCompleto);
            values.put(Usuario.COLUNA_NOME_USUARIO, nomeUsuario);

            linhasAfetadas = db.update(
                    Usuario.NOME_TABELA,
                    values,
                    Usuario.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(idUsuario)}
            );

        } finally {
            Close();
        }

        return linhasAfetadas;
    }
    public int alterarEmail(Long idUsuario, String novoEmail) {

        int linhasAfetadas;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(Usuario.COLUNA_EMAIL, novoEmail);

            linhasAfetadas = db.update(
                    Usuario.NOME_TABELA,
                    values,
                    Usuario.COLUNA_ID + " = ?",
                    new String[]{String.valueOf(idUsuario)}
            );

        } finally {
            Close();
        }

        return linhasAfetadas;
    }
    public boolean validarSenha(Long idUsuario, String senha) {

        boolean senhaValida = false;

        try {
            Open();

            String sql =
                    "SELECT * FROM " + Usuario.NOME_TABELA +
                            " WHERE " + Usuario.COLUNA_ID + " = ? AND " +
                            Usuario.COLUNA_SENHA + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{
                            String.valueOf(idUsuario),
                            senha
                    }
            );

            senhaValida = cursor.moveToFirst();

            cursor.close();

        } finally {
            Close();
        }

        return senhaValida;
    }
    public int alterarSenha(Long idUsuario, String novaSenha) {

        int linhasAfetadas;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(Usuario.COLUNA_SENHA, novaSenha);

            linhasAfetadas = db.update(
                    Usuario.NOME_TABELA,
                    values,
                    Usuario.COLUNA_ID + " = ?",
                    new String[]{
                            String.valueOf(idUsuario)
                    }
            );

        } finally {
            Close();
        }

        return linhasAfetadas;
    }
    public int excluirUsuario(Long idUsuario) {

        int linhasRemovidas;

        try {
            Open();

            linhasRemovidas = db.delete(
                    Usuario.NOME_TABELA,
                    Usuario.COLUNA_ID + " = ?",
                    new String[]{
                            String.valueOf(idUsuario)
                    }
            );

        } finally {
            Close();
        }

        return linhasRemovidas;
    }
    public boolean existeNomeUsuario(String nomeUsuario) {

        boolean existe = false;

        try {
            Open();

            String sql =
                    "SELECT " + Usuario.COLUNA_ID +
                            " FROM " + Usuario.NOME_TABELA +
                            " WHERE " + Usuario.COLUNA_NOME_USUARIO + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{nomeUsuario}
            );

            existe = cursor.moveToFirst();

            cursor.close();

        } finally {
            Close();
        }

        return existe;
    }
    public boolean existeEmail(String email) {

        boolean existe = false;

        try {
            Open();

            String sql =
                    "SELECT " + Usuario.COLUNA_ID +
                            " FROM " + Usuario.NOME_TABELA +
                            " WHERE " + Usuario.COLUNA_EMAIL + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{email}
            );

            existe = cursor.moveToFirst();

            cursor.close();

        } finally {
            Close();
        }

        return existe;
    }
}