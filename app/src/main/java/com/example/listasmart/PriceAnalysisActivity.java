package com.example.listasmart;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.PriceAnalysisAdapter;
import com.example.listasmart.database.dao.PrecoSupermercadoDAO;
import com.example.listasmart.database.dao.ProdutoDAO;
import com.example.listasmart.database.model.MarketPrice;
import com.example.listasmart.database.model.Produto;

import java.util.List;

public class PriceAnalysisActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private TextView txtProduto;
    private TextView txtMarca;
    private TextView txtMelhorPreco;
    private TextView txtMelhorMercado;
    private TextView txtEconomia;
    private RecyclerView rvMercados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_price_analysis);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.priceAnalysis),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

        initViews();

        long idProduto = getIntent().getLongExtra("idProduto", -1);

        if (idProduto == -1) {
            txtProduto.setText("Produto não encontrado");
            return;
        }

        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        Produto produto = produtoDAO.buscarPorId(idProduto);

        if (produto != null) {
            txtProduto.setText(produto.getNome());
            txtMarca.setText(produto.getMarca());
        }

        carregarDadosDoBanco(produto);

        backBtn.setOnClickListener(v -> finish());
    }

    private void initViews() {

        backBtn = findViewById(R.id.backBtn);
        txtProduto = findViewById(R.id.txtProduto);
        txtMarca = findViewById(R.id.txtMarca);
        txtMelhorPreco = findViewById(R.id.txtMelhorPreco);
        txtMelhorMercado = findViewById(R.id.txtMelhorMercado);
        txtEconomia = findViewById(R.id.txtEconomia);
        rvMercados = findViewById(R.id.rvMercados);
    }

    private void carregarDadosDoBanco(Produto produto) {

        if (produto == null || produto.getId() == null) {
            return;
        }

        PrecoSupermercadoDAO precoDAO = new PrecoSupermercadoDAO(this);

        List<MarketPrice> mercados =
                precoDAO.listarPrecosPorProduto(produto.getId());

        if (mercados.isEmpty()) {
            txtMelhorPreco.setText("Sem preço");
            txtMelhorMercado.setText("Nenhum mercado encontrado");
            txtEconomia.setText("");
            return;
        }

        MarketPrice melhorPreco = mercados.get(0);

        txtMelhorPreco.setText(melhorPreco.getPreco());

        txtMelhorMercado.setText(
                melhorPreco.getMercado() +
                        " • " +
                        melhorPreco.getDistancia()
        );

        txtEconomia.setText("Melhor opção encontrada");

        rvMercados.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvMercados.setAdapter(
                new PriceAnalysisAdapter(mercados)
        );
    }
}