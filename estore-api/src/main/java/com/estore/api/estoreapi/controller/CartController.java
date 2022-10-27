package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
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
import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.CartDAO;

@RestController
@RequestMapping("cart")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDao;
    private ProductDAO productDAO;
    private Integer userId = 9;

    public CartController(CartDAO cartdao, ProductDAO productDao) {
        this.cartDao = cartdao;
        this.productDAO = productDao;
    }

    /**
     * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart.
     * 
     * @return ResponseEntity with array of {@link CartItem CartItem} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("")
    public ResponseEntity<CartItem[]> getCart() {
        try {
            CartItem[] cart = cartDao.getCart();
            return new ResponseEntity<CartItem[]>(cart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
     * 
     * @param userId The userId parameter which contains the id used to find the
     *             {@link User user}.
     * 
     * @return ResponseEntity with array of {@link Product products} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("/user")
    public ResponseEntity<Product[]> getCartForUser(@RequestParam(required = true) Integer userId) {
        try {
            CartItem[] fullCart = cartDao.getCart();
            Product[] cart = productDAO.getCart(fullCart, userId);
            return new ResponseEntity<Product[]>(cart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Responds to the GET request for a {@linkplain CartItem CartItem} in cart for the given id
     *
     * @param id The id used to locate the {@link CartItem CartItem}
     *
     * @return ResponseEntity with {@link CartItem CartItem} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable int id) {
        LOG.info("GET /cart/" + id);
        CartItem product = cartDao.getProduct(id);
        if (product != null)
            return new ResponseEntity<CartItem>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Responds to the PUT request for a {@linkplain CartItem CartItem} to cart
     *
     * @param product is a {@link CartItem CartItem}
     *
     * @return ResponseEntity with {@link CartItem CartItem} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<CartItem> updateCart(@RequestBody CartItem cartItem) {
        LOG.info("PUT /cart " + cartItem);

        try {
            CartItem newProduct = cartDao.updateInCart(cartItem);
            if (newProduct != null)
                return new ResponseEntity<CartItem>(cartItem, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   /** 
    * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
    * after the quantity of a {@linkplain Product product} object has been decremented
    * @param productId The productId parameter which contains the id used to find the
    *             {@link Product product}.
    * 
    * @return ResponseEntity with array of {@link Product products} objects (may be
    *         empty) and HTTP status of OK.
    *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
    */
    @GetMapping("/decrease")
    public ResponseEntity<Product[]> decrease(@RequestParam(required = true) Integer productId) {
        LOG.info("GET /cart " + this.userId + " " + productId);
        try {
            CartItem[] decreasedCart = cartDao.decrease(productId, this.userId);
            CartItem[] fullCart = cartDao.getCart();
            Product[] newCart = productDAO.getCart(fullCart, userId);
            return new ResponseEntity<Product[]>(newCart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** 
    * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
    * after the quantity of a {@linkplain Product product} object has been incremented
    * @param productId The productId parameter which contains the id used to find the
    *             {@link Product product}.
    * 
    * @return ResponseEntity with array of {@link Product products} objects (may be
    *         empty) and HTTP status of OK.
    *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
    */
    @GetMapping("/increase")
    public ResponseEntity<Product[]> increase(@RequestParam(required = true) Integer productId) {
        LOG.info("GET /cart " + this.userId + " " + productId);
        try {
            CartItem[] increasedCart = cartDao.increase(productId, this.userId);
            CartItem[] fullCart = cartDao.getCart();
            Product[] newCart = productDAO.getCart(fullCart, userId);
            return new ResponseEntity<Product[]>(newCart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /** 
    * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
    * after the quantity of a {@linkplain Product product} object has been set to zero
    * @param productId The productId parameter which contains the id used to find the
    *             {@link Product product}.
    * 
    * @return ResponseEntity with array of {@link Product products} objects (may be
    *         empty) and HTTP status of OK.
    *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
    */
    @GetMapping("/clear")
    public ResponseEntity<Product[]> clearItem(@RequestParam(required = true) Integer productId) {
        LOG.info("GET /cart " + this.userId + " " + productId);
        try {
            CartItem[] clearedCart = cartDao.clearItem(productId, this.userId);
            CartItem[] fullCart = cartDao.getCart();
            Product[] newCart = productDAO.getCart(fullCart, userId);
            return new ResponseEntity<Product[]>(newCart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Responds to the POST request for a {@linkplain CartItem cartItem} to cart
     *
     * @param product is a {@link CartItem cartItem}
     *
     * @return ResponseEntity with {@link CartItem cartItem} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem product) {
        LOG.info("POST /cart " + product);
        try {
            CartItem createdProduct = cartDao.addToCart(product, this.userId);
            return new ResponseEntity<CartItem>(createdProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain CartItem CartItem} with the given id
     * 
     * @param id The id of the {@link CartItem CartItem} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CartItem> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /cart/" + id);
        try {
            if (cartDao.deleteFromCart(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
