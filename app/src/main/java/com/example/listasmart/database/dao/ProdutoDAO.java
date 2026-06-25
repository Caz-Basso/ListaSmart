package com.example.listasmart.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.model.Categoria;
import com.example.listasmart.database.model.Produto;

import java.util.ArrayList;
import java.text.Normalizer;

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

    public boolean existeProduto(String nomeProduto) {

        boolean existe = false;

        try {
            Open();

            String sql =
                    "SELECT " + Produto.COLUNA_ID +
                            " FROM " + Produto.NOME_TABELA +
                            " WHERE " + Produto.COLUNA_NOME + " = ?";

            Cursor cursor = db.rawQuery(
                    sql,
                    new String[]{nomeProduto}
            );

            existe = cursor.moveToFirst();

            cursor.close();

        } finally {
            Close();
        }

        return existe;
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

        inserirProdutoSeNaoExistir("Arroz", "Tio João", "arroz", 1L);
        inserirProdutoSeNaoExistir("Leite", "Parmalat", "leite", 1L);
        inserirProdutoSeNaoExistir("Sabonete", "Dove", "sabonete", 3L);
        inserirProdutoSeNaoExistir("Café", "Melitta", "cafe", 1L);
        inserirProdutoSeNaoExistir("Feijão", "Kicaldo", "feijao", 1L);
        inserirProdutoSeNaoExistir("Macarrão", "Renata", "macarrao", 1L);
        inserirProdutoSeNaoExistir("Óleo", "Soya", "oleo", 1L);
        inserirProdutoSeNaoExistir("Açúcar", "União", "acucar", 1L);
        inserirProdutoSeNaoExistir("Farinha de Trigo", "Dona Benta", "farinha", 1L);
        inserirProdutoSeNaoExistir("Molho de Tomate", "Pomarola", "molho_tomate", 1L);
        inserirProdutoSeNaoExistir("Água Mineral", "Crystal", "agua", 2L);
        inserirProdutoSeNaoExistir("Refrigerante", "Coca-Cola", "refrigerante", 2L);
        inserirProdutoSeNaoExistir("Suco", "Del Valle", "suco", 2L);
        inserirProdutoSeNaoExistir("Creme Dental", "Colgate", "creme_dental", 3L);
        inserirProdutoSeNaoExistir("Papel Higiênico", "Neve", "papel_higienico", 3L);
        inserirProdutoSeNaoExistir("Shampoo", "Seda", "shampoo", 3L);
        inserirProdutoSeNaoExistir("Detergente", "Ypê", "detergente", 4L);
        inserirProdutoSeNaoExistir("Sabão em Pó", "Omo", "sabao_po", 4L);
        inserirProdutoSeNaoExistir("Desinfetante", "Pinho Sol", "desinfetante", 4L);
    }

    private void inserirProdutoSeNaoExistir(
            String nome,
            String marca,
            String imagem,
            Long idCategoria
    ) {

        if (buscarIdPorNomeMarcaNormalizado(nome, marca) != null) {
            return;
        }

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setMarca(marca);
        produto.setImagemUrl(imagem);
        produto.setIdCategoria(idCategoria);
        produto.setVerificado(1);

        insert(produto);
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
    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        textoNormalizado = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return textoNormalizado.toLowerCase().trim();
    }

    public Long buscarIdPorNomeMarcaNormalizado(String nome, String marca) {
        Long idProduto = null;

        try {
            Open();

            Cursor cursor = db.rawQuery(
                    "SELECT " + Produto.COLUNA_ID + ", " +
                            Produto.COLUNA_NOME + ", " +
                            Produto.COLUNA_MARCA +
                            " FROM " + Produto.NOME_TABELA,
                    null
            );

            String nomeNormalizado = normalizar(nome);
            String marcaNormalizada = normalizar(marca);

            while (cursor.moveToNext()) {
                String nomeBanco = cursor.getString(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_NOME)
                );

                String marcaBanco = cursor.getString(
                        cursor.getColumnIndexOrThrow(Produto.COLUNA_MARCA)
                );

                if (
                        normalizar(nomeBanco).equals(nomeNormalizado) &&
                                normalizar(marcaBanco).equals(marcaNormalizada)
                ) {
                    idProduto = cursor.getLong(
                            cursor.getColumnIndexOrThrow(Produto.COLUNA_ID)
                    );
                    break;
                }
            }

            cursor.close();

        } finally {
            Close();
        }

        return idProduto;
    }



}