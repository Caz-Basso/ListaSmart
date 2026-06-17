package com.example.listasmart.Profile;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.listasmart.database.dao.UsuarioDAO;
import com.example.listasmart.database.model.Usuario;
import com.example.listasmart.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import com.example.listasmart.R;

public class SecurityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_security);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.securityPage),
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

        TextView txtEmailAtual = findViewById(R.id.txtEmailAtual);

        Long idUsuario = SessionManager.getIdUsuario(this);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

        if (usuario != null) {
            txtEmailAtual.setText(usuario.getEmail());
        }

        TextInputEditText editNovoEmail =
                findViewById(R.id.editNovoEmail);

        TextInputEditText editSenhaAtual =
                findViewById(R.id.editSenhaAtual);

        TextInputEditText editNovaSenha =
                findViewById(R.id.editNovaSenha);

        TextInputEditText editConfirmarSenha =
                findViewById(R.id.editConfirmarSenha);

        MaterialButton btnAlterarSenha =
                findViewById(R.id.btnAlterarSenha);

        MaterialButton btnAlterarEmail =
                findViewById(R.id.btnAlterarEmail);
        btnAlterarEmail.setOnClickListener(v -> {

            String novoEmail =
                    editNovoEmail.getText().toString().trim();

            if (novoEmail.isEmpty()) {
                Toast.makeText(
                        this,
                        "Informe um novo email",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            int linhasAfetadas =
                    usuarioDAO.alterarEmail(
                            idUsuario,
                            novoEmail
                    );

            if (linhasAfetadas > 0) {

                txtEmailAtual.setText(novoEmail);
                editNovoEmail.setText("");

                Toast.makeText(
                        this,
                        "Email atualizado com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Erro ao atualizar email",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        btnAlterarSenha.setOnClickListener(v -> {

            String senhaAtual =
                    editSenhaAtual.getText().toString().trim();

            String novaSenha =
                    editNovaSenha.getText().toString().trim();

            String confirmarSenha =
                    editConfirmarSenha.getText().toString().trim();

            if (senhaAtual.isEmpty()
                    || novaSenha.isEmpty()
                    || confirmarSenha.isEmpty()) {

                Toast.makeText(
                        this,
                        "Preencha todos os campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (!novaSenha.equals(confirmarSenha)) {

                Toast.makeText(
                        this,
                        "As senhas não coincidem",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            if (!usuarioDAO.validarSenha(idUsuario, senhaAtual)) {

                Toast.makeText(
                        this,
                        "Senha atual incorreta",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            int linhasAfetadas =
                    usuarioDAO.alterarSenha(
                            idUsuario,
                            novaSenha
                    );

            if (linhasAfetadas > 0) {

                editSenhaAtual.setText("");
                editNovaSenha.setText("");
                editConfirmarSenha.setText("");

                Toast.makeText(
                        this,
                        "Senha atualizada com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Erro ao atualizar senha",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        backBtn.setOnClickListener(v -> finish());
    }
}
