package com.example.final_proyect;

import android.content.Context;
import android.os.Bundle;
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

    public HomeFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar el RecyclerView
        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columnas

        // Lista de productos
        productList = new ArrayList<>();
        productList.add(new Product("Cesta de Mimbre", "$25.00", R.drawable.cesta_de_mimbre, 4.5f));
        productList.add(new Product("Jarrón de Cerámica", "$45.00", R.drawable.jarron_ceramina, 4.7f));
        productList.add(new Product("Manta Tejida a Mano", "$60.00", R.drawable.manta_tejida_a_mano, 4.3f));
        productList.add(new Product("Figuras de Madera Talladas", "$35.00", R.drawable.escultura_de_madera, 4.6f));
        productList.add(new Product("Collar de Perlas", "$20.00", R.drawable.collar_perlas, 4.4f));
        productList.add(new Product("Bolso de Cuero", "$80.00", R.drawable.bolso_de_cuero, 4.8f));
        productList.add(new Product("Escultura de Hierro", "$100.00", R.drawable.escultura_de_hierro, 4.7f));
        productList.add(new Product("Sombrero de Paja Hecho a Mano", "$15.00", R.drawable.sombrero_de_lana, 4.2f));
        productList.add(new Product("Alfombra de Lana", "$120.00", R.drawable.alfombra_de_lana, 4.5f));
        productList.add(new Product("Juguete de Madera", "$10.00", R.drawable.juguete_madera, 4.3f));

        // Configurar el adaptador y asignarlo al RecyclerView
        adapter = new ProductAdapter(productList, getActivity()); // Usar getActivity() para el contexto
        recyclerView.setAdapter(adapter);

        // Obtener la referencia al SearchView desde la actividad principal
        searchView = getActivity().findViewById(R.id.search_view);

        // Configurar el listener del SearchView para filtrar productos
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // No realizar acción adicional al presionar "Buscar"
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.toLowerCase();
                filterProducts(query); // Llamar al método para filtrar los productos
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
        adapter.updateList(filteredList); // Actualizar la lista con los productos filtrados
    }

    // Adaptador para el RecyclerView que muestra los productos
    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        private List<Product> productList;
        private Context context;

        public ProductAdapter(List<Product> productList, Context context) {
            this.productList = productList;
            this.context = context;
        }

        // Método para actualizar la lista de productos
        public void updateList(List<Product> newProductList) {
            this.productList = newProductList;
            notifyDataSetChanged(); // Notificar al RecyclerView que los datos han cambiado
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

            // Mostrar ícono favorito inicial (por defecto no favorito)
            holder.isFavorite = false;
            holder.favoriteButton.setImageResource(R.drawable.ic_border_favorite);

            // Botón para agregar al carrito
            holder.buyButton.setOnClickListener(v -> {
                CartManager.getInstance().addToCart(product);
                Toast.makeText(context, product.getName() + " añadido al carrito", Toast.LENGTH_SHORT).show();
            });

            // Botón para agregar o quitar de favoritos
            holder.favoriteButton.setOnClickListener(v -> {
                holder.isFavorite = !holder.isFavorite;
                if (holder.isFavorite) {
                    holder.favoriteButton.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(context, product.getName() + " añadido a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    holder.favoriteButton.setImageResource(R.drawable.ic_border_favorite);
                    Toast.makeText(context, product.getName() + " eliminado de favoritos", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        // ViewHolder para cada producto
        public class ProductViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name, price;
            Button buyButton;
            ImageView favoriteButton;
            boolean isFavorite = false;

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
