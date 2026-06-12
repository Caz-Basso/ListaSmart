package com.example.listasmart.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.databinding.ListProductsBinding;

import java.util.List;

public class MyListAdapter
        extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<Produto> itens;
    private Long idLista;
    private Runnable onListaAlterada;

    public MyListAdapter(List<Produto> itens, Long idLista, Runnable onListaAlterada){
        this.itens = itens;
        this.idLista = idLista;
        this.onListaAlterada = onListaAlterada;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        ListProductsBinding binding;

        public ViewHolder(ListProductsBinding binding) {
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
        ListProductsBinding binding =
                ListProductsBinding.inflate(
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
        Produto produto = itens.get(position);

        holder.binding.productName.setText(produto.getNome());

        holder.binding.quantity.setText(
                String.valueOf(produto.getQuantidade())
        );

        holder.binding.addBtn.setOnClickListener(v -> {
            int novaQuantidade = produto.getQuantidade() + 1;

            ItemListaDAO itemListaDAO = new ItemListaDAO(v.getContext());
            itemListaDAO.atualizarQuantidade(
                    idLista,
                    produto.getId(),
                    novaQuantidade
            );

            produto.setQuantidade(novaQuantidade);

            holder.binding.quantity.setText(
                    String.valueOf(produto.getQuantidade())
            );

            if (onListaAlterada != null) {
                onListaAlterada.run();
            }
        });

        holder.binding.removeBtn.setOnClickListener(v -> {
            if(produto.getQuantidade() > 1){
                int novaQuantidade = produto.getQuantidade() - 1;

                ItemListaDAO itemListaDAO = new ItemListaDAO(v.getContext());
                itemListaDAO.atualizarQuantidade(
                        idLista,
                        produto.getId(),
                        novaQuantidade
                );

                produto.setQuantidade(novaQuantidade);

                holder.binding.quantity.setText(
                        String.valueOf(produto.getQuantidade())
                );

                if (onListaAlterada != null) {
                    onListaAlterada.run();
                }
            }
        });

        holder.binding.deleteBtn.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            if(currentPosition != RecyclerView.NO_POSITION){

                Produto produtoRemovido = itens.get(currentPosition);

                ItemListaDAO itemListaDAO = new ItemListaDAO(v.getContext());
                itemListaDAO.removerItem(
                        idLista,
                        produtoRemovido.getId()
                );

                itens.remove(currentPosition);
                notifyItemRemoved(currentPosition);

                if (onListaAlterada != null) {
                    onListaAlterada.run();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}