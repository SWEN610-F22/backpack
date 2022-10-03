package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductFileDAO implements ProductDAO {
    Map<Integer, Product> products;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;
        Product[] productsArray = objectMapper.readValue(new File(filename), Product[].class);
        for (Product product : productsArray) {
            products.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        ++nextId;
        return true;
    }

    private Product[] getProductsArray(){
        return getProductsArray(null);
    }


    private Product[] getProductsArray(String containsText){
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().toLowerCase().contains(containsText.toLowerCase())) {
                productsList.add(product);
            }

        }

        Product[] productArray = new Product[productsList.size()];
        productsList.toArray(productArray);
        return productArray;
    }


    @Override
    public Product[] getProducts() {
        synchronized(products){
            return getProductsArray();
        }
    }

    @Override
    public Product[] findProducts(String containsText) {
        synchronized(products) {
            return getProductsArray(containsText);
        }
    }
}
