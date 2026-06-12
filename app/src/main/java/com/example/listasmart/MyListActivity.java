package com.example.listasmart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.MyListAdapter;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;
import com.example.listasmart.database.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mylist);

        ImageButton backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.myList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListaCompraDAO listaDAO = new ListaCompraDAO(this);
        ItemListaDAO itemListaDAO = new ItemListaDAO(this);

        Long idLista = listaDAO.buscarListaUsuario(1L);

        ArrayList<Produto> produtos = new ArrayList<>();

        if (idLista != null) {
            produtos = itemListaDAO.listarProdutosDaLista(idLista);
        }

        RecyclerView recyclerView = findViewById(R.id.myListRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        MyListAdapter adapter = new MyListAdapter(produtos, idLista);
        recyclerView.setAdapter(adapter);

        View btnAnalise = findViewById(R.id.btnAnalise);


        btnAnalise.setOnClickListener(v -> {
            realizarAnalise();
        });
    }
    private void realizarAnalise() {

            List<MercadoRanking> ranking = new ArrayList<>();

            ranking.add(new MercadoRanking("Giassi", 94.30, 3.5));
            ranking.add(new MercadoRanking("Bistek", 96.80, 5.8));
            ranking.add(new MercadoRanking("Atacadão", 92.15, 4.1));
            ranking.add(new MercadoRanking("Mercado Central", 89.90, 2.0));
            ranking.add(new MercadoRanking("Mercado Bom Preço", 87.45, 1.2));

            ranking.sort((m1, m2) ->
                    Double.compare(m1.getTotal(), m2.getTotal()));

            mostrarResultado(ranking);
        }

    private void mostrarResultado(List<MercadoRanking> ranking) {

        MercadoRanking vencedor = ranking.get(0);

        View cardResultadoAnalise =
                findViewById(R.id.cardResultadoAnalise);

        cardResultadoAnalise.setVisibility(View.VISIBLE);
    }
}

