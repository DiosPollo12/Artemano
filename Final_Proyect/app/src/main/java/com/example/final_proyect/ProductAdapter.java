package com.example.final_proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productImage.setImageResource(product.getImageResId());

        // Cargar estado desde SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFav = prefs.getBoolean("fav_" + product.getName(), false);
        product.setFavorite(isFav);

        // Mostrar el ícono de favorito
        if (isFav) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_border_favorite);
        }

        // Guardar al hacer clic
        holder.favoriteButton.setOnClickListener(v -> {
            boolean nuevoEstado = !product.isFavorite();
            product.setFavorite(nuevoEstado);

            // Guardar en SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("fav_" + product.getName(), nuevoEstado);
            editor.apply();

            if (nuevoEstado) {
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

    // Método para actualizar la lista filtrada
    public void updateList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage, favoriteButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}
