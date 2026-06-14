package com.example.listasmart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.listasmart.database.dao.ItemListaDAO;
import com.example.listasmart.database.dao.ListaCompraDAO;
import com.example.listasmart.database.dao.ProdutoDAO;
import com.example.listasmart.database.model.Produto;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ProductPageActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtMarca;
    private TextView txtCategoria;
    private TextView txtCodigo;

    private long idProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.productPage),
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

        txtNome = findViewById(R.id.txtNome);
        txtMarca = findViewById(R.id.txtMarca);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtCodigo = findViewById(R.id.txtCodigo);

        idProduto = getIntent().getLongExtra("idProduto", -1);

        Produto produto = null;

        if (idProduto != -1) {

            ProdutoDAO produtoDAO = new ProdutoDAO(this);
            produto = produtoDAO.buscarPorId(idProduto);

            if (produto != null) {

                txtNome.setText(produto.getNome());
                txtMarca.setText(produto.getMarca());
                ImageView imgProduto = findViewById(R.id.imgProduto);

                String nomeImagem = produto.getImagemUrl();

                if (nomeImagem != null && !nomeImagem.isEmpty()) {
                    int imageRes = getResources().getIdentifier(
                            nomeImagem,
                            "drawable",
                            getPackageName()
                    );

                    if (imageRes != 0) {
                        imgProduto.setImageResource(imageRes);
                    }
                }

                txtCategoria.setText(
                        produto.getCategoria() != null
                                ? produto.getCategoria()
                                : "Sem categoria"
                );
                txtCodigo.setText(
                        produto.getCodigoBarras() != null
                                ? produto.getCodigoBarras()
                                : "Não cadastrado"
                );
            }
        }

        ImageButton btnFavorito = findViewById(R.id.btnFavorito);

        final boolean[] favorito = {false};

        btnFavorito.setOnClickListener(v -> {

            favorito[0] = !favorito[0];

            btnFavorito.setImageResource(
                    favorito[0]
                            ? R.drawable.baseline_favorite_24
                            : R.drawable.baseline_favorite_border_24
            );
        });

        CardView cardTabela = findViewById(R.id.cardTabelaNutricional);

        cardTabela.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Tabela Nutricional")
                    .setMessage("Não há tabela nutricional cadastrada para este produto.")
                    .setPositiveButton("Fechar", null)
                    .show();
        });

        CardView cardAnalise = findViewById(R.id.cardAnalisePrecos);

        cardAnalise.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Análise de Preços")
                    .setMessage("Nenhuma comparação disponível para este produto no momento.")
                    .setPositiveButton("Fechar", null)
                    .show();
        });

        ImageButton backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        MaterialButton btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> abrirBottomSheet());
    }

    private void abrirBottomSheet() {

        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);

        View view = getLayoutInflater().inflate(
                R.layout.bottomsheet_add_product,
                null
        );

        bottomSheet.setContentView(view);

        MaterialCardView opcaoRapida =
                view.findViewById(R.id.opcaoListaRapida);

        MaterialCardView opcaoPersonalizada =
                view.findViewById(R.id.opcaoListaPersonalizada);

        opcaoRapida.setOnClickListener(v -> {

            adicionarProdutoNaLista();

            bottomSheet.dismiss();
        });

        opcaoPersonalizada.setOnClickListener(v -> {

            Toast.makeText(
                    this,
                    "Disponível em breve",
                    Toast.LENGTH_SHORT
            ).show();
        });

        bottomSheet.show();
    }

    private void adicionarProdutoNaLista() {

        ListaCompraDAO listaDAO = new ListaCompraDAO(this);
        ItemListaDAO itemListaDAO = new ItemListaDAO(this);

        Long idLista = listaDAO.buscarListaUsuario(1L);

        if (idLista == null) {
            idLista = listaDAO.criarListaPadrao(1L);
        }

        itemListaDAO.adicionarItem(idLista, idProduto);

        new AlertDialog.Builder(this)
                .setTitle("Sucesso")
                .setMessage("Produto adicionado à lista!")
                .setPositiveButton("OK", null)
                .show();
    }
}