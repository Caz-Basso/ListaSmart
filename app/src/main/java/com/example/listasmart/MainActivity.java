package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.getWritableDatabase();

        MaterialButton btnLista = findViewById(R.id.btnLista);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Product> produtos = new ArrayList<>();

        produtos.add(
                new Product(
                        "Arroz",
                        "Tio João",
                        "Alimentos",
                        1
                )
        );

        produtos.add(
                new Product(
                        "Leite",
                        "Parmalat",
                        "Bebidas",
                        3
                )
        );

        produtos.add(
                new Product(
                        "Sabonete",
                        "Dove",
                        "Higiene",
                        2
                )
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ProductListAdapter adapter = new ProductListAdapter(produtos);
        recyclerView.setAdapter(adapter);
    }
}