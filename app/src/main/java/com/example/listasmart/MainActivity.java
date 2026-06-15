package com.example.listasmart;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.Profile.ProfileActivity;
import com.example.listasmart.RecyclerView.ProductListAdapter;
import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;
import com.example.listasmart.database.dao.PrecoSupermercadoDAO;
import com.example.listasmart.database.dao.ProdutoDAO;
import com.example.listasmart.database.dao.SupermercadoDAO;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.database.dao.CategoriaDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<Produto> produtos;
    private boolean headerOculto = false;
    private int alturaOriginalHeader = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton listBtn = findViewById(R.id.listBtn);
        ImageButton homeBtn = findViewById(R.id.homeBtn);
        ImageButton profileBtn = findViewById(R.id.profileBtn);

        listBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyListActivity.class);
            startActivity(intent);
        });

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.getWritableDatabase();

        ListaCompraDAO listaDAO = new ListaCompraDAO(this);

        Long idLista = listaDAO.buscarListaUsuario(1L);

        if (idLista == null) {
            idLista = listaDAO.criarListaPadrao(1L);
        }

        CategoriaDAO categoriaDAO = new CategoriaDAO(this);
        categoriaDAO.inserirCategoriasIniciais();

        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        produtoDAO.inserirProdutosIniciais();
        produtoDAO.atualizarImagensProdutosIniciais();
        produtoDAO.atualizarCategoriasProdutosIniciais();

        SupermercadoDAO supermercadoDAO = new SupermercadoDAO(this);
        supermercadoDAO.inserirSupermercadosIniciais();

        PrecoSupermercadoDAO precoSupermercadoDAO = new PrecoSupermercadoDAO(this);
        precoSupermercadoDAO.inserirPrecosIniciais();

        ImageButton myListBtn = findViewById(R.id.mylistBtn);
        TextView cartCount = findViewById(R.id.cartCount);

        Long idListaFinal = idLista;

        ItemListaDAO itemListaDAOInicial = new ItemListaDAO(this);
        cartCount.setText(String.valueOf(itemListaDAOInicial.contarItens(idListaFinal)));

        myListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyListActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        SearchView searchView = findViewById(R.id.searchBar);

        ImageView searchIcon = searchView.findViewById(
                androidx.appcompat.R.id.search_mag_icon
        );

        if (searchIcon != null) {
            searchIcon.setImageResource(R.drawable.outline_search_24);
        }

        View plate = searchView.findViewById(
                androidx.appcompat.R.id.search_plate
        );

        if (plate != null) {
            plate.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        }

        produtos = produtoDAO.listar();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductListAdapter(produtos, () -> {
            ItemListaDAO itemListaDAO = new ItemListaDAO(this);
            cartCount.setText(String.valueOf(itemListaDAO.contarItens(idListaFinal)));
        });

        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarProdutos(query, produtoDAO);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarProdutos(newText, produtoDAO);
                return true;
            }
        });

        homeBtn.setOnClickListener(v -> {
            searchView.setQuery("", false);
            searchView.clearFocus();
            recyclerView.smoothScrollToPosition(0);
        });

        View homeHeader = findViewById(R.id.homeHeader);

        homeHeader.post(() -> {
            alturaOriginalHeader = homeHeader.getHeight();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (alturaOriginalHeader <= 0) return;

                if (dy > 15 && !headerOculto) {

                    headerOculto = true;

                    ValueAnimator animator =
                            ValueAnimator.ofInt(homeHeader.getHeight(), 0);

                    animator.setDuration(250);

                    animator.addUpdateListener(animation -> {
                        int valor = (int) animation.getAnimatedValue();

                        homeHeader.getLayoutParams().height = valor;
                        homeHeader.requestLayout();
                    });

                    animator.start();

                } else if (dy < -15 && headerOculto) {

                    headerOculto = false;

                    ValueAnimator animator =
                            ValueAnimator.ofInt(
                                    homeHeader.getHeight(),
                                    alturaOriginalHeader
                            );

                    animator.setDuration(250);

                    animator.addUpdateListener(animation -> {
                        int valor = (int) animation.getAnimatedValue();

                        homeHeader.getLayoutParams().height = valor;
                        homeHeader.requestLayout();
                    });

                    animator.start();
                }
            }
        });
    }

    private void buscarProdutos(String texto, ProdutoDAO produtoDAO) {

        ArrayList<Produto> resultado;

        if (texto == null || texto.trim().isEmpty()) {
            resultado = produtoDAO.listar();
        } else {
            resultado = produtoDAO.buscarPorTexto(texto.trim());
        }

        adapter.atualizarLista(resultado);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView cartCount = findViewById(R.id.cartCount);

        ListaCompraDAO listaDAO = new ListaCompraDAO(this);
        Long idLista = listaDAO.buscarListaUsuario(1L);

        if (idLista != null) {
            ItemListaDAO itemListaDAO = new ItemListaDAO(this);
            cartCount.setText(String.valueOf(itemListaDAO.contarItens(idLista)));
        } else {
            cartCount.setText("0");
        }
    }
}