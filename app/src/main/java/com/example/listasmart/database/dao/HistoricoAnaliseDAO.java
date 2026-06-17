package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.HistoricoAnalise;

import java.util.ArrayList;

public class HistoricoAnaliseDAO extends AbstrataDAO {

    public HistoricoAnaliseDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(HistoricoAnalise historico) {
        long idInserido;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(HistoricoAnalise.COLUNA_ID_USUARIO, historico.getIdUsuario());
            values.put(HistoricoAnalise.COLUNA_ID_LISTA, historico.getIdLista());
            values.put(HistoricoAnalise.COLUNA_MERCADO_RECOMENDADO, historico.getMercadoRecomendado());
            values.put(HistoricoAnalise.COLUNA_VALOR_TOTAL, historico.getValorTotal());
            values.put(HistoricoAnalise.COLUNA_ECONOMIA, historico.getEconomia());

            idInserido = db.insert(
                    HistoricoAnalise.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idInserido;
    }

    public ArrayList<HistoricoAnalise> listarPorUsuario(Long idUsuario) {
        ArrayList<HistoricoAnalise> historicos = new ArrayList<>();

        try {
            Open();

            String sql =
                    "SELECT * FROM " + HistoricoAnalise.NOME_TABELA +
                            " WHERE " + HistoricoAnalise.COLUNA_ID_USUARIO + " = ? " +
                            " ORDER BY " + HistoricoAnalise.COLUNA_DATA_ANALISE + " DESC";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idUsuario)}
            );

            while (cursor.moveToNext()) {
                HistoricoAnalise historico = new HistoricoAnalise();

                historico.setId(cursor.getLong(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_ID)
                ));

                historico.setIdUsuario(cursor.getLong(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_ID_USUARIO)
                ));

                historico.setIdLista(cursor.getLong(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_ID_LISTA)
                ));

                historico.setMercadoRecomendado(cursor.getString(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_MERCADO_RECOMENDADO)
                ));

                historico.setValorTotal(cursor.getDouble(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_VALOR_TOTAL)
                ));

                historico.setEconomia(cursor.getDouble(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_ECONOMIA)
                ));

                historico.setDataAnalise(cursor.getString(
                        cursor.getColumnIndexOrThrow(HistoricoAnalise.COLUNA_DATA_ANALISE)
                ));

                historicos.add(historico);
            }

            cursor.close();

        } finally {
            Close();
        }

        return historicos;
    }
}