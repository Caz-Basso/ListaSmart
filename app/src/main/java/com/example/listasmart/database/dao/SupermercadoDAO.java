package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Supermercado;

import java.util.ArrayList;
import java.text.Normalizer;

public class SupermercadoDAO extends AbstrataDAO {

    public SupermercadoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(Supermercado supermercado) {
        long idInserido;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(Supermercado.COLUNA_NOME_FANTASIA, supermercado.getNomeFantasia());
            values.put(Supermercado.COLUNA_ENDERECO, supermercado.getEndereco());
            values.put(Supermercado.COLUNA_BAIRRO, supermercado.getBairro());
            values.put(Supermercado.COLUNA_CIDADE, supermercado.getCidade());
            values.put(Supermercado.COLUNA_ESTADO, supermercado.getEstado());
            values.put(Supermercado.COLUNA_LATITUDE, supermercado.getLatitude());
            values.put(Supermercado.COLUNA_LONGITUDE, supermercado.getLongitude());

            idInserido = db.insert(
                    Supermercado.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idInserido;
    }

    public int contarSupermercados() {
        int total = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + Supermercado.NOME_TABELA,
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

    public ArrayList<Supermercado> listar() {
        ArrayList<Supermercado> supermercados = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + Supermercado.NOME_TABELA,
                    null
            );

            while (cursor.moveToNext()) {
                Supermercado supermercado = new Supermercado();

                supermercado.setId(
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_ID)
                        )
                );

                supermercado.setNomeFantasia(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_NOME_FANTASIA)
                        )
                );

                supermercado.setEndereco(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_ENDERECO)
                        )
                );

                supermercado.setBairro(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_BAIRRO)
                        )
                );

                supermercado.setCidade(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_CIDADE)
                        )
                );

                supermercado.setEstado(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_ESTADO)
                        )
                );

                supermercado.setLatitude(
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_LATITUDE)
                        )
                );

                supermercado.setLongitude(
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(Supermercado.COLUNA_LONGITUDE)
                        )
                );

                supermercados.add(supermercado);
            }

            cursor.close();

        } finally {
            Close();
        }

        return supermercados;
    }

    public void inserirSupermercadosIniciais() {
        if (contarSupermercados() > 0) {
            return;
        }

        Supermercado giassi = new Supermercado();
        giassi.setNomeFantasia("Giassi");
        giassi.setEndereco("R. Henrique Lage, 1251");
        giassi.setBairro("Santa Barbara");
        giassi.setCidade("Criciúma");
        giassi.setEstado("SC");
        giassi.setLatitude(0);
        giassi.setLongitude(0);
        insert(giassi);

        Supermercado bistek = new Supermercado();
        bistek.setNomeFantasia("Bistek");
        bistek.setEndereco("Av. Centenário");
        bistek.setBairro("Centro");
        bistek.setCidade("Criciúma");
        bistek.setEstado("SC");
        bistek.setLatitude(0);
        bistek.setLongitude(0);
        insert(bistek);

        Supermercado fort = new Supermercado();
        fort.setNomeFantasia("Fort Atacadista");
        fort.setEndereco("Av. Centenário");
        fort.setBairro("Pinheirinho");
        fort.setCidade("Criciúma");
        fort.setEstado("SC");
        fort.setLatitude(0);
        fort.setLongitude(0);
        insert(fort);

        Supermercado angeloni = new Supermercado();
        angeloni.setNomeFantasia("Angeloni");
        angeloni.setEndereco("Av. Centenário");
        angeloni.setBairro("Centro");
        angeloni.setCidade("Criciúma");
        angeloni.setEstado("SC");
        angeloni.setLatitude(0);
        angeloni.setLongitude(0);
        insert(angeloni);
    }
    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoNormalizado = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return textoNormalizado.toLowerCase().trim();
    }

    public Long buscarIdPorNomeNormalizado(String nome) {
        Long idSupermercado = null;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT " + Supermercado.COLUNA_ID + ", " +
                            Supermercado.COLUNA_NOME_FANTASIA +
                            " FROM " + Supermercado.NOME_TABELA,
                    null
            );

            String nomeNormalizado = normalizar(nome);

            while (cursor.moveToNext()) {
                String nomeBanco = cursor.getString(
                        cursor.getColumnIndexOrThrow(Supermercado.COLUNA_NOME_FANTASIA)
                );

                if (normalizar(nomeBanco).equals(nomeNormalizado)) {
                    idSupermercado = cursor.getLong(
                            cursor.getColumnIndexOrThrow(Supermercado.COLUNA_ID)
                    );
                    break;
                }
            }

            cursor.close();

        } finally {
            Close();
        }

        return idSupermercado;
    }
}