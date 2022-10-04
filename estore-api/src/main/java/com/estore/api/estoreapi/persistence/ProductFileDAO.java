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

/**
 * Implements object persistence of Product objects through JSON file.
 */
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

    /**
     * Loads {@linkplain Product products} from the JSON file into the map and sets nextId to be the greatestId in the file.
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed or read from
     */
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
<<<<<<< HEAD
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

=======

    /**
     * Generates an array of {@linkplain Product products} that includes all the products
     * @return  The array of {@link Product products}, may be empty
     */
>>>>>>> 06e58930d05dcf1c66a9f98d56fdbf551048bb89
    private Product[] getProductsArray(){
        return getProductsArray(null);
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map for any {@linkplain Product products} that contains the text specified by containsText
     * If containsText is null, the array contains all of the {@linkplain Product products} in the tree map
     * @return  The array of {@link Product products}, may be empty
     */
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

<<<<<<< HEAD
=======

    
    /** 
     * Makes a call to read all the products saved in the JSON file and the consequent processes to convert it to an array.
     * @return Product[] of all products, may be empty
     */
>>>>>>> 06e58930d05dcf1c66a9f98d56fdbf551048bb89
    @Override
    public Product[] getProducts() {
        synchronized(products){
            return getProductsArray();
        }
    }

<<<<<<< HEAD
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


=======
    
    /** Finds all products with name matching the string in containsText
     * @param containsText string to be matched against
     * @return Product[] array that matches the search text
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized(products) {
            return getProductsArray(containsText);
        }
    }
>>>>>>> 06e58930d05dcf1c66a9f98d56fdbf551048bb89
}
