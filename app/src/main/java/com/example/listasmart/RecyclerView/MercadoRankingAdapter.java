package com.example.listasmart.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.database.model.MercadoRanking;
import com.example.listasmart.databinding.ItemMercadoBinding;
import com.example.listasmart.database.model.Produto;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MercadoRankingAdapter extends RecyclerView.Adapter<MercadoRankingAdapter.ViewHolder> {

    private final List<MercadoRanking> itens;
    private Integer mercadoExpandidoPosicao = null;

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
                NumberFormat.getCurrencyInstance(
                        new Locale("pt", "BR")
                );

        holder.binding.txtPosicao.setText(
                (position + 1) + "º"
        );

        holder.binding.txtMercado.setText(
                mercado.getNome()
        );

        holder.binding.txtTotal.setText(
                formatoMoeda.format(mercado.getTotal())
        );

        holder.binding.txtTotalDetalhes.setText(
                formatoMoeda.format(mercado.getTotal())
        );
        holder.binding.layoutProdutosDetalhes.removeAllViews();

        for (Produto produto : mercado.getProdutos()) {

            LinearLayout linha = new LinearLayout(
                    holder.itemView.getContext()
            );

            linha.setOrientation(
                    LinearLayout.HORIZONTAL
            );

            TextView txtNome = new TextView(
                    holder.itemView.getContext()
            );

            TextView txtPreco = new TextView(
                    holder.itemView.getContext()
            );
            txtNome.setText(
                    produto.getNome() +
                            " x" +
                            produto.getQuantidade()
            );

            txtPreco.setText(
                    formatoMoeda.format(
                            produto.getPrecoAnalise()
                    )
            );
            txtNome.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    )
            );

            linha.addView(txtNome);
            linha.addView(txtPreco);

            holder.binding.layoutProdutosDetalhes.addView(linha);
        }

        holder.binding.txtProdutosNaoEncontrados.setText(
                "⚠ 2 produtos não encontrados"
        );

        boolean expandido =
                mercadoExpandidoPosicao != null
                        && mercadoExpandidoPosicao == position;

        holder.binding.layoutDetalhes.setVisibility(
                expandido ? View.VISIBLE : View.GONE
        );

        holder.binding.imgExpandir.setRotation(
                expandido ? 180f : 0f
        );

        holder.binding.cardMercado.setOnClickListener(v -> {

            TransitionManager.beginDelayedTransition(
                    (ViewGroup) holder.binding.cardMercado,
                    new AutoTransition()
            );

            int posicaoAnterior =
                    mercadoExpandidoPosicao == null
                            ? -1
                            : mercadoExpandidoPosicao;

            if (expandido) {

                mercadoExpandidoPosicao = null;

            } else {

                mercadoExpandidoPosicao = position;
            }

            if (posicaoAnterior != -1) {
                notifyItemChanged(posicaoAnterior);
            }

            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}