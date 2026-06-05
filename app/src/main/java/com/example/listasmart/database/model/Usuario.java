package com.example.listasmart.database.model;

public class Usuario {

    public static final String NOME_TABELA = "Usuarios";

    public static final String
            COLUNA_ID = "id_usuario",
            COLUNA_NOME_USUARIO = "nome_usuario",
            COLUNA_NOME_COMPLETO = "nome_completo",
            COLUNA_EMAIL = "email",
            COLUNA_SENHA = "senha";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_NOME_USUARIO + " TEXT NOT NULL UNIQUE, "
                    + COLUNA_NOME_COMPLETO + " TEXT NOT NULL, "
                    + COLUNA_EMAIL + " TEXT NOT NULL UNIQUE, "
                    + COLUNA_SENHA + " TEXT NOT NULL"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private String nomeUsuario;
    private String nomeCompleto;
    private String email;
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}