package com.example.listasmart.Profile;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.listasmart.database.dao.UsuarioDAO;
import com.example.listasmart.database.model.Usuario;
import com.example.listasmart.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import com.example.listasmart.R;

public class ProfileInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_profile_info);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.profileInfoPage),
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
        TextInputEditText editNomeCompleto = findViewById(R.id.editNomeCompleto);
        TextInputEditText editNomeUsuario = findViewById(R.id.editNomeUsuario);
        TextInputEditText editEmail = findViewById(R.id.editEmail);
        MaterialButton btnSalvar = findViewById(R.id.btnSalvar);

        Long idUsuario = SessionManager.getIdUsuario(this);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

        if (usuario != null) {
            editNomeCompleto.setText(usuario.getNomeCompleto());
            editNomeUsuario.setText(usuario.getNomeUsuario());
            editEmail.setText(usuario.getEmail());
        }
        btnSalvar.setOnClickListener(v -> {
            String nomeCompleto = editNomeCompleto.getText().toString().trim();
            String nomeUsuario = editNomeUsuario.getText().toString().trim();

            if (nomeCompleto.isEmpty() || nomeUsuario.isEmpty()) {
                Toast.makeText(
                        this,
                        "Preencha nome completo e nome de usuário",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            int linhasAfetadas = usuarioDAO.atualizarPerfil(
                    idUsuario,
                    nomeCompleto,
                    nomeUsuario
            );

            if (linhasAfetadas > 0) {
                Toast.makeText(
                        this,
                        "Perfil atualizado com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Erro ao atualizar perfil",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        backBtn.setOnClickListener(v -> finish());
    }
}
