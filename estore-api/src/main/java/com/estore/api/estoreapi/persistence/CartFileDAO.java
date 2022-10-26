package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CartFileDAO implements CartDAO {
    Map<Integer, CartItem> cart;
    private ObjectMapper objectMapper;
    private static int nextId;
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
        cart = new TreeMap<>();
        nextId = 0;
        CartItem[] productsArray = objectMapper.readValue(new File(filename), CartItem[].class);
        for (CartItem product : productsArray) {
            cart.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        ++nextId;
        return true;
    }


    
    /** 
     * Loads the list of products in the cart.
     * @return An array of {@link Product product} objects, may be empty
     */
    private CartItem[] getProductsArray(){
        ArrayList<CartItem> productsList = new ArrayList<>();

        for (CartItem product : cart.values()) {
            productsList.add(product);

        }

        CartItem[] productArray = new CartItem[productsList.size()];
        productsList.toArray(productArray);
        return productArray;
    }

    @Override
    public CartItem[] getCart() {
        synchronized(cart){
            return getProductsArray();
        }
    }

    @Override
    public boolean isCartEmpty(){
        if(cart.size()==0)return true;
        else
            return false;
    }
    

    @Override
    public CartItem updateInCart(CartItem product) throws IOException {
        synchronized(cart) {
            if (cart.containsKey(product.getId()) == false)
                return null;
            if(product.getQuantity()==0){
                deleteFromCart(product.getId());
                return product;
            }
            else{
                cart.put(product.getId(), product);
                save();
                return product;
            }
        }
    }

    @Override
    public CartItem[] decrease(int productId, int userId) throws IOException {
        synchronized(cart) {
            int cartId = 0;
            if (cart.containsKey(productId) == false)
                return null;
            for (CartItem product : cart.values()) {
                if(product.getUserId()==userId && product.getProductId()==productId){
                    product.setQuantity(product.getQuantity()-1);
                    cart.put(product.getId(), product);
                    save();
                    if(product.getQuantity()==0){
                        cartId = product.getId();
                        deleteFromCart(product.getId());
                        save();
                    return getCart();
                }
            }
            
            }
            if (cart.containsKey(cartId) == false){
                CartItem product = getProduct(cartId);
                cart.put(product.getId(), product);
                save();
                return getCart();
            }
            else{
                return null;
            }
        }
    }

    @Override
    public CartItem[] increase(int productId, int userId) throws IOException {
        synchronized(cart) {
            if (cart.containsKey(productId) == false)
                return null;
            for (CartItem product : cart.values()) {
                if(product.getUserId()==userId && product.getProductId()==productId){
                    product.setQuantity(product.getQuantity()+1);
                    cart.put(product.getId(), product);
                    save();
                    return getCart();
                }
            }
        }
        return null;
    }
    

    
    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * @return true if the {@link Product products} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        CartItem[] productArray = getProductsArray();
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    
    @Override
    public CartItem getProduct(int id){
        synchronized(cart) {
            return cart.getOrDefault(id, null);
        }
    }

    @Override
    public CartItem addToCart(CartItem product) throws IOException {
        synchronized(cart) {
            CartItem newProduct = new CartItem(nextId(), product.getUserId(), product.getProductId(), product.getQuantity());
            cart.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    @Override
    public boolean deleteFromCart(int id) throws IOException {
        synchronized(cart) {
            if (cart.containsKey(id)) {
                cart.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
