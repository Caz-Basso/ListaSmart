package com.example.listasmart.database.model;

public class Supermercado {

    public static final String NOME_TABELA = "Supermercados";

    public static final String
            COLUNA_ID = "id_supermercado",
            COLUNA_NOME_FANTASIA = "nome_fantasia",
            COLUNA_ENDERECO = "endereco",
            COLUNA_BAIRRO = "bairro",
            COLUNA_CIDADE = "cidade",
            COLUNA_ESTADO = "estado",
            COLUNA_LATITUDE = "latitude",
            COLUNA_LONGITUDE = "longitude";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_NOME_FANTASIA + " TEXT NOT NULL, "
                    + COLUNA_ENDERECO + " TEXT, "
                    + COLUNA_BAIRRO + " TEXT, "
                    + COLUNA_CIDADE + " TEXT, "
                    + COLUNA_ESTADO + " TEXT, "
                    + COLUNA_LATITUDE + " REAL, "
                    + COLUNA_LONGITUDE + " REAL"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private String nomeFantasia;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private double latitude;
    private double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}