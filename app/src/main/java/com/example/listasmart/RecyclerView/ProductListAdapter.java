package com.example.listasmart.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.listasmart.databinding.ItemProductBinding;
import com.example.listasmart.model.Product;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    List<Product> itens;

    public  ProductListAdapter(List<Product> itens){
        this.itens = itens;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemProductBinding binding;

        public ViewHolder(ItemProductBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemProductBinding binding =
        ItemProductBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return  new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product produto = itens.get(position);

        holder.binding.txtNome.setText(produto.getNome());
        holder.binding.txtMarca.setText(produto.getMarca());
        holder.binding.txtCategoria.setText(produto.getCategoria());
        holder.binding.btnAdd.setOnClickListener(v -> {
            produto.setQuantidade(
                    produto.getQuantidade() + 1
            );
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}