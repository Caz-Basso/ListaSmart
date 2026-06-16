package com.example.listasmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listasmart.database.dao.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEdit;
    private EditText senhaEdit;
    private Button entrarBtn;
    private TextView criarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs =
                getSharedPreferences("listasmart", MODE_PRIVATE);

        if (prefs.getBoolean("logado", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        emailEdit = findViewById(R.id.emailEdit);
        senhaEdit = findViewById(R.id.senhaEdit);
        entrarBtn = findViewById(R.id.entrarBtn);
        criarConta = findViewById(R.id.criarConta);

        entrarBtn.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String senha = senhaEdit.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO(this);
            boolean loginValido = usuarioDAO.validarLogin(email, senha);

            if (loginValido) {

                Long idUsuario = usuarioDAO.buscarIdPorLogin(
                        email,
                        senha
                );

                SharedPreferences prefsLogin =
                        getSharedPreferences("listasmart", MODE_PRIVATE);

                prefsLogin.edit()
                        .putBoolean("logado", true)
                        .putLong("id_usuario", idUsuario)
                        .apply();

                Toast.makeText(
                        this,
                        "Login realizado com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(
                        this,
                        "Email ou senha inválidos",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        criarConta.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });
    }
}