package com.example.listasmart.database.model;

public class ItemLista {

    public static final String NOME_TABELA = "Itens_Lista";

    public static final String
            COLUNA_ID = "id_item",
            COLUNA_ID_LISTA = "id_lista",
            COLUNA_ID_PRODUTO = "id_produto",
            COLUNA_QUANTIDADE = "quantidade",
            COLUNA_COMPRADO = "comprado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_ID_LISTA + " INTEGER NOT NULL, "
                    + COLUNA_ID_PRODUTO + " INTEGER NOT NULL, "
                    + COLUNA_QUANTIDADE + " INTEGER NOT NULL DEFAULT 1, "
                    + COLUNA_COMPRADO + " INTEGER DEFAULT 0"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private Long idLista;
    private Long idProduto;
    private int quantidade;
    private int comprado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getComprado() {
        return comprado;
    }

    public void setComprado(int comprado) {
        this.comprado = comprado;
    }
}