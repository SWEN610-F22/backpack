package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCart {
    @JsonProperty("user") User user;
    @JsonProperty("products") ArrayList<Product> products;

    public UserCart() {
        this.user = null;
        this.products = new ArrayList<>();
    }


    public ArrayList<Product> getProducts() {
        return products;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        products.add(product);
    }


    @Override
    public String toString() {
        return "UserCart [user=" + user + ", products=" + products + "]";
    }
    

    
}
