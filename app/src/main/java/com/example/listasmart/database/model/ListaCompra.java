package com.example.listasmart.database.model;

public class ListaCompra {

    public static final String NOME_TABELA = "Listas_Compras";

    public static final String
            COLUNA_ID = "id_lista",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_NOME_LISTA = "nome_lista",
            COLUNA_DATA_CRIACAO = "data_criacao";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_ID_USUARIO + " INTEGER NOT NULL, "
                    + COLUNA_NOME_LISTA + " TEXT NOT NULL DEFAULT 'Lista Rápida', "
                    + COLUNA_DATA_CRIACAO + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private Long idUsuario;
    private String nomeLista;

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

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }
}