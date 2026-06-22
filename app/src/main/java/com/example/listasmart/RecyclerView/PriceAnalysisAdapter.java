package com.example.listasmart.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.listasmart.database.model.MarketPrice;
import com.example.listasmart.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PriceAnalysisAdapter extends RecyclerView.Adapter<PriceAnalysisAdapter.ViewHolder> {

    private final List<MarketPrice> mercados;

    public PriceAnalysisAdapter(List<MarketPrice> mercados) {
        this.mercados = mercados;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_market_price, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MarketPrice mercado = mercados.get(position);

        holder.txtPosicao.setText(mercado.getPosicao() + "º");
        holder.txtMercado.setText(mercado.getMercado());
        holder.txtDistancia.setText(mercado.getDistancia());
        holder.txtPreco.setText(mercado.getPreco());

        if (mercado.getPosicao() == 1) {
            holder.cardMercado.setStrokeWidth(3);
        }
    }

    @Override
    public int getItemCount() {
        return mercados.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardMercado;

        TextView txtPosicao;
        TextView txtMercado;
        TextView txtDistancia;
        TextView txtPreco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardMercado = itemView.findViewById(R.id.cardMercado);

            txtPosicao = itemView.findViewById(R.id.txtPosicao);
            txtMercado = itemView.findViewById(R.id.txtMercado);
            txtDistancia = itemView.findViewById(R.id.txtDistancia);
            txtPreco = itemView.findViewById(R.id.txtPreco);
        }
    }
}