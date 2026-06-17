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
    public int contarAnalises(Long idUsuario) {

        int total = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " +
                            HistoricoAnalise.NOME_TABELA +
                            " WHERE " +
                            HistoricoAnalise.COLUNA_ID_USUARIO +
                            " = ?",
                    new String[]{
                            String.valueOf(idUsuario)
                    }
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
    public double calcularEconomiaTotal(Long idUsuario) {

        double total = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT SUM(" +
                            HistoricoAnalise.COLUNA_ECONOMIA +
                            ") FROM " +
                            HistoricoAnalise.NOME_TABELA +
                            " WHERE " +
                            HistoricoAnalise.COLUNA_ID_USUARIO +
                            " = ?",
                    new String[]{
                            String.valueOf(idUsuario)
                    }
            );

            if (cursor.moveToFirst()) {
                total = cursor.getDouble(0);
            }

            cursor.close();

        } finally {
            Close();
        }

        return total;
    }
    public double buscarMaiorEconomia(Long idUsuario) {

        double maiorEconomia = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT MAX(" +
                            HistoricoAnalise.COLUNA_ECONOMIA +
                            ") FROM " +
                            HistoricoAnalise.NOME_TABELA +
                            " WHERE " +
                            HistoricoAnalise.COLUNA_ID_USUARIO +
                            " = ?",
                    new String[]{
                            String.valueOf(idUsuario)
                    }
            );

            if (cursor.moveToFirst()) {
                maiorEconomia = cursor.getDouble(0);
            }

            cursor.close();

        } finally {
            Close();
        }

        return maiorEconomia;
    }
    public String buscarMercadoMaisEscolhido(Long idUsuario) {

        String mercado = "Nenhum";

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            HistoricoAnalise.COLUNA_MERCADO_RECOMENDADO +
                            ", COUNT(*) AS total " +
                            "FROM " +
                            HistoricoAnalise.NOME_TABELA +
                            " WHERE " +
                            HistoricoAnalise.COLUNA_ID_USUARIO +
                            " = ? " +
                            "GROUP BY " +
                            HistoricoAnalise.COLUNA_MERCADO_RECOMENDADO +
                            " ORDER BY total DESC " +
                            "LIMIT 1",
                    new String[]{
                            String.valueOf(idUsuario)
                    }
            );

            if (cursor.moveToFirst()) {

                mercado = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                HistoricoAnalise.COLUNA_MERCADO_RECOMENDADO
                        )
                );
            }

            cursor.close();

        } finally {
            Close();
        }

        return mercado;
    }
}