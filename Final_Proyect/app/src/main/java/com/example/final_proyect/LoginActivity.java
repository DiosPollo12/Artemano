package com.example.final_proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecer el layout antes de acceder a sus vistas
        setContentView(R.layout.activity_login);  // Asegúrate de que este layout existe

        // Configurar insets para ajustar las barras del sistema
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Inicializar el ImageView para el fondo
        ImageView backgroundImage = findViewById(R.id.backgroundImage);
        if (backgroundImage != null) {
            backgroundImage.setImageResource(R.drawable.newfondo);
            backgroundImage.setAlpha(0.5f);
        }

        // Configurar el botón para iniciar la siguiente actividad
        Button btnIr = findViewById(R.id.btn_login);
        if (btnIr != null) {
            btnIr.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, PaginaInicioActivity.class);
                startActivity(intent);
            });
        }
    }
}
