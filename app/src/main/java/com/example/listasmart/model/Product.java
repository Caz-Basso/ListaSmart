package com.example.listasmart.model;

public class Product {

    private String nome;
    private String marca;
    private String categoria;

    public Product(
            String nome,
            String marca,
            String categoria
    ) {
        this.nome = nome;
        this.marca = marca;
        this.categoria = categoria;
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
}
