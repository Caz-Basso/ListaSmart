package com.example.listasmart;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProdutoCupomAdapter;
import com.example.listasmart.database.model.Produto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualReceiptActivity extends AppCompatActivity {

    private MaterialButton btnAddProduct;
    private RecyclerView recyclerProdutos;

    private ProdutoCupomAdapter adapter;
    private ArrayList<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manual_receipt);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.manualReceipt),
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
                });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        btnAddProduct = findViewById(R.id.btnAddProduct);
        recyclerProdutos = findViewById(R.id.recyclerProdutos);

        listaProdutos = new ArrayList<>();

        adapter = new ProdutoCupomAdapter(
                listaProdutos,
                false,
                new ProdutoCupomAdapter.OnProdutoClickListener() {

                    @Override
                    public void onEditar(Produto produto) {
                    }

                    @Override
                    public void onRemover(Produto produto) {

                        listaProdutos.remove(produto);

                        adapter.notifyDataSetChanged();
                    }
                }
        );

        recyclerProdutos.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerProdutos.setAdapter(adapter);

        btnAddProduct.setOnClickListener(v ->
                mostrarDialogAdicionarProduto());
    }

    private void mostrarDialogAdicionarProduto() {

        View view = getLayoutInflater().inflate(
                R.layout.dialog_add_product,
                null
        );

        android.app.Dialog dialog =
                new android.app.Dialog(this);

        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT)
            );
        }

        dialog.show();

        AutoCompleteTextView dropdownCategoria = view.findViewById(R.id.dropdown_category);

        List<String> categorias = Arrays.asList(
                "Alimentos",
                "Bebidas",
                "Limpeza",
                "Higiene",
                "Outros"
        );

        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categorias
        );

        dropdownCategoria.setAdapter(categoriasAdapter);

        MaterialButton btnCancelar = view.findViewById(R.id.btnCancelar);
        MaterialButton btnAdicionar = view.findViewById(R.id.btnAdicionar);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnAdicionar.setOnClickListener(v -> {

            TextInputEditText editNome = view.findViewById(R.id.editNomeProduto);
            TextInputEditText editMarca = view.findViewById(R.id.editMarca);
            TextInputEditText editQuantidade = view.findViewById(R.id.editQuantidade);
            TextInputEditText editPreco = view.findViewById(R.id.editPrecoUnitario);
            String nome = editNome.getText().toString().trim();

            if (nome.isEmpty()) {
                editNome.setError("Informe o nome");

                return;
            }

            Produto produto = new Produto();

            produto.setNome(nome);
            produto.setMarca(editMarca.getText().toString().trim());

            try {
                produto.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));
            } catch (Exception e) {
                produto.setQuantidade(1);
            }

            try {
                produto.setPrecoMockado(Double.parseDouble(editPreco.getText().toString())
                );

            } catch (Exception e) {
                produto.setPrecoMockado(0);
            }

            listaProdutos.add(produto);

            adapter.notifyItemInserted(listaProdutos.size() - 1);

            Toast.makeText(
                    this,
                    "Produto adicionado!",
                    Toast.LENGTH_SHORT
            ).show();

            dialog.dismiss();
        });
    }
}