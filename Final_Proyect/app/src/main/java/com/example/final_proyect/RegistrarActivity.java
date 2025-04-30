package com.example.final_proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText, nameEditText;
    Button btnRegistrar;
    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        // Inicializar vistas
        emailEditText = findViewById(R.id.registerEmailEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.registerConfirmPasswordEditText);
        nameEditText = findViewById(R.id.registerNameEditText);  // Campo de nombre
        btnRegistrar = findViewById(R.id.btn_register);

        // Inicializar DB
        dbHelper = new DatabaseHelper(this);

        // Fondo transparente
        View mainView = findViewById(R.id.register_main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        ImageView backgroundImage = findViewById(R.id.registerBackgroundImage);
        if (backgroundImage != null) {
            backgroundImage.setImageResource(R.drawable.newfondo);
            backgroundImage.setAlpha(0.5f);
        }

        // Acción del botón registrar
        btnRegistrar.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String userName = nameEditText.getText().toString().trim();  // Obtener el nombre

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userName.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                boolean registered = dbHelper.insertUser(email, password);
                if (registered) {
                    // Guardar el nombre del usuario en SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_name", userName);
                    editor.putString("user_email", email);
                    editor.apply();

                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, PaginaInicioActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
