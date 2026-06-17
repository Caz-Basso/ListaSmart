package com.example.listasmart.Profile;

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

import com.example.listasmart.R;
import com.example.listasmart.RecyclerView.HistoryAdapter;
import com.example.listasmart.database.dao.HistoricoAnaliseDAO;
import com.example.listasmart.database.model.HistoricoAnalise;

import com.example.listasmart.utils.SessionManager;

import java.util.ArrayList;

public class AnalysisHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_history);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.analysisHistoryPage),
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
                }
        );

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        TextView txtTotalAnalises = findViewById(R.id.txtTotalAnalises);
        RecyclerView recyclerView = findViewById(R.id.recyclerHistory);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HistoricoAnaliseDAO historicoDAO = new HistoricoAnaliseDAO(this);

        Long idUsuario = SessionManager.getIdUsuario(this);

        ArrayList<HistoricoAnalise> lista =
                historicoDAO.listarPorUsuario(idUsuario);

        txtTotalAnalises.setText(String.valueOf(lista.size()));

        HistoryAdapter adapter = new HistoryAdapter(lista);
        recyclerView.setAdapter(adapter);
    }
}