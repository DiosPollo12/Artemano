package com.example.final_proyect;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.recyclerview.widget.RecyclerView;

public class PaginaInicioActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchView = findViewById(R.id.search_view);

        // Configuración del Bottom Navigation y fragmentos
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment;

            if (item.getItemId() == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                fragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.nav_shop) {
                fragment = new ShopCarFragment();
            } else if (item.getItemId() == R.id.nav_menu) {
                fragment = new MenuFragment();
            } else {
                fragment = null;
            }

            return loadFragment(fragment);
        });

        // Configuración de la barra de búsqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Aquí podrías ejecutar la búsqueda
                filterProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aquí puedes mostrar resultados en tiempo real
                filterProducts(newText);
                return false;
            }
        });
    }

    // Método para filtrar los productos
    private void filterProducts(String query) {
        // Obtén el fragmento de Home o donde quieras mostrar los productos
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (homeFragment != null) {
            homeFragment.filterProducts(query);  // Llama a un método en el fragmento de Home
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
