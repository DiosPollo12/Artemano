package com.example.final_proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("user_session", MODE_PRIVATE);

        boolean hasVisited = preferences.getBoolean("hasVisited", false);
        String savedEmail = preferences.getString("email", null);

        if (hasVisited) {
            // Si ya visitó antes, validar si tiene sesión recordada
            if (savedEmail != null) {
                startActivity(new Intent(MainActivity.this, PaginaInicioActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            finish();
        } else {
            // Primera vez → mostrar MainActivity y guardar preferencia
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("hasVisited", true);
            editor.apply();

            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            // Configurar insets
            if (findViewById(R.id.main) != null) {
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });
            }

            // Imagen de fondo
            ImageView backgroundImage = findViewById(R.id.backgroundImage);
            if (backgroundImage != null) {
                backgroundImage.setImageResource(R.drawable.newfondo);
                backgroundImage.setAlpha(0.5f);
            }

            // Botón Login
            Button btnIr = findViewById(R.id.btn_ir);
            btnIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            // Botón Registrar
            btnIr = findViewById(R.id.btn_ir2);
            btnIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
