package com.example.final_proyect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<MenuOption> menuList;

    public MenuFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = view.findViewById(R.id.menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        menuList = new ArrayList<>();
        menuList.add(new MenuOption("Inicio", R.drawable.ic_home));
        menuList.add(new MenuOption("Carrito", R.drawable.ic_shop));
        menuList.add(new MenuOption("Cuenta", R.drawable.ic_profile));
        menuList.add(new MenuOption("Cambiar Contraseña", R.drawable.ic_password));
        menuList.add(new MenuOption("Notificaciones", R.drawable.ic_notifications));
        menuList.add(new MenuOption("Privacidad", R.drawable.ic_privacy));
        menuList.add(new MenuOption("Cerrar Sesión", R.drawable.ic_logout));

        adapter = new MenuAdapter(menuList, getContext(), option -> {
            String title = option.getTitle();

            if (title.equals("Inicio")) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            } else if (title.equals("Carrito")) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ShopCarFragment())
                        .commit();
            } else if (title.equals("Cuenta")) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .commit();
            } else if (title.equals("Cerrar Sesión")) {
                cerrarSesion();  // 👈 Aquí llamas la función
            } else {
                Toast.makeText(getContext(), title + " seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    // 👇 Aquí agregas tu función
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
