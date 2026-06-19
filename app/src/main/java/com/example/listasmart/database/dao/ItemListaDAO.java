package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.ItemLista;
import com.example.listasmart.database.model.Produto;

import java.util.ArrayList;

public class ItemListaDAO extends AbstrataDAO {

    public ItemListaDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long adicionarItem(Long idLista, Long idProduto) {
        long resultado;

        try {
            Open();

            String sqlBusca = "SELECT " + ItemLista.COLUNA_ID + ", " + ItemLista.COLUNA_QUANTIDADE +
                    " FROM " + ItemLista.NOME_TABELA +
                    " WHERE " + ItemLista.COLUNA_ID_LISTA + " = ? AND " +
                    ItemLista.COLUNA_ID_PRODUTO + " = ?";

            Cursor cursor = db.rawQuery(
                    sqlBusca,
                    new String[]{String.valueOf(idLista), String.valueOf(idProduto)}
            );

            if (cursor.moveToFirst()) {
                long idItem = cursor.getLong(
                        cursor.getColumnIndexOrThrow(ItemLista.COLUNA_ID)
                );

                int quantidadeAtual = cursor.getInt(
                        cursor.getColumnIndexOrThrow(ItemLista.COLUNA_QUANTIDADE)
                );

                ContentValues values = new ContentValues();
                values.put(ItemLista.COLUNA_QUANTIDADE, quantidadeAtual + 1);

                resultado = db.update(
                        ItemLista.NOME_TABELA,
                        values,
                        ItemLista.COLUNA_ID + " = ?",
                        new String[]{String.valueOf(idItem)}
                );

            } else {
                ContentValues values = new ContentValues();

                values.put(ItemLista.COLUNA_ID_LISTA, idLista);
                values.put(ItemLista.COLUNA_ID_PRODUTO, idProduto);
                values.put(ItemLista.COLUNA_QUANTIDADE, 1);
                values.put(ItemLista.COLUNA_COMPRADO, 0);

                resultado = db.insert(
                        ItemLista.NOME_TABELA,
                        null,
                        values
                );
            }

            cursor.close();

        } finally {
            Close();
        }

        return resultado;
    }

    public int atualizarQuantidade(Long idLista, Long idProduto, int novaQuantidade) {
        int linhasAfetadas;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ItemLista.COLUNA_QUANTIDADE, novaQuantidade);

            linhasAfetadas = db.update(
                    ItemLista.NOME_TABELA,
                    values,
                    ItemLista.COLUNA_ID_LISTA + " = ? AND " + ItemLista.COLUNA_ID_PRODUTO + " = ?",
                    new String[]{String.valueOf(idLista), String.valueOf(idProduto)}
            );

        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public int removerItem(Long idLista, Long idProduto) {
        int linhasRemovidas;

        try {
            Open();

            linhasRemovidas = db.delete(
                    ItemLista.NOME_TABELA,
                    ItemLista.COLUNA_ID_LISTA + " = ? AND " + ItemLista.COLUNA_ID_PRODUTO + " = ?",
                    new String[]{String.valueOf(idLista), String.valueOf(idProduto)}
            );

        } finally {
            Close();
        }

        return linhasRemovidas;
    }

    public int contarItens(Long idLista) {
        int total = 0;

        try {
            Open();

            String sql = "SELECT SUM(" + ItemLista.COLUNA_QUANTIDADE + ") FROM " +
                    ItemLista.NOME_TABELA +
                    " WHERE " +
                    ItemLista.COLUNA_ID_LISTA +
                    " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idLista)}
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

    public ArrayList<Produto> listarProdutosDaLista(Long idLista) {
        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            Open();

            String sql =
                    "SELECT p." + Produto.COLUNA_ID + ", " +
                            "p." + Produto.COLUNA_NOME + ", " +
                            "p." + Produto.COLUNA_MARCA + ", " +
                            "i." + ItemLista.COLUNA_QUANTIDADE + " " +
                            "FROM " + ItemLista.NOME_TABELA + " i " +
                            "INNER JOIN " + Produto.NOME_TABELA + " p " +
                            "ON i." + ItemLista.COLUNA_ID_PRODUTO + " = p." + Produto.COLUNA_ID + " " +
                            "WHERE i." + ItemLista.COLUNA_ID_LISTA + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idLista)}
            );

            while (cursor.moveToNext()) {
                Produto produto = new Produto();

                produto.setId(
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow(Produto.COLUNA_ID)
                        )
                );

                produto.setNome(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Produto.COLUNA_NOME)
                        )
                );

                produto.setMarca(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(Produto.COLUNA_MARCA)
                        )
                );

                produto.setCategoria("Produto");

                produto.setQuantidade(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(ItemLista.COLUNA_QUANTIDADE)
                        )
                );

                produtos.add(produto);
            }

            cursor.close();

        } finally {
            Close();
        }

        return produtos;
    }
    public int excluirPorLista(Long idLista) {

        int linhasRemovidas;

        try {
            Open();

            linhasRemovidas = db.delete(
                    ItemLista.NOME_TABELA,
                    ItemLista.COLUNA_ID_LISTA + " = ?",
                    new String[]{
                            String.valueOf(idLista)
                    }
            );

        } finally {
            Close();
        }

        return linhasRemovidas;
    }
}