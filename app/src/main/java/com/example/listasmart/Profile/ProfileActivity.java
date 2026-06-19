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

import android.widget.TextView;

import com.example.listasmart.database.dao.UsuarioDAO;
import com.example.listasmart.database.model.Usuario;
import com.example.listasmart.utils.SessionManager;
import com.example.listasmart.database.dao.EnderecoDAO;
import com.example.listasmart.database.dao.HistoricoAnaliseDAO;
import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;

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

        carregarDadosUsuario();

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

        menuSeguranca.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                SecurityActivity.class
                        )
                )
        );

        menuEnderecoConta.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                AddressActivity.class
                        )
                )
        );

        menuHistoricoAnalises.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                AnalysisHistoryActivity.class
                        )
                )
        );

        menuEstatisticas.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                StatisticsActivity.class
                        )
                )
        );

        menuSobre.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                ProfileActivity.this,
                                AboutActivity.class
                        )
                )
        );

        menuExcluirConta.setOnClickListener(v -> {

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Excluir conta")
                    .setMessage(
                            "Tem certeza que deseja excluir sua conta?\n\n" +
                                    "Todos os seus dados serão removidos permanentemente e esta ação não poderá ser desfeita."
                    )
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Excluir", (dialog, which) -> {

                        Long idUsuario = SessionManager.getIdUsuario(this);

                        EnderecoDAO enderecoDAO =
                                new EnderecoDAO(this);

                        HistoricoAnaliseDAO historicoDAO =
                                new HistoricoAnaliseDAO(this);

                        ListaCompraDAO listaDAO =
                                new ListaCompraDAO(this);

                        ItemListaDAO itemListaDAO =
                                new ItemListaDAO(this);

                        UsuarioDAO usuarioDAO =
                                new UsuarioDAO(this);

                        Long idLista =
                                listaDAO.buscarListaUsuario(idUsuario);

                        if (idLista != null) {
                            itemListaDAO.excluirPorLista(idLista);
                        }

                        enderecoDAO.excluirPorUsuario(idUsuario);

                        historicoDAO.excluirPorUsuario(idUsuario);

                        listaDAO.excluirPorUsuario(idUsuario);

                        usuarioDAO.excluirUsuario(idUsuario);

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
    private void carregarDadosUsuario() {
        TextView txtNomeCompleto = findViewById(R.id.txtNomeCompleto);
        TextView txtNomeUsuario = findViewById(R.id.txtNomeUsuario);

        Long idUsuario = SessionManager.getIdUsuario(this);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

        if (usuario != null) {
            txtNomeCompleto.setText(usuario.getNomeCompleto());
            txtNomeUsuario.setText("@" + usuario.getNomeUsuario());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        carregarDadosUsuario();
    }
}