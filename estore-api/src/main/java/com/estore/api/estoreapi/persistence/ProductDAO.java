package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface ProductDAO {
    Product[] getProducts() throws IOException;
    Product updateProduct(Product product) throws IOException;
}
