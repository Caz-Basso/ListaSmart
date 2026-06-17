package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.MercadoRankingAdapter;
import com.example.listasmart.RecyclerView.MyListAdapter;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;
import com.example.listasmart.database.dao.PrecoSupermercadoDAO;
import com.example.listasmart.database.model.MercadoRanking;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.database.dao.HistoricoAnaliseDAO;
import com.example.listasmart.database.model.HistoricoAnalise;
import com.google.android.material.button.MaterialButton;
import com.example.listasmart.Profile.AnalysisHistoryActivity;
import com.example.listasmart.Profile.AddressActivity;
import com.example.listasmart.database.dao.EnderecoDAO;
import com.example.listasmart.database.model.Endereco;

import com.example.listasmart.utils.SessionManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyListActivity extends AppCompatActivity {

    private Long idLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mylist);


        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.myList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListaCompraDAO listaDAO = new ListaCompraDAO(this);
        ItemListaDAO itemListaDAO = new ItemListaDAO(this);

        Long idUsuario = SessionManager.getIdUsuario(this);

        idLista = listaDAO.buscarListaUsuario(idUsuario);
        ArrayList<Produto> produtos = new ArrayList<>();

        if (idLista != null) {
            produtos = itemListaDAO.listarProdutosDaLista(idLista);
        }

        RecyclerView recyclerView = findViewById(R.id.myListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyListAdapter adapter = new MyListAdapter(produtos, idLista, () -> {
            View cardResultadoAnalise = findViewById(R.id.cardResultadoAnalise);
            cardResultadoAnalise.setVisibility(View.GONE);
        });

        recyclerView.setAdapter(adapter);

        View btnAnalise = findViewById(R.id.btnAnalise);
        btnAnalise.setOnClickListener(v -> realizarAnalise());

        MaterialButton btnHistory = findViewById(R.id.btnHistory);

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MyListActivity.this, AnalysisHistoryActivity.class);
            startActivity(intent);
        });
    }

    private void realizarAnalise() {

        if (idLista == null) {
            Toast.makeText(this, "Lista não encontrada", Toast.LENGTH_SHORT).show();
            return;
        }
        Long idUsuario = SessionManager.getIdUsuario(this);

        EnderecoDAO enderecoDAO = new EnderecoDAO(this);

        Endereco endereco =
                enderecoDAO.buscarPorUsuario(idUsuario);

        if (endereco == null) {

            Toast.makeText(
                    this,
                    "Cadastre seu endereço antes de realizar a análise",
                    Toast.LENGTH_LONG
            ).show();

            startActivity(
                    new Intent(
                            MyListActivity.this,
                            AddressActivity.class
                    )
            );

            return;
        }

        PrecoSupermercadoDAO precoDAO = new PrecoSupermercadoDAO(this);

        List<MercadoRanking> ranking =
                precoDAO.calcularRankingPorLista(idLista);

        if (ranking.isEmpty()) {
            Toast.makeText(
                    this,
                    "Adicione produtos à lista para analisar os preços",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        mostrarResultado(ranking);
    }

    private void mostrarResultado(List<MercadoRanking> ranking) {

        MercadoRanking vencedor = ranking.get(0);
        MercadoRanking maisCaro = ranking.get(ranking.size() - 1);

        double economia = maisCaro.getTotal() - vencedor.getTotal();

        HistoricoAnalise historico = new HistoricoAnalise();
        Long idUsuario = SessionManager.getIdUsuario(this);
        historico.setIdUsuario(idUsuario);
        historico.setIdLista(idLista);
        historico.setMercadoRecomendado(vencedor.getNome());
        historico.setValorTotal(vencedor.getTotal());
        historico.setEconomia(economia);

        HistoricoAnaliseDAO historicoDAO = new HistoricoAnaliseDAO(this);
        historicoDAO.insert(historico);

        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        View cardResultadoAnalise = findViewById(R.id.cardResultadoAnalise);
        TextView txtResumoAnalise = findViewById(R.id.txtResumoAnalise);
        TextView txtMercadoRecomendado = findViewById(R.id.txtMercadoRecomendado);
        TextView txtValorRecomendado = findViewById(R.id.txtValorRecomendado);
        TextView txtEconomia = findViewById(R.id.txtEconomia);
        TextView txtDistancia = findViewById(R.id.txtDistancia);
        RecyclerView rvRankingMercados = findViewById(R.id.rvRankingMercados);

        cardResultadoAnalise.setVisibility(View.VISIBLE);

        txtResumoAnalise.setText(
                ranking.size() + " mercados encontrados"
        );

        txtMercadoRecomendado.setText(
                vencedor.getNome()
        );

        txtValorRecomendado.setText(
                formatoMoeda.format(vencedor.getTotal())
        );

        txtEconomia.setText(
                "Economize " + formatoMoeda.format(economia)
        );

        txtDistancia.setText(
                "Distância não calculada neste MVP"
        );

        rvRankingMercados.setLayoutManager(new LinearLayoutManager(this));

        MercadoRankingAdapter rankingAdapter = new MercadoRankingAdapter(ranking);

        rvRankingMercados.setAdapter(rankingAdapter);
        rvRankingMercados.setItemAnimator(null);
    }
}