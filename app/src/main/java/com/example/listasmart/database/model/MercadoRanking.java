package com.example.listasmart.database.model;

import java.util.ArrayList;

public class MercadoRanking {

    private String nome;
    private double total;
    private double distanciaKm;
    private ArrayList<Produto> produtos;

    public MercadoRanking(String nome, double total, double distanciaKm) {
        this.nome = nome;
        this.total = total;
        this.distanciaKm = distanciaKm;
        this.produtos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public double getTotal() {
        return total;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }
}