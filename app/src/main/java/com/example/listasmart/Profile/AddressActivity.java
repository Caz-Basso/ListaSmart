package com.example.listasmart.Profile;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

import com.example.listasmart.database.dao.EnderecoDAO;
import com.example.listasmart.database.model.Endereco;
import com.example.listasmart.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import com.example.listasmart.R;

public class AddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_address);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.addressPage),
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
        TextInputEditText editCep =
                findViewById(R.id.editCep);

        TextInputEditText editCidade =
                findViewById(R.id.editCidade);

        TextInputEditText editEstado =
                findViewById(R.id.editEstado);

        TextInputEditText editRua =
                findViewById(R.id.editRua);

        TextInputEditText editNumero =
                findViewById(R.id.editNumero);

        TextInputEditText editComplemento =
                findViewById(R.id.editComplemento);

        TextInputEditText editNomeContato =
                findViewById(R.id.editNomeContato);

        TextInputEditText editTelefoneContato =
                findViewById(R.id.editTelefoneContato);

        MaterialButton btnSalvarEndereco =
                findViewById(R.id.btnSalvarEndereco);

        Long idUsuario =
                SessionManager.getIdUsuario(this);

        EnderecoDAO enderecoDAO =
                new EnderecoDAO(this);

        Endereco endereco =
                enderecoDAO.buscarPorUsuario(idUsuario);

        if (endereco != null) {

            editCep.setText(endereco.getCep());

            editCidade.setText(endereco.getCidade());

            editEstado.setText(endereco.getEstado());

            editRua.setText(endereco.getRua());

            editNumero.setText(endereco.getNumero());

            editComplemento.setText(
                    endereco.getComplemento()
            );

            editNomeContato.setText(
                    endereco.getNomeContato()
            );

            editTelefoneContato.setText(
                    endereco.getTelefoneContato()
            );
        }
        btnSalvarEndereco.setOnClickListener(v -> {

            Endereco enderecoSalvar = new Endereco();

            enderecoSalvar.setIdUsuario(idUsuario);

            enderecoSalvar.setCep(
                    editCep.getText().toString().trim()
            );

            enderecoSalvar.setCidade(
                    editCidade.getText().toString().trim()
            );

            enderecoSalvar.setEstado(
                    editEstado.getText().toString().trim()
            );

            enderecoSalvar.setRua(
                    editRua.getText().toString().trim()
            );

            enderecoSalvar.setNumero(
                    editNumero.getText().toString().trim()
            );

            enderecoSalvar.setComplemento(
                    editComplemento.getText().toString().trim()
            );

            enderecoSalvar.setNomeContato(
                    editNomeContato.getText().toString().trim()
            );

            enderecoSalvar.setTelefoneContato(
                    editTelefoneContato.getText().toString().trim()
            );

            enderecoDAO.salvarOuAtualizar(
                    enderecoSalvar
            );

            Toast.makeText(
                    this,
                    "Endereço salvo com sucesso!",
                    Toast.LENGTH_SHORT
            ).show();
        });

        backBtn.setOnClickListener(v -> finish());
    }
}