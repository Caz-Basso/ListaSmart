package com.example.listasmart.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.listasmart.ProductPageActivity;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.databinding.ItemProductBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    private List<Produto> itens;
    private Runnable atualizarCarrinho;

    public ProductListAdapter(List<Produto> itens, Runnable atualizarCarrinho){
        this.itens = itens;
        this.atualizarCarrinho = atualizarCarrinho;
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

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Produto produto = itens.get(position);

        holder.binding.txtNome.setText(produto.getNome());
        holder.binding.txtMarca.setText(produto.getMarca());

        holder.binding.btnAdd.setOnClickListener(v -> {

            ListaCompraDAO listaDAO = new ListaCompraDAO(v.getContext());
            ItemListaDAO itemListaDAO = new ItemListaDAO(v.getContext());

            Long idLista = listaDAO.buscarListaUsuario(1L);

            if (idLista == null) {
                idLista = listaDAO.criarListaPadrao(1L);
            }

            long idItem = itemListaDAO.adicionarItem(
                    idLista,
                    produto.getId()
            );

            if (idItem > 0) {

                Toast.makeText(
                        v.getContext(),
                        produto.getNome() + " adicionado à lista!",
                        Toast.LENGTH_SHORT
                ).show();

                if (atualizarCarrinho != null) {
                    atualizarCarrinho.run();
                }

            } else {

                Toast.makeText(
                        v.getContext(),
                        "Erro ao adicionar produto",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    ProductPageActivity.class
            );

            intent.putExtra(
                    "idProduto",
                    produto.getId()
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}