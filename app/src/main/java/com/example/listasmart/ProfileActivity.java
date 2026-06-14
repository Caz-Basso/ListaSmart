package com.example.listasmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton backBtn = findViewById(R.id.backBtn);
        Button logoutBtn = findViewById(R.id.logoutBtn);

        backBtn.setOnClickListener(v -> finish());

        logoutBtn.setOnClickListener(v -> {

            SharedPreferences prefs =
                    getSharedPreferences("listasmart", MODE_PRIVATE);

            prefs.edit()
                    .clear()
                    .apply();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }
}
