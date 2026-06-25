package com.example.listasmart;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProdutoCupomAdapter;
import com.example.listasmart.database.dao.CategoriaDAO;
import com.example.listasmart.database.dao.PrecoSupermercadoDAO;
import com.example.listasmart.database.dao.ProdutoDAO;
import com.example.listasmart.database.dao.SupermercadoDAO;
import com.example.listasmart.database.model.Produto;
import com.example.listasmart.database.model.Supermercado;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualReceiptActivity extends AppCompatActivity {

    private MaterialButton btnAddProduct;
    private MaterialButton btnSalvarProduto;
    private MaterialButton btnCancelarProduto;
    private MaterialButton btnSalvarCompra;

    private RecyclerView recyclerProdutos;
    private ProdutoCupomAdapter adapter;
    private ArrayList<Produto> listaProdutos;

    private View formProduto;

    private TextInputEditText editMercado;
    private TextInputEditText editNomeProduto;
    private TextInputEditText editMarca;
    private TextInputEditText editQuantidade;
    private TextInputEditText editPrecoUnitario;

    private NestedScrollView scrollView;
    private AutoCompleteTextView dropdownCategoria;

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
                }
        );

        configurarHeader();
        inicializarViews();
        configurarCategorias();
        configurarRecyclerView();
        configurarEventos();
    }

    private void configurarHeader() {
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }

    private void inicializarViews() {
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnSalvarProduto = findViewById(R.id.btnSalvarProduto);
        btnCancelarProduto = findViewById(R.id.btnCancelarProduto);
        btnSalvarCompra = findViewById(R.id.btnSalvarCompra);

        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        formProduto = findViewById(R.id.formProduto);

        editMercado = findViewById(R.id.editMercado);
        editNomeProduto = findViewById(R.id.editNomeProduto);
        editMarca = findViewById(R.id.editMarca);
        editQuantidade = findViewById(R.id.editQuantidade);
        editPrecoUnitario = findViewById(R.id.editPrecoUnitario);

        dropdownCategoria = findViewById(R.id.dropdown_category);
        scrollView = findViewById(R.id.scrollView);

        ViewCompat.setOnApplyWindowInsetsListener(scrollView, (view, insets) -> {
            Insets imeInsets =
                    insets.getInsets(WindowInsetsCompat.Type.ime());

            Insets navInsets =
                    insets.getInsets(WindowInsetsCompat.Type.navigationBars());

            view.setPadding(
                    view.getPaddingLeft(),
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    imeInsets.bottom + navInsets.bottom + 32
            );

            return insets;
        });
    }

    private void configurarCategorias() {
        List<String> categorias = Arrays.asList(
                "Alimentos",
                "Bebidas",
                "Limpeza",
                "Higiene",
                "Padaria",
                "Açougue",
                "Frios",
                "Outros"
        );

        ArrayAdapter<String> categoriasAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        categorias
                );

        dropdownCategoria.setAdapter(categoriasAdapter);
    }

    private void configurarRecyclerView() {
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
                        int posicao = listaProdutos.indexOf(produto);

                        if (posicao >= 0) {
                            listaProdutos.remove(posicao);
                            adapter.notifyItemRemoved(posicao);
                        }
                    }
                }
        );

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProdutos.setAdapter(adapter);
    }

    private void configurarEventos() {
        btnAddProduct.setOnClickListener(v -> {
            formProduto.setVisibility(View.VISIBLE);
            btnAddProduct.setVisibility(View.GONE);

            formProduto.post(() -> {
                scrollView.smoothScrollTo(0, formProduto.getTop());
                editNomeProduto.requestFocus();

                InputMethodManager imm =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                imm.showSoftInput(
                        editNomeProduto,
                        InputMethodManager.SHOW_IMPLICIT
                );
            });
        });

        btnCancelarProduto.setOnClickListener(v -> {
            limparFormulario();
            esconderFormulario();
        });

        btnSalvarProduto.setOnClickListener(v -> adicionarProduto());

        btnSalvarCompra.setOnClickListener(v -> salvarCompra());
    }

    private void adicionarProduto() {
        String nome =
                editNomeProduto.getText()
                        .toString()
                        .trim();

        if (nome.isEmpty()) {
            editNomeProduto.setError("Informe o nome do produto");
            return;
        }

        Produto produto = new Produto();
        produto.setNome(nome);

        produto.setMarca(
                editMarca.getText()
                        .toString()
                        .trim()
        );

        try {
            produto.setQuantidade(
                    Integer.parseInt(
                            editQuantidade.getText()
                                    .toString()
                                    .trim()
                    )
            );
        } catch (Exception e) {
            produto.setQuantidade(1);
        }

        try {
            produto.setPrecoMockado(
                    Double.parseDouble(
                            editPrecoUnitario.getText()
                                    .toString()
                                    .trim()
                                    .replace(",", ".")
                    )
            );
        } catch (Exception e) {
            produto.setPrecoMockado(0);
        }

        String categoria = dropdownCategoria.getText().toString().trim();

        if (categoria.isEmpty()) {
            categoria = "Outros";
        }

        produto.setCategoria(categoria);

        listaProdutos.add(produto);

        adapter.notifyItemInserted(listaProdutos.size() - 1);

        limparFormulario();
        esconderFormulario();
    }

    private void salvarCompra() {
        String nomeMercado =
                editMercado.getText()
                        .toString()
                        .trim();

        if (nomeMercado.isEmpty()) {
            editMercado.setError("Informe o mercado");
            return;
        }

        if (listaProdutos.isEmpty()) {
            Toast.makeText(
                    this,
                    "Adicione pelo menos um produto",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ProdutoDAO produtoDAO = new ProdutoDAO(this);
        SupermercadoDAO supermercadoDAO = new SupermercadoDAO(this);
        CategoriaDAO categoriaDAO = new CategoriaDAO(this);
        PrecoSupermercadoDAO precoDAO = new PrecoSupermercadoDAO(this);

        Long idSupermercado =
                supermercadoDAO.buscarIdPorNomeNormalizado(nomeMercado);

        if (idSupermercado == null) {
            Supermercado supermercado = new Supermercado();
            supermercado.setNomeFantasia(nomeMercado);
            supermercado.setEndereco("");
            supermercado.setBairro("");
            supermercado.setCidade("");
            supermercado.setEstado("");
            supermercado.setLatitude(0);
            supermercado.setLongitude(0);

            idSupermercado = supermercadoDAO.insert(supermercado);
        }

        for (Produto item : listaProdutos) {
            String nomeProduto = item.getNome();
            String marca = item.getMarca();

            Long idProduto =
                    produtoDAO.buscarIdPorNomeMarcaNormalizado(
                            nomeProduto,
                            marca
                    );

            if (idProduto == null) {
                Long idCategoria =
                        categoriaDAO.buscarIdPorNomeNormalizado(
                                item.getCategoria()
                        );

                if (idCategoria == null) {
                    idCategoria =
                            categoriaDAO.buscarIdPorNomeNormalizado("Outros");
                }

                if (idCategoria == null) {
                    idCategoria = 1L;
                }

                Produto produto = new Produto();
                produto.setNome(nomeProduto);
                produto.setMarca(marca);
                produto.setIdCategoria(idCategoria);
                produto.setImagemUrl("produto_padrao");
                produto.setVerificado(0);

                idProduto = produtoDAO.insert(produto);
            }

            precoDAO.inserirOuAtualizarPreco(
                    idProduto,
                    idSupermercado,
                    item.getPrecoMockado()
            );
        }

        Toast.makeText(
                this,
                "Compra salva com sucesso!",
                Toast.LENGTH_SHORT
        ).show();

        listaProdutos.clear();
        adapter.notifyDataSetChanged();

        editMercado.setText("");
    }

    private void esconderFormulario() {
        formProduto.setVisibility(View.GONE);
        btnAddProduct.setVisibility(View.VISIBLE);
    }

    private void limparFormulario() {
        editNomeProduto.setText("");
        editMarca.setText("");
        editQuantidade.setText("");
        editPrecoUnitario.setText("");
        dropdownCategoria.setText("");
    }
}