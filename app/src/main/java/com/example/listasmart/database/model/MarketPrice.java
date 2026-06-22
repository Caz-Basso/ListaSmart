package com.example.listasmart.database.model;

public class MarketPrice {

    private final int posicao;
    private final String mercado;
    private final String distancia;
    private final String preco;

    public MarketPrice(
            int posicao,
            String mercado,
            String distancia,
            String preco
    ) {
        this.posicao = posicao;
        this.mercado = mercado;
        this.distancia = distancia;
        this.preco = preco;
    }

    public int getPosicao() {
        return posicao;
    }

    public String getMercado() {
        return mercado;
    }

    public String getDistancia() {
        return distancia;
    }

    public String getPreco() {
        return preco;
    }
}