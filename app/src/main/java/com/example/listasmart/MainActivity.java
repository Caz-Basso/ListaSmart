package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProductListAdapter;
import com.example.listasmart.database.DBOpenHelper;
import com.example.listasmart.model.Product;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private ArrayList<Product> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBOpenHelper dbHelper = new DBOpenHelper(this);

        ImageButton myListBtn = findViewById(R.id.mylistBtn);
        MaterialButton btnSaibaMais = findViewById(R.id.btnSaibaMais);
        TextView cartCount = findViewById(R.id.cartCount);

        myListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyListActivity.class);
            startActivity(intent);
        });

        btnSaibaMais.setOnClickListener(v ->
                Toast.makeText(
                        MainActivity.this,
                        "O Lista Smart é um projeto comunitário criado para ajudar consumidores a economizar nas compras do dia a dia.",
                        Toast.LENGTH_LONG
                ).show()
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        produtos = new ArrayList<>();

        produtos.add(new Product(
                "Arroz",
                "Tio João",
                "Alimentos",
                1
        ));

        produtos.add(new Product(
                "Leite",
                "Parmalat",
                "Bebidas",
                3
        ));

        produtos.add(new Product(
                "Sabonete",
                "Dove",
                "Higiene",
                2
        ));

        cartCount.setText(String.valueOf(produtos.size()));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductListAdapter(produtos);
        recyclerView.setAdapter(adapter);
    }
}