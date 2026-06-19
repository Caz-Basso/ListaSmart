package com.example.listasmart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class RegisterReceiptActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialButton btnManual;
    private MaterialButton btnQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_receipt);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.registerReceipt),
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

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnManual = findViewById(R.id.btnManual);
        btnQrCode = findViewById(R.id.btnQrCode);
    }

    private void setupListeners() {

        btnBack.setOnClickListener(v -> finish());

        btnManual.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegisterReceiptActivity.this,
                    ManualReceiptActivity.class
            );

            startActivity(intent);

        });

        btnQrCode.setOnClickListener(v -> {

            Intent intent = new Intent(
                    RegisterReceiptActivity.this,
                    ReceiptDemoActivity.class
            );

            startActivity(intent);

        });
    }
}