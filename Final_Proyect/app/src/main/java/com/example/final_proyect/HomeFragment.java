package com.example.final_proyect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private SearchView searchView;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Obtener SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Crear la lista de productos y restaurar favoritos
        productList = new ArrayList<>();
        productList.add(createProduct("Cesta de Mimbre", "$25.00", R.drawable.cesta_de_mimbre, 4.5f, prefs));
        productList.add(createProduct("Jarrón de Cerámica", "$45.00", R.drawable.jarron_ceramina, 4.7f, prefs));
        productList.add(createProduct("Manta Tejida a Mano", "$60.00", R.drawable.manta_tejida_a_mano, 4.3f, prefs));
        productList.add(createProduct("Figuras de Madera Talladas", "$35.00", R.drawable.escultura_de_madera, 4.6f, prefs));
        productList.add(createProduct("Collar de Perlas", "$20.00", R.drawable.collar_perlas, 4.4f, prefs));
        productList.add(createProduct("Bolso de Cuero", "$80.00", R.drawable.bolso_de_cuero, 4.8f, prefs));
        productList.add(createProduct("Escultura de Hierro", "$100.00", R.drawable.escultura_de_hierro, 4.7f, prefs));
        productList.add(createProduct("Sombrero de Paja Hecho a Mano", "$15.00", R.drawable.sombrero_de_lana, 4.2f, prefs));
        productList.add(createProduct("Alfombra de Lana", "$120.00", R.drawable.alfombra_de_lana, 4.5f, prefs));
        productList.add(createProduct("Juguete de Madera", "$10.00", R.drawable.juguete_madera, 4.3f, prefs));

        adapter = new ProductAdapter(productList, getActivity(), prefs);
        recyclerView.setAdapter(adapter);

        searchView = getActivity().findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                filterProducts(newText.toLowerCase());
                return false;
            }
        });

        return view;
    }

    // Método para filtrar los productos según la búsqueda
    void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query)) {
                filteredList.add(product);
            }
        }
        adapter.updateList(filteredList);
    }

    // Método auxiliar para crear un producto con su estado de favorito desde SharedPreferences
    private Product createProduct(String name, String price, int imageResId, float rating, SharedPreferences prefs) {
        Product product = new Product(name, price, imageResId, rating);
        product.setFavorite(prefs.getBoolean("fav_" + name, false));
        return product;
    }

    // Adaptador para el RecyclerView que muestra los productos
    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private List<Product> productList;
        private Context context;
        private SharedPreferences prefs;

        public ProductAdapter(List<Product> productList, Context context, SharedPreferences prefs) {
            this.productList = productList;
            this.context = context;
            this.prefs = prefs;
        }

        public void updateList(List<Product> newProductList) {
            this.productList = newProductList;
            notifyDataSetChanged();
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            Product product = productList.get(position);

            holder.name.setText(product.getName());
            holder.price.setText(product.getPrice());
            holder.image.setImageResource(product.getImageResId());

            // Cargar estado de favorito
            boolean isFavorite = product.isFavorite();
            holder.favoriteButton.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_border_favorite);

            holder.buyButton.setOnClickListener(v -> {
                CartManager.getInstance().addToCart(product);
                Toast.makeText(context, product.getName() + " añadido al carrito", Toast.LENGTH_SHORT).show();
            });

            holder.favoriteButton.setOnClickListener(v -> {
                boolean nuevoEstado = !product.isFavorite();
                product.setFavorite(nuevoEstado);
                holder.favoriteButton.setImageResource(nuevoEstado ? R.drawable.ic_favorite : R.drawable.ic_border_favorite);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("fav_" + product.getName(), nuevoEstado);
                editor.apply();
                Toast.makeText(context,
                        product.getName() + (nuevoEstado ? " añadido a favoritos" : " eliminado de favoritos"),
                        Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name, price;
            Button buyButton;
            ImageView favoriteButton;

            public ProductViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.product_image);
                name = itemView.findViewById(R.id.product_name);
                price = itemView.findViewById(R.id.product_price);
                buyButton = itemView.findViewById(R.id.buy_button);
                favoriteButton = itemView.findViewById(R.id.favorite_button);
            }
        }
    }
}

