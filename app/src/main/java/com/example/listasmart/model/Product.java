package com.example.listasmart.model;

public class Product {

    private String nome;
    private String marca;
    private String categoria;
    private int quantidade;

    public Product(
            String nome,
            String marca,
            String categoria,
            int quantidade
    ){
        this.nome = nome;
        this.marca = marca;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public Integer getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
