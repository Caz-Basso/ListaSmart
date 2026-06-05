package com.example.listasmart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.RecyclerView.MyListAdapter;
import com.example.listasmart.RecyclerView.ProductListAdapter;
import com.example.listasmart.model.Product;

import java.util.ArrayList;
public class MyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mylist);

        ImageButton deleteBtn = findViewById(R.id.deleteBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.myList), (v, insets) -> {
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
                        2
                )
        );

        produtos.add(
                new Product(
                        "Sabonete",
                        "Dove",
                        "Higiene",
                        3
                )
        );

        RecyclerView recyclerView = findViewById(R.id.myListRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );
        MyListAdapter adapter = new MyListAdapter(produtos);
        recyclerView.setAdapter(adapter);
    }

}
