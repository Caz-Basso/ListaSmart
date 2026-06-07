package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Produto;

import java.util.ArrayList;

public class ProdutoDAO extends AbstrataDAO {

    public ProdutoDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public long insert(Produto produto) {

        long idInserido;

        try {
            Open();

            ContentValues values = new ContentValues();

            values.put(Produto.COLUNA_NOME, produto.getNome());
            values.put(Produto.COLUNA_MARCA, produto.getMarca());
            values.put(Produto.COLUNA_VERIFICADO, produto.getVerificado());

            idInserido = db.insert(
                    Produto.NOME_TABELA,
                    null,
                    values
            );

        } finally {
            Close();
        }

        return idInserido;
    }

    public ArrayList<Produto> listar() {

        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + Produto.NOME_TABELA,
                    null
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

                produto.setVerificado(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(Produto.COLUNA_VERIFICADO)
                        )
                );

                produto.setCategoria("Produto");
                produto.setQuantidade(1);

                produtos.add(produto);
            }

            cursor.close();

        } finally {
            Close();
        }

        return produtos;
    }

    public int contarProdutos() {
        int total = 0;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + Produto.NOME_TABELA,
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

    public void inserirProdutosIniciais() {
        if (contarProdutos() > 0) {
            return;
        }

        Produto arroz = new Produto();
        arroz.setNome("Arroz");
        arroz.setMarca("Tio João");
        arroz.setVerificado(1);
        insert(arroz);

        Produto leite = new Produto();
        leite.setNome("Leite");
        leite.setMarca("Parmalat");
        leite.setVerificado(1);
        insert(leite);

        Produto sabonete = new Produto();
        sabonete.setNome("Sabonete");
        sabonete.setMarca("Dove");
        sabonete.setVerificado(1);
        insert(sabonete);

        Produto cafe = new Produto();
        cafe.setNome("Café");
        cafe.setMarca("Melitta");
        cafe.setVerificado(1);
        insert(cafe);

        Produto feijao = new Produto();
        feijao.setNome("Feijão");
        feijao.setMarca("Kicaldo");
        feijao.setVerificado(1);
        insert(feijao);

        Produto macarrao = new Produto();
        macarrao.setNome("Macarrão");
        macarrao.setMarca("Renata");
        macarrao.setVerificado(1);
        insert(macarrao);

        Produto oleo = new Produto();
        oleo.setNome("Óleo");
        oleo.setMarca("Soya");
        oleo.setVerificado(1);
        insert(oleo);
    }
}