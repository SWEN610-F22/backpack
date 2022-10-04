package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;

    public ProductController(ProductDAO productDAO) {
        this.productDao = productDAO;
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name
     * contains the text in name. If text is empty, returns all products.
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts(@RequestParam(required = false) String name) {
        try {
            Product[] products;
            if (name == null) {
                products = productDao.getProducts();
            } else {
                products = productDao.findProducts(name);
            }
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
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
            Product product = productDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /product " + product);

        try {
            Product newProduct = productDao.updateProduct(product);
            if (newProduct != null)
                return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /product " + product);
        try {
            Product[] existingProducts = productDao.findProducts(product.getName());
            if (existingProducts.length > 0) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                Product createdProduct = productDao.createProduct(product);
                return new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Product[]> deleteProducts(@RequestParam(required = false) String name) {
        try {
            Product[] products;
            if (name == null) {
                products = productDao.getProducts();
                System.out.println("no products deleted");
            } else {
                products = productDao.findProducts(name);
                List<Product> list = Arrays.asList(products);
                for (Product p : list) {
                    if (p.getName() == name) {
                        list.remove(p);
                    }
                }
                products = (Product[]) list.toArray();
            }
            return new ResponseEntity<Product[]>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
