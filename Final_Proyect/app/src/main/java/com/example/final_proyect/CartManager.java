package com.example.final_proyect;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartList;

    private CartManager() {
        cartList = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        cartList.add(product);
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public void clearCart() {
        cartList.clear();
    }
}
