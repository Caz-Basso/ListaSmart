package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.ItemLista;
import com.example.listasmart.database.model.MarketPrice;
import com.example.listasmart.database.model.MercadoRanking;
import com.example.listasmart.database.model.PrecoSupermercado;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.database.model.Supermercado;

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

        inserirPrecosProduto("Arroz", 24.90, 26.50, 23.90, 25.20);
        inserirPrecosProduto("Leite", 5.20, 4.99, 5.10, 5.30);
        inserirPrecosProduto("Sabonete", 3.50, 3.90, 3.40, 3.70);
        inserirPrecosProduto("Café", 18.90, 17.80, 19.20, 18.50);
        inserirPrecosProduto("Feijão", 8.50, 7.99, 8.30, 8.70);
        inserirPrecosProduto("Macarrão", 4.50, 4.20, 4.60, 4.30);
        inserirPrecosProduto("Óleo", 6.99, 7.20, 6.80, 7.10);

        inserirPrecosProduto("Açúcar", 4.99, 5.20, 4.80, 5.10);
        inserirPrecosProduto("Farinha de Trigo", 5.90, 5.60, 6.10, 5.80);
        inserirPrecosProduto("Molho de Tomate", 3.20, 3.49, 3.10, 3.30);

        inserirPrecosProduto("Água Mineral", 2.50, 2.30, 2.60, 2.40);
        inserirPrecosProduto("Refrigerante", 8.99, 9.50, 8.70, 9.20);
        inserirPrecosProduto("Suco", 6.99, 7.30, 6.80, 7.10);

        inserirPrecosProduto("Creme Dental", 7.90, 8.20, 7.50, 8.00);
        inserirPrecosProduto("Papel Higiênico", 18.90, 19.50, 18.40, 19.20);
        inserirPrecosProduto("Shampoo", 14.90, 15.50, 14.40, 15.20);

        inserirPrecosProduto("Detergente", 2.49, 2.70, 2.39, 2.60);
        inserirPrecosProduto("Sabão em Pó", 16.90, 17.50, 16.40, 17.20);
        inserirPrecosProduto("Desinfetante", 7.50, 7.90, 7.20, 7.70);
    }

    private void inserirPrecosProduto(
            String nomeProduto,
            double precoMercado1,
            double precoMercado2,
            double precoMercado3,
            double precoMercado4
    ) {
        Long idProduto = buscarIdProdutoPorNome(nomeProduto);

        if (idProduto == null) {
            return;
        }

        inserirPrecoSeNaoExistir(idProduto, 1L, precoMercado1);
        inserirPrecoSeNaoExistir(idProduto, 2L, precoMercado2);
        inserirPrecoSeNaoExistir(idProduto, 3L, precoMercado3);
        inserirPrecoSeNaoExistir(idProduto, 4L, precoMercado4);
    }

    private Long buscarIdProdutoPorNome(String nomeProduto) {
        Long idProduto = null;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT " + Produto.COLUNA_ID +
                            " FROM " + Produto.NOME_TABELA +
                            " WHERE " + Produto.COLUNA_NOME + " = ?",
                    new String[]{nomeProduto}
            );

            if (cursor.moveToFirst()) {
                idProduto = cursor.getLong(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_ID)
                );
            }

            cursor.close();

        } finally {
            Close();
        }

        return idProduto;
    }

    private void inserirPrecoSeNaoExistir(
            Long idProduto,
            Long idSupermercado,
            double valor
    ) {
        if (existePreco(idProduto, idSupermercado)) {
            return;
        }

        inserirPreco(idProduto, idSupermercado, valor);
    }

    private boolean existePreco(Long idProduto, Long idSupermercado) {
        boolean existe = false;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + PrecoSupermercado.NOME_TABELA +
                            " WHERE " + PrecoSupermercado.COLUNA_ID_PRODUTO + " = ? " +
                            "AND " + PrecoSupermercado.COLUNA_ID_SUPERMERCADO + " = ?",
                    new String[]{
                            String.valueOf(idProduto),
                            String.valueOf(idSupermercado)
                    }
            );

            if (cursor.moveToFirst()) {
                existe = cursor.getInt(0) > 0;
            }

            cursor.close();

        } finally {
            Close();
        }

        return existe;
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
    public ArrayList<MarketPrice> listarPrecosPorProduto(Long idProduto) {

        ArrayList<MarketPrice> lista = new ArrayList<>();

        try {
            Open();

            String sql =
                    "SELECT s." + Supermercado.COLUNA_NOME_FANTASIA + " AS nome_mercado, " +
                            "p." + PrecoSupermercado.COLUNA_PRECO + " AS preco " +
                            "FROM " + PrecoSupermercado.NOME_TABELA + " p " +
                            "INNER JOIN " + Supermercado.NOME_TABELA + " s " +
                            "ON p." + PrecoSupermercado.COLUNA_ID_SUPERMERCADO +
                            " = s." + Supermercado.COLUNA_ID + " " +
                            "WHERE p." + PrecoSupermercado.COLUNA_ID_PRODUTO + " = ? " +
                            "ORDER BY p." + PrecoSupermercado.COLUNA_PRECO + " ASC";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idProduto)}
            );

            int posicao = 1;

            while (cursor.moveToNext()) {

                String nomeMercado = cursor.getString(
                        cursor.getColumnIndexOrThrow("nome_mercado")
                );

                double preco = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("preco")
                );

                String precoFormatado = String.format("R$ %.2f", preco);

                MarketPrice item = new MarketPrice(
                        posicao,
                        nomeMercado,
                        "Distância não informada",
                        precoFormatado
                );

                lista.add(item);
                posicao++;
            }

            cursor.close();

        } finally {
            Close();
        }

        return lista;
    }
    public void inserirOuAtualizarPreco(
            Long idProduto,
            Long idSupermercado,
            double valor
    ) {
        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT " + PrecoSupermercado.COLUNA_ID +
                            " FROM " + PrecoSupermercado.NOME_TABELA +
                            " WHERE " + PrecoSupermercado.COLUNA_ID_PRODUTO + " = ? " +
                            "AND " + PrecoSupermercado.COLUNA_ID_SUPERMERCADO + " = ?",
                    new String[]{
                            String.valueOf(idProduto),
                            String.valueOf(idSupermercado)
                    }
            );

            boolean existe = cursor.moveToFirst();

            cursor.close();

            ContentValues values = new ContentValues();
            values.put(PrecoSupermercado.COLUNA_PRECO, valor);

            if (existe) {
                db.update(
                        PrecoSupermercado.NOME_TABELA,
                        values,
                        PrecoSupermercado.COLUNA_ID_PRODUTO + " = ? AND " +
                                PrecoSupermercado.COLUNA_ID_SUPERMERCADO + " = ?",
                        new String[]{
                                String.valueOf(idProduto),
                                String.valueOf(idSupermercado)
                        }
                );
            } else {
                values.put(PrecoSupermercado.COLUNA_ID_PRODUTO, idProduto);
                values.put(PrecoSupermercado.COLUNA_ID_SUPERMERCADO, idSupermercado);

                db.insert(
                        PrecoSupermercado.NOME_TABELA,
                        null,
                        values
                );
            }

        } finally {
            Close();
        }
    }
}