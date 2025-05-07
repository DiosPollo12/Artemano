package com.example.final_proyect;

public class Product {
    private String name;
    private String price;
    private int imageResId;
    private float rating;
    private boolean isFavorite; // Campo nuevo

    public Product(String name, String price, int imageResId, float rating) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.rating = rating;
        this.isFavorite = false;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getRating() {
        return rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

