package com.example.listasmart.database.model;

public class AnalysisHistory {

    private String nomeLista;
    private String data;
    private String mercado;
    private String economia;

    public AnalysisHistory(String nomeLista, String data, String mercado, String economia) {
        this.nomeLista = nomeLista;
        this.data = data;
        this.mercado = mercado;
        this.economia = economia;
    }

    public String getNomeLista() { return nomeLista; }
    public String getData() { return data; }
    public String getMercado() { return mercado; }
    public String getEconomia() { return economia; }
}
