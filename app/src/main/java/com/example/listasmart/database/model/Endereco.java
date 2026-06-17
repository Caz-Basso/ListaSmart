package com.example.listasmart.database.model;

public class Endereco {

    public static final String NOME_TABELA = "Enderecos";

    public static final String
            COLUNA_ID = "id_endereco",
            COLUNA_ID_USUARIO = "id_usuario",
            COLUNA_CEP = "cep",
            COLUNA_CIDADE = "cidade",
            COLUNA_ESTADO = "estado",
            COLUNA_RUA = "rua",
            COLUNA_NUMERO = "numero",
            COLUNA_COMPLEMENTO = "complemento",
            COLUNA_NOME_CONTATO = "nome_contato",
            COLUNA_TELEFONE_CONTATO = "telefone_contato";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + " ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_ID_USUARIO + " INTEGER NOT NULL UNIQUE, "
                    + COLUNA_CEP + " TEXT, "
                    + COLUNA_CIDADE + " TEXT, "
                    + COLUNA_ESTADO + " TEXT, "
                    + COLUNA_RUA + " TEXT, "
                    + COLUNA_NUMERO + " TEXT, "
                    + COLUNA_COMPLEMENTO + " TEXT, "
                    + COLUNA_NOME_CONTATO + " TEXT, "
                    + COLUNA_TELEFONE_CONTATO + " TEXT"
                    + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + NOME_TABELA;

    private Long id;
    private Long idUsuario;
    private String cep;
    private String cidade;
    private String estado;
    private String rua;
    private String numero;
    private String complemento;
    private String nomeContato;
    private String telefoneContato;

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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }
}