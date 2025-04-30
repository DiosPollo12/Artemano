package com.example.final_proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartList;
    private Context context;

    public CartAdapter(List<Product> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;
        RatingBar rating;

        public CartViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            rating = itemView.findViewById(R.id.product_rating);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartList.get(position);
        holder.image.setImageResource(product.getImageResId());
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.rating.setRating(product.getRating());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}

