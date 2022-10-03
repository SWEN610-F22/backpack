package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface ProductDAO {
    Product[] getProducts() throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * @param product {@linkplain Product product} object to be created and saved.
     * The id of the product object is ignored and a new unique id is assigned.
     * @return new {@link Product product} if successful, false otherwise.
     * @throws IOException if there is an issue with underlying storage.
     */
    Product createProduct(Product product) throws IOException;
}

