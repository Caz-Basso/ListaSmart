package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProdutoCupomAdapter;
import com.example.listasmart.database.model.Produto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class ReceiptDemoActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> cameraLauncher;

    private TextView txtStatus;
    private TextView txtEtapa;
    private TextView txtMercado;

    private TextView txtTituloProdutos;
    private TextView txtQuantidadeProdutos;

    private View cardMercado;
    private View cardAviso;

    private RecyclerView recyclerProdutos;
    private ProdutoCupomAdapter adapter;
    private ArrayList<Produto> listaProdutos;

    private MaterialButton btnConfirmar;
    private CircularProgressIndicator progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_demo);

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.demoReceipt),
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

        txtStatus = findViewById(R.id.txtStatus);
        txtEtapa = findViewById(R.id.txtEtapa);
        txtMercado = findViewById(R.id.txtMercado);
        cardMercado = findViewById(R.id.cardMercado);
        txtTituloProdutos = findViewById(R.id.txtTituloProdutos);
        txtQuantidadeProdutos = findViewById(R.id.txtQuantidadeProdutos);

        cardAviso = findViewById(R.id.cardAviso);

        recyclerProdutos = findViewById(R.id.recyclerProdutos);

        btnConfirmar = findViewById(R.id.btnConfirmar);

        progress = findViewById(R.id.progress);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        btnConfirmar.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Contribuição enviada com sucesso!",
                    Toast.LENGTH_LONG
            ).show();

            finish();
        });

        listaProdutos = new ArrayList<>();

        adapter = new ProdutoCupomAdapter(
                listaProdutos,
                true,
                new ProdutoCupomAdapter.OnProdutoClickListener() {

                    @Override
                    public void onEditar(Produto produto) {

                        Toast.makeText(
                                ReceiptDemoActivity.this,
                                "Editar: " + produto.getNome(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onRemover(Produto produto) {
                        // Não utilizado na demonstração
                    }
                }
        );

        recyclerProdutos.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerProdutos.setAdapter(adapter);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK) {
                        iniciarAnalise();
                    } else {
                        finish();
                    }
                });

        abrirCamera();
    }

    private void abrirCamera() {

        Intent cameraIntent =
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraLauncher.launch(cameraIntent);
    }

    private void iniciarAnalise() {

        Handler handler = new Handler(Looper.getMainLooper());

        txtEtapa.setText("Lendo informações do cupom...");

        handler.postDelayed(() -> {

            cardMercado.setVisibility(View.VISIBLE);

            txtMercado.setText("Angeloni");

            txtEtapa.setText("Estabelecimento identificado");

        }, 1200);

        handler.postDelayed(() -> {

            txtTituloProdutos.setVisibility(View.VISIBLE);

            txtQuantidadeProdutos.setVisibility(View.VISIBLE);

            txtQuantidadeProdutos.setText(
                    "3 produtos identificados"
            );

            txtEtapa.setText("Identificando produtos...");

        }, 2500);

        handler.postDelayed(() -> {

            recyclerProdutos.setVisibility(View.VISIBLE);

            carregarProdutosMockados();

        }, 3800);

        handler.postDelayed(() -> {

            cardAviso.setVisibility(View.VISIBLE);

        }, 6200);

        handler.postDelayed(() -> {

            progress.setVisibility(View.GONE);

            txtStatus.setText("Análise concluída");

            txtEtapa.setText(
                    "Revise as informações encontradas antes de enviar."
            );

            btnConfirmar.setVisibility(View.VISIBLE);

        }, 7200);
    }

    private void carregarProdutosMockados() {

        listaProdutos.clear();

        Produto p1 = new Produto();
        p1.setNome("Arroz Branco");
        p1.setMarca("Tio João");
        p1.setQuantidade(2);
        p1.setPrecoMockado(24.90);

        Produto p2 = new Produto();
        p2.setNome("Feijão Preto");
        p2.setMarca("Camil");
        p2.setQuantidade(1);
        p2.setPrecoMockado(8.99);

        Produto p3 = new Produto();
        p3.setNome("Leite Integral");
        p3.setMarca("Piracanjuba");
        p3.setQuantidade(3);
        p3.setPrecoMockado(5.49);

        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(() -> {

            txtEtapa.setText(
                    "Produto encontrado: " + p1.getNome()
            );

            adicionarProduto(p1);

        }, 0);

        handler.postDelayed(() -> {

            txtEtapa.setText(
                    "Produto encontrado: " + p2.getNome()
            );

            adicionarProduto(p2);

        }, 800);

        handler.postDelayed(() -> {

            txtEtapa.setText(
                    "Produto encontrado: " + p3.getNome()
            );

            adicionarProduto(p3);

        }, 1600);

        handler.postDelayed(() -> {

            txtEtapa.setText(
                    "Verificando base de dados..."
            );

        }, 2500);
    }

    private void adicionarProduto(Produto produto) {

        listaProdutos.add(produto);

        int posicao = listaProdutos.size() - 1;

        adapter.notifyItemInserted(posicao);

        recyclerProdutos.smoothScrollToPosition(posicao);
    }
}