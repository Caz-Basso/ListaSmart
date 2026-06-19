package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProdutoCupomAdapter;
import com.example.listasmart.database.model.Produto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualReceiptActivity extends AppCompatActivity {

    private MaterialButton btnAddProductManual;
    private MaterialButton btnFinalizarCompra;

    private RecyclerView recyclerProdutos;
    private CardView cardFormularioProduto;
    private LinearLayout bottomButtonsContainer;

    private TextView txtFormTitulo;
    private TextInputEditText editFormNomeProduto;
    private TextInputEditText editFormMarca;
    private AutoCompleteTextView dropdownFormCategory;
    private TextInputEditText editFormQuantidade;
    private TextInputEditText editFormPrecoUnitario;

    private MaterialButton btnFormCancelar;
    private MaterialButton btnFormAcao;

    private ProdutoCupomAdapter adapter;
    private ArrayList<Produto> listaProdutos;

    private int posicaoProdutoEmEdicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manual_receipt);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.manualReceiptMainLayout),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        btnAddProductManual = findViewById(R.id.btnAddProductManual);
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra);

        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        cardFormularioProduto = findViewById(R.id.cardFormularioProduto);
        bottomButtonsContainer = findViewById(R.id.bottomButtonsContainer);

        View formInclude = findViewById(R.id.formProdutoInline);

        txtFormTitulo = formInclude.findViewById(R.id.txtFormTitulo);
        editFormNomeProduto = formInclude.findViewById(R.id.editFormNomeProduto);
        editFormMarca = formInclude.findViewById(R.id.editFormMarca);
        dropdownFormCategory = formInclude.findViewById(R.id.dropdownFormCategory);
        editFormQuantidade = formInclude.findViewById(R.id.editFormQuantidade);
        editFormPrecoUnitario = formInclude.findViewById(R.id.editFormPrecoUnitario);
        btnFormCancelar = formInclude.findViewById(R.id.btnFormCancelar);
        btnFormAcao = formInclude.findViewById(R.id.btnFormAcao);

        List<String> categorias = Arrays.asList("Alimentos", "Bebidas", "Limpeza", "Higiene", "Outros");

        ArrayAdapter<String> categoriasAdapter =
                new ArrayAdapter<>(this, R.layout.list_item, categorias);

        dropdownFormCategory.setAdapter(categoriasAdapter);

        listaProdutos = new ArrayList<>();

        adapter = new ProdutoCupomAdapter(
                listaProdutos,
                true,
                new ProdutoCupomAdapter.OnProdutoClickListener() {
                    @Override
                    public void onEditar(Produto produto) {
                        prepararFormularioParaEditar(produto);
                    }

                    @Override
                    public void onRemover(Produto produto) { }
                }
        );

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProdutos.setAdapter(adapter);

        btnAddProductManual.setOnClickListener(v -> {
            prepararFormularioParaAdicionar();
            exibirFormularioInline(true);
        });

        btnFinalizarCompra.setOnClickListener(v -> {
            if (listaProdutos.isEmpty()) {
                Toast.makeText(this, "Adicione pelo menos um produto.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Registro finalizado com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        });

        btnFormCancelar.setOnClickListener(v -> {
            limparCamposFormulario();
            exibirFormularioInline(false);
        });

        btnFormAcao.setOnClickListener(v -> finalizarAcaoFormulario());

        handleScannerData();
    }

    private void handleScannerData() {
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("produtos_escaneados")) {

            ArrayList<Produto> produtosEscaneados =
                    (ArrayList<Produto>) intent.getSerializableExtra("produtos_escaneados");

            if (produtosEscaneados != null && !produtosEscaneados.isEmpty()) {
                listaProdutos.clear();
                listaProdutos.addAll(produtosEscaneados);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void prepararFormularioParaEditar(Produto produto) {
        posicaoProdutoEmEdicao = listaProdutos.indexOf(produto);

        editFormNomeProduto.setText(produto.getNome());
        editFormMarca.setText(produto.getMarca());
        editFormQuantidade.setText(String.valueOf(produto.getQuantidade()));
        editFormPrecoUnitario.setText(String.valueOf(produto.getPrecoMockado()));

        txtFormTitulo.setText("Editar Produto");
        btnFormAcao.setText("Salvar");

        exibirFormularioInline(true);
    }

    private void prepararFormularioParaAdicionar() {
        limparCamposFormulario();

        posicaoProdutoEmEdicao = -1;

        txtFormTitulo.setText("Novo Produto");
        btnFormAcao.setText("Adicionar");
    }

    private void exibirFormularioInline(boolean exibir) {
        if (exibir) {
            cardFormularioProduto.setVisibility(View.VISIBLE);
            bottomButtonsContainer.setVisibility(View.GONE);

            if (!listaProdutos.isEmpty()) {
                recyclerProdutos.smoothScrollToPosition(listaProdutos.size() - 1);
            }
        } else {
            cardFormularioProduto.setVisibility(View.GONE);
            bottomButtonsContainer.setVisibility(View.VISIBLE);
        }
    }

    private void limparCamposFormulario() {
        editFormNomeProduto.setText("");
        editFormMarca.setText("");
        dropdownFormCategory.setText("", false);
        editFormQuantidade.setText("1");
        editFormPrecoUnitario.setText("");

        editFormNomeProduto.setError(null);
    }

    private void finalizarAcaoFormulario() {
        String nome = editFormNomeProduto.getText().toString().trim();

        if (nome.isEmpty()) {
            editFormNomeProduto.setError("Informe o nome");
            editFormNomeProduto.requestFocus();
            return;
        }

        boolean isEdicao = posicaoProdutoEmEdicao != -1;

        Produto produto = isEdicao
                ? listaProdutos.get(posicaoProdutoEmEdicao)
                : new Produto();

        produto.setNome(nome);
        produto.setMarca(editFormMarca.getText().toString().trim());

        try {
            produto.setQuantidade(Integer.parseInt(editFormQuantidade.getText().toString()));
        } catch (Exception e) {
            produto.setQuantidade(1);
        }

        try {
            produto.setPrecoMockado(Double.parseDouble(editFormPrecoUnitario.getText().toString()));
        } catch (Exception e) {
            produto.setPrecoMockado(0);
        }

        if (isEdicao) {
            adapter.notifyItemChanged(posicaoProdutoEmEdicao);
            Toast.makeText(this, "Produto atualizado!", Toast.LENGTH_SHORT).show();
        } else {
            listaProdutos.add(produto);
            adapter.notifyItemInserted(listaProdutos.size() - 1);
            Toast.makeText(this, "Produto adicionado!", Toast.LENGTH_SHORT).show();
        }

        posicaoProdutoEmEdicao = -1;

        limparCamposFormulario();
        exibirFormularioInline(false);
    }
}