package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CartFileDAO implements CartDAO {
    Map<Integer, Product> cart;
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
        cart = new TreeMap<>();
        Product[] productsArray = objectMapper.readValue(new File(filename), Product[].class);
        for (Product product : productsArray) {
            cart.put(product.getId(), product);
        }
        return true;
    }


    
    /** 
     * Loads the list of products in the cart.
     * @return An array of {@link Product product} objects, may be empty
     */
    private Product[] getProductsArray(){
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : cart.values()) {
            productsList.add(product);

        }

        Product[] productArray = new Product[productsList.size()];
        productsList.toArray(productArray);
        return productArray;
    }

    @Override
    public Product[] getCart() {
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
    public Product updateInCart(Product product) throws IOException {
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

    
    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * @return true if the {@link Product products} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    
    @Override
    public Product getProduct(int id) throws IOException {
        synchronized(cart) {
            return cart.getOrDefault(id, null);
        }
    }

    @Override
    public Product addToCart(Product product) throws IOException {
        synchronized(cart) {
            Product newProduct = new Product(product.getId(),product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
            if(cart.containsKey(newProduct.getId())){
                int oldQuantity = getProduct(newProduct.getId()).getQuantity();
                int newQuantity = newProduct.getQuantity() + oldQuantity;
                newProduct.setQuantity(newQuantity);
            }
            cart.put(newProduct.getId(),newProduct);
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

    @Override
    public boolean clearCart() throws IOException{
        cart.clear();
        return save();
    }


}
