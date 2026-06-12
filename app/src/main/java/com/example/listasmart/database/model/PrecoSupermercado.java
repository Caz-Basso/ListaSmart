package com.example.listasmart.database.model;

public class PrecoSupermercado {

    public static final String NOME_TABELA = "Precos_Supermercados";

    public static final String
            COLUNA_ID = "id_preco",
            COLUNA_ID_PRODUTO = "id_produto",
            COLUNA_ID_SUPERMERCADO = "id_supermercado",
            COLUNA_PRECO = "preco",
            COLUNA_DATA_ATUALIZACAO = "data_atualizacao";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_ID_PRODUTO + " INTEGER NOT NULL, "
                    + COLUNA_ID_SUPERMERCADO + " INTEGER NOT NULL, "
                    + COLUNA_PRECO + " REAL NOT NULL, "
                    + COLUNA_DATA_ATUALIZACAO + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private Long idProduto;
    private Long idSupermercado;
    private double preco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdSupermercado() {
        return idSupermercado;
    }

    public void setIdSupermercado(Long idSupermercado) {
        this.idSupermercado = idSupermercado;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}