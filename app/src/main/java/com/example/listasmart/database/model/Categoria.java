package com.example.listasmart.database.model;

public class Categoria {

    public static final String NOME_TABELA = "Categorias";

    public static final String
            COLUNA_ID = "id_categoria",
            COLUNA_NOME = "nome_categoria";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_NOME + " TEXT NOT NULL UNIQUE"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}