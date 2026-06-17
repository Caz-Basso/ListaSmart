package com.example.listasmart.Profile;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.listasmart.database.dao.HistoricoAnaliseDAO;
import com.example.listasmart.utils.SessionManager;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.listasmart.R;

public class StatisticsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_statistics);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.statisticsPage),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        ImageButton backBtn = findViewById(R.id.backBtn);

        TextView txtEconomiaTotal =
                findViewById(R.id.txtEconomiaTotal);

        TextView txtTotalAnalises =
                findViewById(R.id.txtTotalAnalises);

        TextView txtMercadoFavorito =
                findViewById(R.id.txtMercadoFavorito);

        TextView txtMaiorEconomia =
                findViewById(R.id.txtMaiorEconomia);

        TextView txtProdutos =
                findViewById(R.id.txtProdutos);

        Long idUsuario =
                SessionManager.getIdUsuario(this);

        HistoricoAnaliseDAO historicoDAO =
                new HistoricoAnaliseDAO(this);

        ListaCompraDAO listaDAO =
                new ListaCompraDAO(this);

        ItemListaDAO itemListaDAO =
                new ItemListaDAO(this);

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(
                        new Locale("pt", "BR")
                );
        int totalAnalises =
                historicoDAO.contarAnalises(idUsuario);

        double economiaTotal =
                historicoDAO.calcularEconomiaTotal(idUsuario);

        double maiorEconomia =
                historicoDAO.buscarMaiorEconomia(idUsuario);

        String mercadoFavorito =
                historicoDAO.buscarMercadoMaisEscolhido(idUsuario);

        Long idLista =
                listaDAO.buscarListaUsuario(idUsuario);

        int totalProdutos = 0;

        if (idLista != null) {
            totalProdutos =
                    itemListaDAO.contarItens(idLista);
        }

        txtTotalAnalises.setText(
                String.valueOf(totalAnalises)
        );

        txtEconomiaTotal.setText(
                formatoMoeda.format(economiaTotal)
        );

        txtMaiorEconomia.setText(
                formatoMoeda.format(maiorEconomia)
        );

        txtMercadoFavorito.setText(
                mercadoFavorito
        );

        txtProdutos.setText(
                String.valueOf(totalProdutos)
        );

        backBtn.setOnClickListener(v -> finish());
    }
}
