package com.example.listasmart.database.dao;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Endereco;

public class EnderecoDAO extends AbstrataDAO {

    public EnderecoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }
    public Endereco buscarPorUsuario(Long idUsuario) {

        Endereco endereco = null;

        try {
            Open();

            String sql =
                    "SELECT * FROM " + Endereco.NOME_TABELA +
                            " WHERE " + Endereco.COLUNA_ID_USUARIO + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idUsuario)}
            );

            if (cursor.moveToFirst()) {

                endereco = new Endereco();

                endereco.setId(
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_ID
                                )
                        )
                );

                endereco.setIdUsuario(idUsuario);

                endereco.setCep(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_CEP
                                )
                        )
                );

                endereco.setCidade(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_CIDADE
                                )
                        )
                );

                endereco.setEstado(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_ESTADO
                                )
                        )
                );

                endereco.setRua(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_RUA
                                )
                        )
                );

                endereco.setNumero(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_NUMERO
                                )
                        )
                );

                endereco.setComplemento(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_COMPLEMENTO
                                )
                        )
                );

                endereco.setNomeContato(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_NOME_CONTATO
                                )
                        )
                );

                endereco.setTelefoneContato(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        Endereco.COLUNA_TELEFONE_CONTATO
                                )
                        )
                );
            }

            cursor.close();

        } finally {
            Close();
        }

        return endereco;
    }
    public long salvarOuAtualizar(Endereco endereco) {

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(Endereco.COLUNA_ID_USUARIO, endereco.getIdUsuario());
            values.put(Endereco.COLUNA_CEP, endereco.getCep());
            values.put(Endereco.COLUNA_CIDADE, endereco.getCidade());
            values.put(Endereco.COLUNA_ESTADO, endereco.getEstado());
            values.put(Endereco.COLUNA_RUA, endereco.getRua());
            values.put(Endereco.COLUNA_NUMERO, endereco.getNumero());
            values.put(Endereco.COLUNA_COMPLEMENTO, endereco.getComplemento());
            values.put(Endereco.COLUNA_NOME_CONTATO, endereco.getNomeContato());
            values.put(Endereco.COLUNA_TELEFONE_CONTATO, endereco.getTelefoneContato());

            Cursor cursor = db.rawQuery(
                    "SELECT " + Endereco.COLUNA_ID +
                            " FROM " + Endereco.NOME_TABELA +
                            " WHERE " + Endereco.COLUNA_ID_USUARIO + " = ?",
                    new String[]{String.valueOf(endereco.getIdUsuario())}
            );

            boolean existe = cursor.moveToFirst();

            cursor.close();

            if (existe) {
                return db.update(
                        Endereco.NOME_TABELA,
                        values,
                        Endereco.COLUNA_ID_USUARIO + " = ?",
                        new String[]{String.valueOf(endereco.getIdUsuario())}
                );
            } else {
                return db.insert(
                        Endereco.NOME_TABELA,
                        null,
                        values
                );
            }

        } finally {
            Close();
        }
    }
}