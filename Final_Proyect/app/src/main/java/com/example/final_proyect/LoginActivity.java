package com.example.final_proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button btnLogin;
    CheckBox rememberCheckBox;
    ImageView eyeIcon;

    boolean isPasswordVisible = false;
    DatabaseHelper dbHelper;

    SharedPreferences preferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        dbHelper = new DatabaseHelper(this);

        preferences = getSharedPreferences("user_session", MODE_PRIVATE);

        // Vistas
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
        btnLogin = findViewById(R.id.btn_login);
        eyeIcon = findViewById(R.id.eyeIcon);

        // Restaurar sesión si se marcó previamente
        String savedEmail = preferences.getString("email", null);
        if (savedEmail != null) {
            Intent intent = new Intent(LoginActivity.this, PaginaInicioActivity.class);
            startActivity(intent);
            finish();
        }

        // Mostrar/ocultar contraseña
        eyeIcon.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                eyeIcon.setImageResource(R.drawable.ic_eye_open); // ícono de ojo abierto
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyeIcon.setImageResource(R.drawable.ic_eye_closed); // ícono de ojo cerrado
            }
            passwordEditText.setSelection(passwordEditText.length()); // Mantener cursor al final
        });

        // Botón de login
        btnLogin.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Ingresa tus datos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean userValid = dbHelper.verificarUsuario(email, password);
            if (userValid) {
                if (rememberCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", email);
                    editor.apply();
                }

                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, PaginaInicioActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
