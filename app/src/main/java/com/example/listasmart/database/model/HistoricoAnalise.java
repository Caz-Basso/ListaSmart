package com.example.listasmart.database.model;

public class HistoricoAnalise {

    public static final String NOME_TABELA = "Historico_Analises";

    public static final String
            COLUNA_ID = "id_historico",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_ID_LISTA = "id_lista",
            COLUNA_MERCADO_RECOMENDADO = "mercado_recomendado",
            COLUNA_VALOR_TOTAL = "valor_total",
            COLUNA_ECONOMIA = "economia",
            COLUNA_DATA_ANALISE = "data_analise";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_ID_USUARIO + " INTEGER NOT NULL, "
                    + COLUNA_ID_LISTA + " INTEGER NOT NULL, "
                    + COLUNA_MERCADO_RECOMENDADO + " TEXT NOT NULL, "
                    + COLUNA_VALOR_TOTAL + " REAL NOT NULL, "
                    + COLUNA_ECONOMIA + " REAL NOT NULL, "
                    + COLUNA_DATA_ANALISE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private Long idUsuario;
    private Long idLista;
    private String mercadoRecomendado;
    private double valorTotal;
    private double economia;
    private String dataAnalise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public String getMercadoRecomendado() {
        return mercadoRecomendado;
    }

    public void setMercadoRecomendado(String mercadoRecomendado) {
        this.mercadoRecomendado = mercadoRecomendado;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getEconomia() {
        return economia;
    }

    public void setEconomia(double economia) {
        this.economia = economia;
    }

    public String getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(String dataAnalise) {
        this.dataAnalise = dataAnalise;
    }
}