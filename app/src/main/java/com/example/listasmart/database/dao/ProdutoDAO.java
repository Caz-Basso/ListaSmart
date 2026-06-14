package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Categoria;
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

            values.put(Produto.COLUNA_ID_CATEGORIA, produto.getIdCategoria());
            values.put(Produto.COLUNA_NOME, produto.getNome());
            values.put(Produto.COLUNA_MARCA, produto.getMarca());
            values.put(Produto.COLUNA_IMAGEM_URL, produto.getImagemUrl());
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

            String sql =
                    "SELECT p.*, c." + Categoria.COLUNA_NOME + " AS nome_categoria " +
                            "FROM " + Produto.NOME_TABELA + " p " +
                            "LEFT JOIN " + Categoria.NOME_TABELA + " c " +
                            "ON p." + Produto.COLUNA_ID_CATEGORIA + " = c." + Categoria.COLUNA_ID;

            Cursor cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Produto produto = criarProdutoDoCursor(cursor);
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
        arroz.setImagemUrl("arroz");
        arroz.setIdCategoria(1L);
        arroz.setVerificado(1);
        insert(arroz);

        Produto leite = new Produto();
        leite.setNome("Leite");
        leite.setMarca("Parmalat");
        leite.setImagemUrl("leite");
        leite.setIdCategoria(1L);
        leite.setVerificado(1);
        insert(leite);

        Produto sabonete = new Produto();
        sabonete.setNome("Sabonete");
        sabonete.setMarca("Dove");
        sabonete.setImagemUrl("sabonete");
        sabonete.setIdCategoria(3L);
        sabonete.setVerificado(1);
        insert(sabonete);

        Produto cafe = new Produto();
        cafe.setNome("Café");
        cafe.setMarca("Melitta");
        cafe.setImagemUrl("cafe");
        cafe.setIdCategoria(1L);
        cafe.setVerificado(1);
        insert(cafe);

        Produto feijao = new Produto();
        feijao.setNome("Feijão");
        feijao.setMarca("Kicaldo");
        feijao.setImagemUrl("feijao");
        feijao.setIdCategoria(1L);
        feijao.setVerificado(1);
        insert(feijao);

        Produto macarrao = new Produto();
        macarrao.setNome("Macarrão");
        macarrao.setMarca("Renata");
        macarrao.setImagemUrl("macarrao");
        macarrao.setIdCategoria(1L);
        macarrao.setVerificado(1);
        insert(macarrao);

        Produto oleo = new Produto();
        oleo.setNome("Óleo");
        oleo.setMarca("Soya");
        oleo.setImagemUrl("oleo");
        oleo.setIdCategoria(1L);
        oleo.setVerificado(1);
        insert(oleo);
    }

    public Produto buscarPorId(long idProduto) {

        Produto produto = null;

        try {
            Open();

            String sql =
                    "SELECT p.*, c." + Categoria.COLUNA_NOME + " AS nome_categoria " +
                            "FROM " + Produto.NOME_TABELA + " p " +
                            "LEFT JOIN " + Categoria.NOME_TABELA + " c " +
                            "ON p." + Produto.COLUNA_ID_CATEGORIA + " = c." + Categoria.COLUNA_ID + " " +
                            "WHERE p." + Produto.COLUNA_ID + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{String.valueOf(idProduto)}
            );

            if (cursor.moveToFirst()) {
                produto = criarProdutoDoCursor(cursor);
            }

            cursor.close();

        } finally {
            Close();
        }

        return produto;
    }

    public ArrayList<Produto> buscarPorTexto(String texto) {

        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            Open();

            String termo = "%" + texto + "%";

            String sql =
                    "SELECT p.*, c." + Categoria.COLUNA_NOME + " AS nome_categoria " +
                            "FROM " + Produto.NOME_TABELA + " p " +
                            "LEFT JOIN " + Categoria.NOME_TABELA + " c " +
                            "ON p." + Produto.COLUNA_ID_CATEGORIA + " = c." + Categoria.COLUNA_ID + " " +
                            "WHERE p." + Produto.COLUNA_NOME + " LIKE ? " +
                            "OR p." + Produto.COLUNA_MARCA + " LIKE ? " +
                            "OR c." + Categoria.COLUNA_NOME + " LIKE ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{termo, termo, termo}
            );

            while (cursor.moveToNext()) {
                Produto produto = criarProdutoDoCursor(cursor);
                produtos.add(produto);
            }

            cursor.close();

        } finally {
            Close();
        }

        return produtos;
    }

    private Produto criarProdutoDoCursor(Cursor cursor) {

        Produto produto = new Produto();

        produto.setId(
                cursor.getLong(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_ID)
                )
        );

        produto.setIdCategoria(
                cursor.getLong(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_ID_CATEGORIA)
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

        produto.setCodigoBarras(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_CODIGO_BARRAS)
                )
        );

        produto.setImagemUrl(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_IMAGEM_URL)
                )
        );

        produto.setVerificado(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_VERIFICADO)
                )
        );

        int indexCategoria = cursor.getColumnIndex("nome_categoria");

        if (indexCategoria != -1 && !cursor.isNull(indexCategoria)) {
            produto.setCategoria(cursor.getString(indexCategoria));
        } else {
            produto.setCategoria("Produto");
        }

        produto.setQuantidade(1);

        return produto;
    }

    public void atualizarImagensProdutosIniciais() {
        try {
            Open();

            atualizarImagem("Arroz", "arroz");
            atualizarImagem("Leite", "leite");
            atualizarImagem("Sabonete", "sabonete");
            atualizarImagem("Café", "cafe");
            atualizarImagem("Feijão", "feijao");
            atualizarImagem("Macarrão", "macarrao");
            atualizarImagem("Óleo", "oleo");

        } finally {
            Close();
        }
    }

    private void atualizarImagem(String nomeProduto, String nomeImagem) {
        ContentValues values = new ContentValues();
        values.put(Produto.COLUNA_IMAGEM_URL, nomeImagem);

        db.update(
                Produto.NOME_TABELA,
                values,
                Produto.COLUNA_NOME + " = ?",
                new String[]{nomeProduto}
        );
    }

    public void atualizarCategoriasProdutosIniciais() {
        try {
            Open();

            atualizarCategoria("Arroz", 1L);
            atualizarCategoria("Leite", 1L);
            atualizarCategoria("Sabonete", 3L);
            atualizarCategoria("Café", 1L);
            atualizarCategoria("Feijão", 1L);
            atualizarCategoria("Macarrão", 1L);
            atualizarCategoria("Óleo", 1L);

        } finally {
            Close();
        }
    }

    private void atualizarCategoria(String nomeProduto, Long idCategoria) {
        ContentValues values = new ContentValues();
        values.put(Produto.COLUNA_ID_CATEGORIA, idCategoria);

        db.update(
                Produto.NOME_TABELA,
                values,
                Produto.COLUNA_NOME + " = ?",
                new String[]{nomeProduto}
        );
    }
}