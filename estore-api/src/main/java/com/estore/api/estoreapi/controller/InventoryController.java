package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDao inventoryDao;

    public InventoryController(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }
    /**
     * Responds to the GET request for all {@linkplain Product products} in the inventory.
     *
     *
     * @return ResponseEntity with array of {@link Product product} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getInventory() {
        try {
            Product[] products;
            products = inventoryDao.getInventory();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Checks to see if the inventory is empty or not
     * @return HTTP status OK if the inventory is indeed empty, FOUND otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> isInventoryEmpty(){
        try {
            return inventoryDao.isInventoryEmpty()? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.FOUND);
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Responds to the GET request for a {@linkplain Product product} for the given id
     *
     * @param id The id used to locate the {@link Product product}
     *
     * @return ResponseEntity with {@link Product product} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = inventoryDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request for updating a {@linkplain Product product} in the inventory
     *
     * @param product is a {@link Product product}
     *
     * @return ResponseEntity with {@link Product product} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateInventory(@RequestBody Product product) {
        LOG.info("PUT /product " + product);

        try {
            Product newProduct = inventoryDao.updateInventory(product);
            if (newProduct != null)
                return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Responds to the POST request for adding a {@linkplain Product product} to the inventory
     *
     * @param product is a {@link Product product}
     *
     * @return ResponseEntity with {@link Product product} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> addToInventory(@RequestBody Product product) {
        LOG.info("PUT /product " + product);
        try {
            Product addedProduct = inventoryDao.addToInventory(product);
            return new ResponseEntity<Product>(addedProduct, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Deletes a {@linkplain Product product} with the given id
     *
     * @param id The id of the {@link Product product} to be deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id){
        LOG.info("/DELETE/inventory/" + id);
        try{
            return inventoryDao.deleteFromInventory(id)? new ResponseEntity<>(HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
