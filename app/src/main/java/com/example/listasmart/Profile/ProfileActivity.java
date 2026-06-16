package com.example.listasmart.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.listasmart.LoginActivity;
import com.example.listasmart.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.profilePage),
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
        Button logoutBtn = findViewById(R.id.logoutBtn);

        LinearLayout menuInfoPerfil = findViewById(R.id.menuInfoPerfil);
        LinearLayout menuSeguranca = findViewById(R.id.menuSeguranca);
        LinearLayout menuEnderecoConta = findViewById(R.id.menuEnderecoConta);
        LinearLayout menuHistoricoAnalises = findViewById(R.id.menuHistoricoAnalises);
        LinearLayout menuEstatisticas = findViewById(R.id.menuEstatisticas);
        LinearLayout menuSobre = findViewById(R.id.menuSobre);
        LinearLayout menuExcluirConta = findViewById(R.id.menuExcluirConta);

        backBtn.setOnClickListener(v -> {
            finish();

            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        });

        menuInfoPerfil.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                ProfileInfoActivity.class
                        )
                )
        );

        // Segurança
        menuSeguranca.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                SecurityActivity.class
                        )
                )
        );

        // Endereço
        menuEnderecoConta.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                AddressActivity.class
                        )
                )
        );

        // Histórico de análises
        menuHistoricoAnalises.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                AnalysisHistoryActivity.class
                        )
                )
        );

        // Estatísticas
        menuEstatisticas.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                StatisticsActivity.class
                        )
                )
        );

        // Sobre o aplicativo
        menuSobre.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                AboutActivity.class
                        )
                )
        );

        // Excluir conta
        menuExcluirConta.setOnClickListener(v -> {

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Excluir conta")
                    .setMessage(
                            "Tem certeza que deseja excluir sua conta?\n\n" +
                                    "Todos os seus dados serão removidos permanentemente e esta ação não poderá ser desfeita."
                    )
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Excluir", (dialog, which) -> {

                        SharedPreferences prefs =
                                getSharedPreferences("listasmart", MODE_PRIVATE);

                        prefs.edit()
                                .clear()
                                .apply();

                        Intent intent =
                                new Intent(
                                        ProfileActivity.this,
                                        LoginActivity.class
                                );

                        intent.addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                        );

                        startActivity(intent);
                        finish();

                    })
                    .show();

        });

        logoutBtn.setOnClickListener(v -> {

            SharedPreferences prefs =
                    getSharedPreferences("listasmart", MODE_PRIVATE);

            prefs.edit()
                    .clear()
                    .apply();

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            LoginActivity.class
                    );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }
}