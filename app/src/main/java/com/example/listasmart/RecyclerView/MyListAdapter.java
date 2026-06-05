package com.example.listasmart.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.databinding.ListProductsBinding;
import com.example.listasmart.model.Product;

import java.util.List;

public class MyListAdapter
        extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<Product> itens;

    public MyListAdapter(List<Product> itens){
        this.itens = itens;
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

        Product produto = itens.get(position);

        holder.binding.productName
                .setText(produto.getNome());

        holder.binding.quantity
                .setText(
                        String.valueOf(
                                produto.getQuantidade()
                        )
                );
        holder.binding.addBtn.setOnClickListener(v -> {
            produto.setQuantidade( produto.getQuantidade() + 1
            );
            holder.binding.quantity .setText( String.valueOf(produto.getQuantidade()));
        });

        holder.binding.removeBtn.setOnClickListener(v -> {
            if(produto.getQuantidade() > 1){
                produto.setQuantidade( produto.getQuantidade() - 1);
                holder.binding.quantity.setText( String.valueOf( produto.getQuantidade() ));
            }
        });

        holder.binding.deleteBtn.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if(currentPosition != RecyclerView.NO_POSITION){
                itens.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}