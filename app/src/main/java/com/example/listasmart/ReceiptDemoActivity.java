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

    private TextView txtStatus, txtEtapa, txtMercado, txtProdutos;
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
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        txtStatus = findViewById(R.id.txtStatus);
        txtEtapa = findViewById(R.id.txtEtapa);
        txtMercado = findViewById(R.id.txtMercado);
        txtProdutos = findViewById(R.id.txtProdutos);

        cardAviso = findViewById(R.id.cardAviso);

        recyclerProdutos = findViewById(R.id.recyclerProdutos);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        progress = findViewById(R.id.progress);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        listaProdutos = new ArrayList<>();

        adapter = new ProdutoCupomAdapter(
                listaProdutos,
                true,
                new ProdutoCupomAdapter.OnProdutoClickListener() {
                    @Override
                    public void onEditar(Produto produto) {
                        Toast.makeText(ReceiptDemoActivity.this,
                                "Editar: " + produto.getNome(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRemover(Produto produto) { }
                }
        );

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProdutos.setAdapter(adapter);

        btnConfirmar.setOnClickListener(v -> {
            Toast.makeText(this, "Contribuição enviada com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        });

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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void iniciarAnalise() {
        Handler handler = new Handler(Looper.getMainLooper());

        txtEtapa.setText("Lendo informações do cupom...");

        handler.postDelayed(() -> txtMercado.setVisibility(View.VISIBLE), 1200);

        handler.postDelayed(() -> {
            txtProdutos.setVisibility(View.VISIBLE);
            carregarProdutosMockados();
        }, 2500);

        handler.postDelayed(() -> cardAviso.setVisibility(View.VISIBLE), 6200);

        handler.postDelayed(() -> {
            progress.setVisibility(View.GONE);
            txtStatus.setText("Análise concluída");

            txtEtapa.setText("Produtos identificados. Toque em 'Revisar e Enviar'.");

            btnConfirmar.setText("Revisar e Enviar");
            btnConfirmar.setVisibility(View.VISIBLE);

            btnConfirmar.setOnClickListener(v -> {
                Intent intent = new Intent(this, ManualReceiptActivity.class);
                intent.putExtra("produtos_escaneados", listaProdutos);
                intent.putExtra("mercado_escaneado", "Angeloni");

                startActivity(intent);
                finish();
            });

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

        adicionarComDelay(p1, 0, "Arroz Branco");
        adicionarComDelay(p2, 800, "Feijão Preto");
        adicionarComDelay(p3, 1600, "Leite Integral");
    }

    private void adicionarComDelay(Produto produto, int delay, String nome) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            txtEtapa.setText("Produto encontrado: " + nome);
            listaProdutos.add(produto);
            adapter.notifyItemInserted(listaProdutos.size() - 1);
        }, delay);
    }
}