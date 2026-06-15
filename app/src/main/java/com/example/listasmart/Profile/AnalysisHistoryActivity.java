package com.example.listasmart.Profile;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.R;
import com.example.listasmart.RecyclerView.HistoryAdapter;
import com.example.listasmart.database.model.AnalysisHistory;

import java.util.ArrayList;
import java.util.List;

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

        backBtn.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerHistory);

        List<AnalysisHistory> lista = new ArrayList<>();

        lista.add(new AnalysisHistory(
                "Lista da Semana",
                "14/06/2026 • 18:42",
                "Giassi",
                "R$ 24,90"
        ));

        HistoryAdapter adapter = new HistoryAdapter(lista);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
