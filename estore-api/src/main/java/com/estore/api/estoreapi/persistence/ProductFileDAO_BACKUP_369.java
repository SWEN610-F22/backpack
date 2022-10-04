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

    private Product[] getProductsArray(){
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : products.values()) {
            productsList.add(product);

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

<<<<<<< HEAD
    
    /**
    ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getId()) == false)
                return null;  // product does not exist

            products.put(product.getId(), product);
            save();
            return product;
        }
    }

    public boolean save()
    {
        return false;
    }
=======
    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * @return true if the {@link Product products} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            Product newProduct = new Product(nextId(),product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
            products.put(newProduct.getId(),newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }


>>>>>>> 7a8049a79b6bbeabf44a84841b2bd086d833e83d
}