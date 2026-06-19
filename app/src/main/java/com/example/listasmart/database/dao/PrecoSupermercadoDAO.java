package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.model.MercadoRanking;
import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.ItemLista;
import com.example.listasmart.database.model.PrecoSupermercado;
import com.example.listasmart.database.model.Supermercado;
import com.example.listasmart.database.model.Produto;

import java.util.ArrayList;

public class PrecoSupermercadoDAO extends AbstrataDAO {

    public PrecoSupermercadoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(PrecoSupermercado preco) {
        long idInserido;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(PrecoSupermercado.COLUNA_ID_PRODUTO, preco.getIdProduto());
            values.put(PrecoSupermercado.COLUNA_ID_SUPERMERCADO, preco.getIdSupermercado());
            values.put(PrecoSupermercado.COLUNA_PRECO, preco.getPreco());

            idInserido = db.insert(
                    PrecoSupermercado.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idInserido;
    }

    public int contarPrecos() {
        int total = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + PrecoSupermercado.NOME_TABELA,
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

    public void inserirPrecosIniciais() {

        if (contarPrecos() > 0) {
            return;
        }

        inserirPreco(1L, 1L, 24.90);
        inserirPreco(1L, 2L, 26.50);
        inserirPreco(1L, 3L, 23.90);
        inserirPreco(1L, 4L, 25.20);

        inserirPreco(2L, 1L, 5.20);
        inserirPreco(2L, 2L, 4.99);
        inserirPreco(2L, 3L, 5.10);
        inserirPreco(2L, 4L, 5.30);

        inserirPreco(3L, 1L, 3.50);
        inserirPreco(3L, 2L, 3.90);
        inserirPreco(3L, 3L, 3.40);
        inserirPreco(3L, 4L, 3.70);

        inserirPreco(4L, 1L, 18.90);
        inserirPreco(4L, 2L, 17.80);
        inserirPreco(4L, 3L, 19.20);
        inserirPreco(4L, 4L, 18.50);

        inserirPreco(5L, 1L, 8.50);
        inserirPreco(5L, 2L, 7.99);
        inserirPreco(5L, 3L, 8.30);
        inserirPreco(5L, 4L, 8.70);

        inserirPreco(6L, 1L, 4.50);
        inserirPreco(6L, 2L, 4.20);
        inserirPreco(6L, 3L, 4.60);
        inserirPreco(6L, 4L, 4.30);

        inserirPreco(7L, 1L, 6.99);
        inserirPreco(7L, 2L, 7.20);
        inserirPreco(7L, 3L, 6.80);
        inserirPreco(7L, 4L, 7.10);
    }

    private void inserirPreco(Long idProduto, Long idSupermercado, double valor) {

        PrecoSupermercado preco = new PrecoSupermercado();

        preco.setIdProduto(idProduto);
        preco.setIdSupermercado(idSupermercado);
        preco.setPreco(valor);

        insert(preco);
    }

    public ArrayList<MercadoRanking> calcularRankingPorLista(Long idLista) {

        ArrayList<MercadoRanking> ranking = new ArrayList<>();

        try {
            Open();

            String sql =
                    "SELECT s." + Supermercado.COLUNA_NOME_FANTASIA + " AS nome_mercado, " +
                            "SUM(p." + PrecoSupermercado.COLUNA_PRECO + " * i." + ItemLista.COLUNA_QUANTIDADE + ") AS total " +
                            "FROM " + ItemLista.NOME_TABELA + " i " +
                            "INNER JOIN " + PrecoSupermercado.NOME_TABELA + " p " +
                            "ON i." + ItemLista.COLUNA_ID_PRODUTO + " = p." + PrecoSupermercado.COLUNA_ID_PRODUTO + " " +
                            "INNER JOIN " + Supermercado.NOME_TABELA + " s " +
                            "ON p." + PrecoSupermercado.COLUNA_ID_SUPERMERCADO + " = s." + Supermercado.COLUNA_ID + " " +
                            "WHERE i." + ItemLista.COLUNA_ID_LISTA + " = ? " +
                            "GROUP BY s." + Supermercado.COLUNA_ID + ", s." + Supermercado.COLUNA_NOME_FANTASIA + " " +
                            "ORDER BY total ASC";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idLista)}
            );

            while (cursor.moveToNext()) {

                String nomeMercado = cursor.getString(
                        cursor.getColumnIndexOrThrow("nome_mercado")
                );

                double total = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("total")
                );

                MercadoRanking mercado = new MercadoRanking(
                        nomeMercado,
                        total,
                        0.0
                );

                mercado.setProdutos(new ArrayList<>());

                String sqlProdutos =
                        "SELECT prod." + Produto.COLUNA_NOME + " AS nome_produto, " +
                                "p." + PrecoSupermercado.COLUNA_PRECO + " AS preco, " +
                                "i." + ItemLista.COLUNA_QUANTIDADE + " AS quantidade " +
                                "FROM " + ItemLista.NOME_TABELA + " i " +
                                "INNER JOIN " + Produto.NOME_TABELA + " prod " +
                                "ON i." + ItemLista.COLUNA_ID_PRODUTO + " = prod." + Produto.COLUNA_ID + " " +
                                "INNER JOIN " + PrecoSupermercado.NOME_TABELA + " p " +
                                "ON i." + ItemLista.COLUNA_ID_PRODUTO + " = p." + PrecoSupermercado.COLUNA_ID_PRODUTO + " " +
                                "INNER JOIN " + Supermercado.NOME_TABELA + " s " +
                                "ON p." + PrecoSupermercado.COLUNA_ID_SUPERMERCADO + " = s." + Supermercado.COLUNA_ID + " " +
                                "WHERE i." + ItemLista.COLUNA_ID_LISTA + " = ? " +
                                "AND s." + Supermercado.COLUNA_NOME_FANTASIA + " = ?";

                Cursor cursorProdutos = db.rawQuery(
                        sqlProdutos,
                        new String[]{
                                String.valueOf(idLista),
                                nomeMercado
                        }
                );

                while (cursorProdutos.moveToNext()) {

                    Produto produto = new Produto();

                    produto.setNome(
                            cursorProdutos.getString(
                                    cursorProdutos.getColumnIndexOrThrow("nome_produto")
                            )
                    );
                    produto.setQuantidade(
                            cursorProdutos.getInt(
                                    cursorProdutos.getColumnIndexOrThrow("quantidade")
                            )
                    );

                    produto.setPrecoAnalise(
                            cursorProdutos.getDouble(
                                    cursorProdutos.getColumnIndexOrThrow("preco")
                            )
                    );

                    mercado.getProdutos().add(produto);
                }

                cursorProdutos.close();

                ranking.add(mercado);
            }

            cursor.close();

        } finally {
            Close();
        }

        return ranking;
    }
}