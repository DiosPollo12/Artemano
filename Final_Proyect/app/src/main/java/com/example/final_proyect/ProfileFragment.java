package com.example.final_proyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView usernameText, emailText, purchaseStats, favoriteStats, reviewStats;
    private Button editProfileButton, logoutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Referencias
        profileImage = view.findViewById(R.id.profile_image);
        usernameText = view.findViewById(R.id.username_text);
        emailText = view.findViewById(R.id.email_text);
        purchaseStats = view.findViewById(R.id.purchase_stats);
        favoriteStats = view.findViewById(R.id.favorite_stats);
        reviewStats = view.findViewById(R.id.review_stats);
        editProfileButton = view.findViewById(R.id.edit_profile_button);
        logoutButton = view.findViewById(R.id.logout_button); // ← ESTA FALTABA

        // Obtener datos del SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        String userName = prefs.getString("user_name", "Usuario");
        String userEmail = prefs.getString("user_email", "email@ejemplo.com");

        // Mostrar datos en el perfil
        usernameText.setText(userName);
        emailText.setText(userEmail);
        purchaseStats.setText("Compras: 12");
        favoriteStats.setText("Favoritos: 8");
        reviewStats.setText("Reseñas: 5");

        // Listener Editar Perfil
        editProfileButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Editar perfil", Toast.LENGTH_SHORT).show();
            // Aquí agregas la lógica para editar perfil si quieres
        });

        // Listener Cerrar Sesión
        logoutButton.setOnClickListener(v -> {
            cerrarSesion();
        });

        return view;
    }

    // Método para cerrar sesión
    private void cerrarSesion() {
        // Elimina los datos de sesión guardados
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_session", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Vuelve a la pantalla de inicio

        // Cierra la actividad actual para evitar volver atrás
        requireActivity().finish();

        Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
