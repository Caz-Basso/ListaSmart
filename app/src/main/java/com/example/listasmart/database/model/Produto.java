package com.example.listasmart.database.model;

public class Produto {

    public static final String NOME_TABELA = "Produtos";

    public static final String
            COLUNA_ID = "id_produto",
            COLUNA_ID_CATEGORIA = "id_categoria",
            COLUNA_NOME = "nome_produto",
            COLUNA_MARCA = "marca",
            COLUNA_CODIGO_BARRAS = "codigo_barras",
            COLUNA_IMAGEM_URL = "imagem_url",
            COLUNA_VERIFICADO = "verificado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_ID_CATEGORIA + " INTEGER, "
                    + COLUNA_NOME + " TEXT NOT NULL, "
                    + COLUNA_MARCA + " TEXT, "
                    + COLUNA_CODIGO_BARRAS + " TEXT UNIQUE, "
                    + COLUNA_IMAGEM_URL + " TEXT, "
                    + COLUNA_VERIFICADO + " INTEGER DEFAULT 0"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private Long idCategoria;
    private String nome;
    private String marca;
    private String codigoBarras;
    private String imagemUrl;
    private int verificado;

    private double precoMockado;


    private String categoria;
    private int quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public int getVerificado() {
        return verificado;
    }

    public void setVerificado(int verificado) {
        this.verificado = verificado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoMockado() {
        return precoMockado;
    }

    public void setPrecoMockado(double precoMockado) {
        this.precoMockado = precoMockado;
    }
}