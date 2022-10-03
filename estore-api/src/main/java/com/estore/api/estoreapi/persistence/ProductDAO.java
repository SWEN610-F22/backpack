package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for {@linkplain Product product} object persistence
 */
public interface ProductDAO {
    /**
     * Retrieves all {@linkplain Product products}
     * @return An array of {@link Product products} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all {@linkplain Product products} whose name contains the given text
     * @param containsText The text to match against
     * @return An array of {@link Product products} whose names contain the given text, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String containsText) throws IOException;
}
