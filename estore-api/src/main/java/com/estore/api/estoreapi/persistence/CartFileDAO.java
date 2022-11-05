package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.UserCart;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CartFileDAO implements CartDAO {
    Map<Integer, ArrayList<CartItem>> cart;
    private ObjectMapper objectMapper;
    private String filename;

    public CartFileDAO(@Value("${cart.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    
    /** 
     * Reads the storage file for this user's cart, and loads it.
     * @return true if successful, false if not.
     * @throws IOException if underlying storage cannot be accessed
     */
    private boolean load() throws IOException {
        cart = new HashMap<>();
        CartItem[] cartArray = objectMapper.readValue(new File(filename), CartItem[].class);
        for (CartItem item : cartArray) {
            if(!cart.containsKey(item.getUserId())){
                cart.put(item.getUserId(), new ArrayList<>());
            }
            cart.get(item.getUserId()).add(item);
        }
        return true;
    }


    @Override
    public CartItem[] getCart() throws IOException {
       return cart.values().toArray(new CartItem[0]);
    }

    @Override
    public CartItem[] getCartForUser(int userId) throws IOException {
        return cart.get(userId).toArray(new CartItem[0]);
    }


    @Override
    public CartItem getProduct(int id) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean isCartEmpty() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public CartItem updateInCart(CartItem cartItem) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public CartItem addToCart(CartItem cartItem, Integer userId) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public boolean deleteFromCart(int id) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public CartItem[] increase(int productId, int userId) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public CartItem[] decrease(int productId, int userId) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public CartItem[] clearItem(int productId, int userId) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }


}
