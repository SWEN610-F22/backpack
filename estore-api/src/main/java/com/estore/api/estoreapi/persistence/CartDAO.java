package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.CartItem;

/**
 * Defines the interface for Cart persistence
 */
public interface CartDAO {
    /**
     * Retrieves all {@linkplain CartItem objects}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    CartItem[] getCart() throws IOException;
    /**
     * Returns the CartItem with the specific id
     * @param id The id of the {@link CartItem CartItem} to get
     * @return CartItem with the specific id
     */
    public CartItem getProduct(int id);
    /**
     * Checks if cart is empty
     * @return true if cart is empty, false otherwise
     */
    boolean isCartEmpty();
    /**
     * Updates and saves a {@linkplain CartItem cartItem}
     * @param {@link CartItem cartItem} object to be updated and saved
     * If new quantity of cartItem is zero, deletes cartItem from cart
     * @return updated {@link CartItem cartItem} if successful, null if
     * {@link CartItem cartItem} could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    CartItem updateInCart(CartItem cartItem) throws IOException;
    /**
     * Adds and saves a {@linkplain CartItem cartItem}
     * @param cartItem {@linkplain CartItem cartItem} object to be added to the cart and saved.
     * @param userId {@linkplain Integer integer} is the id of the user of the cart.
     * @return new {@link CartItem cartItem} if successful, null otherwise.
     * @throws IOException if there is an issue with underlying storage.
     */
    CartItem addToCart(CartItem cartItem, Integer userId) throws IOException;
    /**
     * Deletes a {@linkplain CartItem cartItem} with the given id
     * @param id The id of the {@link CartItem cartItem}
     * @return true if the {@link CartItem cartItem} was deleted
     * false if product with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteFromCart(int id) throws IOException;
    /**
     * Increments the quantity of a {@linkplain CartItem cartItem} with the given id for current user
     * @param productId The id of the {@link CartItem cartItem} to increment
     * @param userId The id of the current {@link User user}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storaged
     */
    CartItem[] increase(int productId, int userId) throws IOException;
    /**
     * Decrements the quantity of a {@linkplain CartItem cartItem} with the given id for current user
     * @param productId The id of the {@link CartItem cartItem} to increment
     * @param userId The id of the current {@link User user}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storaged
     */
    CartItem[] decrease(int productId, int userId) throws IOException;
    /**
     * Sets the quantity of a {@linkplain CartItem cartItem} with the given id for current user to zero
     * @param productId The id of the {@link CartItem cartItem} to increment
     * @param userId The id of the current {@link User user}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storaged
     */
    CartItem[] clearItem(int productId, int userId) throws IOException;

    boolean clearCart(int userId) throws IOException;

    Integer[] getIdsForClearing(int userId) throws IOException;

    int getQuantity(int userId, int productId) throws IOException;
}

