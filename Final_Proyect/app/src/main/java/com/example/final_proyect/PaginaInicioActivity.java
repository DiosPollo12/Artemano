package com.example.final_proyect;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class PaginaInicioActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Fragmento por defecto
        loadFragment(new HomeFragment());

        // Navegación con if-else en lugar de switch
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
