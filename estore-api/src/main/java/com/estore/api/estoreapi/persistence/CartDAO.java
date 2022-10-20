package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for Cart persistence
 */
public interface CartDAO {
    /**
     * Retrieves all {@linkplain Product products}
     * @return An array of {@link Product products} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Product[] getCart() throws IOException;
    /**
     * Returns the product with the specific id
     * @param id The id of the {@link Product product} to get
     * @return Product with the specific id
     */
    public Product getProduct(int id) throws IOException;
    /**
     * Checks if cart is empty
     * @return true if cart is empty, false otherwise
     */
    boolean isCartEmpty();
    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     * {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateInCart(Product product) throws IOException;
    /**
     * Adds and saves a {@linkplain Product product}
     * @param product {@linkplain Product product} object to be added to the cart and saved.
     * If the id of the product is zero, product is deleted from cart.
     * @return new {@link Product product} if successful, null otherwise.
     * @throws IOException if there is an issue with underlying storage.
     */
    Product addToCart(Product product) throws IOException;
    /**
     * Deletes a {@linkplain Product product} with the given id
     * @param id The id of the {@link Product product}
     * @return true if the {@link Product product} was deleted
     * false if product with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteFromCart(int id) throws IOException;
    /**
     * Deletes all {@linkplain Product products} in cart
     * return true if successful, false otherwise
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean clearCart() throws IOException;
}

