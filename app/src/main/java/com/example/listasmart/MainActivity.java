package com.example.listasmart;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.ProductListAdapter;
import com.example.listasmart.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
                        "Alimentos"
                )
        );

        produtos.add(
                new Product(
                        "Leite",
                        "Parmalat",
                        "Bebidas"
                )
        );

        produtos.add(
                new Product(
                        "Sabonete",
                        "Dove",
                        "Higiene"
                )
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ProductListAdapter adapter = new ProductListAdapter(this, produtos);
        recyclerView.setAdapter(adapter);

    }
}