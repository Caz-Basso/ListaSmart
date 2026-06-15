package com.example.listasmart.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.database.model.MercadoRanking;
import com.example.listasmart.databinding.ItemMercadoBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MercadoRankingAdapter
        extends RecyclerView.Adapter<MercadoRankingAdapter.ViewHolder> {

    private List<MercadoRanking> itens;

    public MercadoRankingAdapter(List<MercadoRanking> itens) {
        this.itens = itens;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemMercadoBinding binding;

        public ViewHolder(ItemMercadoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        ItemMercadoBinding binding =
                ItemMercadoBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        MercadoRanking mercado = itens.get(position);

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        holder.binding.txtPosicao.setText((position + 1) + "º");
        holder.binding.txtMercado.setText(mercado.getNome());
        holder.binding.txtTotal.setText(formatoMoeda.format(mercado.getTotal()));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}