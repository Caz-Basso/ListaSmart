package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listasmart.database.dao.UsuarioDAO;
import com.example.listasmart.database.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText nomeUsuarioRegister;
    private EditText nomeCompleto;
    private EditText emailRegister;
    private EditText senhaRegister;
    private Button cadastroBtn;
    private TextView criarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nomeUsuarioRegister = findViewById(R.id.nomeUsuarioRegister);
        nomeCompleto = findViewById(R.id.nomeCompleto);
        emailRegister = findViewById(R.id.emailRegister);
        senhaRegister = findViewById(R.id.senhaRegister);
        cadastroBtn = findViewById(R.id.cadastroBtn);
        criarConta = findViewById(R.id.criarConta);

        cadastroBtn.setOnClickListener(v -> {
            String nomeUsuario = nomeUsuarioRegister.getText().toString().trim();
            String nome = nomeCompleto.getText().toString().trim();
            String email = emailRegister.getText().toString().trim();
            String senha = senhaRegister.getText().toString().trim();

            if (nomeUsuario.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setNomeCompleto(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            UsuarioDAO usuarioDAO = new UsuarioDAO(this);
            long id = usuarioDAO.insert(usuario);

            if (id > 0) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar. Usuário ou email já existe.", Toast.LENGTH_LONG).show();
            }
        });

        criarConta.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}