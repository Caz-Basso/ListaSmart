package com.example.listasmart.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.R;
import com.example.listasmart.database.model.Produto;

import java.util.List;
import java.util.Locale;

public class ProdutoCupomAdapter
        extends RecyclerView.Adapter<ProdutoCupomAdapter.ViewHolder> {

    private final List<Produto> produtos;
    private final boolean modoScanner;
    private final OnProdutoClickListener listener;

    public interface OnProdutoClickListener {
        void onEditar(Produto produto);
        void onRemover(Produto produto);
    }

    public ProdutoCupomAdapter(
            List<Produto> produtos,
            boolean modoScanner,
            OnProdutoClickListener listener
    ) {
        this.produtos = produtos;
        this.modoScanner = modoScanner;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_produto_cupom,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Produto produto = produtos.get(position);

        holder.txtNomeProduto.setText(produto.getNome());
        holder.txtMarca.setText("Marca: " + produto.getMarca());

        holder.txtQuantidade.setText(
                "Qtd: " + produto.getQuantidade()
        );

        holder.txtPreco.setText(
                String.format(
                        Locale.getDefault(),
                        "R$ %.2f",
                        produto.getPrecoMockado()
                )
        );

        holder.btnAcao.setOnClickListener(null);

        if (modoScanner) {

            holder.btnAcao.setImageResource(
                    R.drawable.outline_edit_24
            );

            holder.btnAcao.setOnClickListener(v -> {

                int pos = holder.getBindingAdapterPosition();

                if (pos != RecyclerView.NO_POSITION
                        && listener != null) {

                    listener.onEditar(produtos.get(pos));
                }
            });

        } else {

            holder.btnAcao.setImageResource(
                    R.drawable.outline_delete_24
            );

            holder.btnAcao.setOnClickListener(v -> {

                int pos = holder.getBindingAdapterPosition();

                if (pos != RecyclerView.NO_POSITION
                        && listener != null) {

                    listener.onRemover(produtos.get(pos));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtNomeProduto;
        TextView txtMarca;
        TextView txtQuantidade;
        TextView txtPreco;
        ImageButton btnAcao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNomeProduto =
                    itemView.findViewById(R.id.txtNomeProduto);

            txtMarca =
                    itemView.findViewById(R.id.txtMarca);

            txtQuantidade =
                    itemView.findViewById(R.id.txtQuantidade);

            txtPreco =
                    itemView.findViewById(R.id.txtPreco);

            btnAcao =
                    itemView.findViewById(R.id.btnRemover);
        }
    }
}