package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProductListAdapter;
import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;
import com.example.listasmart.database.dao.ProdutoDAO;
import com.example.listasmart.database.model.Produto;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.getWritableDatabase();

        ListaCompraDAO listaDAO = new ListaCompraDAO(this);

        Long idLista = listaDAO.buscarListaUsuario(1L);

        if (idLista == null) {
            idLista = listaDAO.criarListaPadrao(1L);
        }

        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        produtoDAO.inserirProdutosIniciais();

        ImageButton myListBtn = findViewById(R.id.mylistBtn);
        MaterialButton btnSaibaMais = findViewById(R.id.btnSaibaMais);
        TextView cartCount = findViewById(R.id.cartCount);

        Long idListaFinal = idLista;

        ItemListaDAO itemListaDAOInicial = new ItemListaDAO(this);
        cartCount.setText(String.valueOf(itemListaDAOInicial.contarItens(idListaFinal)));

        myListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyListActivity.class);
            startActivity(intent);
        });

        btnSaibaMais.setOnClickListener(v ->
                Toast.makeText(
                        MainActivity.this,
                        "O Lista Smart é um projeto comunitário criado para ajudar consumidores a economizar nas compras do dia a dia.",
                        Toast.LENGTH_LONG
                ).show()
        );

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

        produtos = produtoDAO.listar();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductListAdapter(produtos, () -> {
            ItemListaDAO itemListaDAO = new ItemListaDAO(this);
            cartCount.setText(String.valueOf(itemListaDAO.contarItens(idListaFinal)));
        });

        recyclerView.setAdapter(adapter);
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