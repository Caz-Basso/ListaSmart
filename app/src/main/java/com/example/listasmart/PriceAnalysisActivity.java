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
import com.example.listasmart.database.model.MarketPrice;
import com.example.listasmart.database.model.Produto;

import java.util.ArrayList;
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

        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        if (produto != null) {
            txtProduto.setText(produto.getNome());
            txtMarca.setText(produto.getMarca());
        }

        carregarDadosMockados();

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

    private void carregarDadosMockados() {

        txtMelhorPreco.setText("R$ 24,99");
        txtMelhorMercado.setText("Giassi • 2,1 km");

        txtEconomia.setText("Economia de R$ 2,46 em relação à média");

        rvMercados.setLayoutManager(
                new LinearLayoutManager(this)
        );

        List<MarketPrice> mercados = new ArrayList<>();

        mercados.add(new MarketPrice(
                1,
                "Giassi",
                "2,1 km",
                "R$ 24,99"
        ));

        mercados.add(new MarketPrice(
                2,
                "Bistek",
                "3,5 km",
                "R$ 25,80"
        ));

        mercados.add(new MarketPrice(
                3,
                "Fort",
                "1,8 km",
                "R$ 28,90"
        ));

        mercados.add(new MarketPrice(
                4,
                "Angeloni",
                "4,2 km",
                "R$ 31,50"
        ));

        rvMercados.setAdapter(
                new PriceAnalysisAdapter(mercados)
        );
    }
}