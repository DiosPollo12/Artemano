package com.example.final_proyect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopCarFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;

    public ShopCarFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_car, container, false);

        // Configura el RecyclerView
        recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa el adaptador con una lista vacía o con los productos actuales
        adapter = new CartAdapter(CartManager.getInstance().getCartList(), getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresca el RecyclerView cuando el fragmento vuelve a primer plano
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}

