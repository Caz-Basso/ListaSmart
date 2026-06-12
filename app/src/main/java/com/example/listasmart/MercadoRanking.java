package com.example.listasmart;

public class MercadoRanking {

    private String nome;
    private double total;
    private double distanciaKm;

    public MercadoRanking(String nome, double total, double distanciaKm) {
        this.nome = nome;
        this.total = total;
        this.distanciaKm = distanciaKm;
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
}
