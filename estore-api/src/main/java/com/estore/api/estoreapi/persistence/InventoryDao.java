package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Product;

import java.io.IOException;

public interface InventoryDao {
    /**
     * Retrieves all {@linkplain Product products}
     * @return An array of {@link Product products} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Product[] getInventory() throws IOException;
    /**
     * Returns the product with the specific id
     * @param id The id of the {@link Product product} to get
     * @return Product with the specific id
     */
    public Product getProduct(int id) throws IOException;

    /**
     * checks if the inventory is empty
     * @return true if inventory is empty, false otherwise
     */

    boolean isInventoryEmpty() throws IOException;

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
    Product updateInventory(Product product) throws IOException;

    /**
     * Adds and saves a product to the inventory
     * @param product {@linkplain Product product} product to be added and saved to the inventory
     * @return product if it was added and saved to the inventory successfully, null otherwise
     * @throws IOException if there's an issue with the underlying storage mechanism
     */
    Product addToInventory(Product product) throws IOException;

    /**
     * Deletes the product with the provided id from the inventory
     * @param id if of the product to be deleted from the inventory
     * @return true if the product is deleted successfully, false otherwise
     * @throws IOException if there's an issue with accessing and deleting the product
     */
    boolean deleteFromInventory(int id) throws IOException;
}
