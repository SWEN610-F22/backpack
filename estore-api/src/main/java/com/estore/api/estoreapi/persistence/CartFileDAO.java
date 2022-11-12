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
                deleteFromCart(product.getId());    // If the new quantity of the product is zero, delete
                return product;                     // the product from the cart
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
            for (CartItem product : cart.values()) {
                if(product.getUserId()==userId && product.getProductId()==productId){ // If this product exists in the cart
                    product.setQuantity(product.getQuantity()-1);                     // for the current user,
                    cart.put(product.getId(), product);                               // Decrement the quantity of the product
                    save();                                                           // and update the cart
                    if(product.getQuantity()==0){                                     // If new quantity is zero,
                        cartId = product.getId();                                     // remove product from cart.
                        deleteFromCart(product.getId());
                        save();
                    return getCart();
                    }
                }
            }
            return null;
        }
    }

    @Override
    public CartItem[] increase(int productId, int userId) throws IOException {
        synchronized(cart) {
            for (CartItem product : cart.values()) {
                if(product.getUserId()==userId && product.getProductId()==productId){ // If this product exists in the cart
                    product.setQuantity(product.getQuantity()+1);                     // for the current user,
                    cart.put(product.getId(), product);                               // Increment the quantity of the product
                    save();                                                           // and update the cart
                    return getCart();
                }
            }
        }
        return null;
    }


    @Override
    public CartItem[] clearItem(int productId, int userId) throws IOException {
        synchronized(cart) {
            for (CartItem product : cart.values()) {
                if(product.getUserId()==userId && product.getProductId()==productId){ // If the product exists in the cart
                    cart.remove(product.getId());                                     // for the current user,
                    save();                                                           // Remove the product from the cart
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
    public CartItem addToCart(CartItem product, Integer userId) throws IOException {
        synchronized(cart) {
            for (CartItem cartItem : cart.values()) {
                if(cartItem.getUserId()==userId && product.getProductId()==cartItem.getProductId()){ // If product already exists in
                    cartItem.setQuantity(cartItem.getQuantity()+product.getQuantity());        // this user's cart,
                    cart.put(cartItem.getId(), cartItem);                                      // set new quantity to total of
                    save();                                                                   // old quantity and new quantity
                    return cartItem;
                }
            }
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

    @Override
    public Integer[] getIdsForClearing(int userId) throws IOException{
        ArrayList<Integer> cartIdsForUser = new ArrayList<>();
        for (CartItem cartItem : cart.values()){
            if(cartItem.getUserId()==userId){
                cartIdsForUser.add(cartItem.getProductId());
            }
        }
        Integer[] cartArray = new Integer[cartIdsForUser.size()];
        cartIdsForUser.toArray(cartArray);
        return cartArray;
    }

    @Override
    public int getQuantity(int userId, int productId) throws IOException{
        for (CartItem cartItem : cart.values()){
            if(cartItem.getUserId()==userId && cartItem.getProductId()==productId){
                return cartItem.getQuantity();
            }
        }
        return 0;
    }

    public boolean clearCart(int userId) throws IOException{
        ArrayList<Integer> cartIdsForUser = new ArrayList<>();
        for (CartItem cartItem : cart.values()){
            if(cartItem.getUserId()==userId){
                cartIdsForUser.add(cartItem.getId());
            }
        }
        for (Integer id : cartIdsForUser){
            boolean isSuccessful = deleteFromCart(id);
            if(!isSuccessful)return false;
            save();
        }
        return true;
    }
}
