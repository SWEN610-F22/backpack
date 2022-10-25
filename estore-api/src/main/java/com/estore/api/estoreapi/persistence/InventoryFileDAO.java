package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class InventoryFileDAO implements InventoryDao {
    Map<Integer, Product> inventory;
    private ObjectMapper objectMapper;
    private String filename;

    public InventoryFileDAO(@Value("${inventory.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean load() throws IOException {
        inventory = new TreeMap<>();
        Product[] productsArray = objectMapper.readValue(new File(filename), Product[].class);
        for (Product product : productsArray) {
            inventory.put(product.getId(), product);
        }
        return true;
    }

    private Product[] getProductsArray(){
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : inventory.values()) {
            productsList.add(product);

        }

        Product[] productArray = new Product[productsList.size()];
        productsList.toArray(productArray);
        return productArray;
    }
    @Override
    public Product[] getInventory() throws IOException {
        synchronized (inventory) {
            return getProductsArray();
        }
    }

    @Override
    public Product getProduct(int id) throws IOException {
        synchronized (inventory) {
            return inventory.getOrDefault(id, null);
        }
    }

    @Override
    public boolean isInventoryEmpty() {
        return inventory.size() == 0;
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * @return true if the {@link Product products} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    @Override
    public Product updateInventory(Product product) throws IOException {
        synchronized(inventory) {
            if (!inventory.containsKey(product.getId()))
                return null;  // product does not exist

            if(product.getQuantity()==0){
                deleteFromInventory(product.getId());
            }
            else{
                inventory.put(product.getId(), product);
                save();
            }
            return product;
        }
    }

    @Override
    public Product addToInventory(Product product) throws IOException {
        synchronized (inventory) {
            Product newProduct = new Product(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
            if(inventory.containsKey(newProduct.getId())){
                int oldQuantity = getProduct(newProduct.getId()).getQuantity();
                int newQuantity = newProduct.getQuantity() + oldQuantity;
                newProduct.setQuantity(newQuantity);
            }
            inventory.put(newProduct.getId(), newProduct);
            return newProduct;
        }
    }

    @Override
    public boolean deleteFromInventory(int id) throws IOException {
        synchronized (inventory){
            if(inventory.containsKey(id)){
                inventory.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
