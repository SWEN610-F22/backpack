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
import com.estore.api.estoreapi.helper.UserCartHelper;
import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.UserCart;
import com.estore.api.estoreapi.persistence.CartDAO;

@RestController
@RequestMapping("cart")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDao;
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public CartController(CartDAO cartdao, ProductDAO productDao, UserDAO userDAO) {
        this.cartDao = cartdao;
        this.productDAO = productDao;
        this.userDAO = userDAO;
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
    @GetMapping("{userId}")
    public ResponseEntity<UserCart> getCartForUser(@PathVariable int userId) {
        try {
            CartItem[] cart = cartDao.getCartForUser(userId);
            UserCartHelper cartHelper = new UserCartHelper(productDAO, userDAO);
            UserCart userCart = cartHelper.convertCart(cart);
            return new ResponseEntity<UserCart>(userCart, HttpStatus.OK);
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
    @PutMapping("decrease")
    public ResponseEntity<Product> decrease(@RequestBody CartItem cartItem) {
        LOG.info("GET /cart/decrease " + cartItem);
        try {
            CartItem decreasedCart = cartDao.decrease(cartItem.getProductId(), cartItem.getUserId());
            UserCartHelper cartHelper = new UserCartHelper(productDAO, userDAO);
            Product product = cartHelper.convertCartItem(decreasedCart);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
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
    @PutMapping("increase")
    public ResponseEntity<Product> increase(@RequestBody CartItem cartItem) {
        LOG.info("PUT /cart/increase " + cartItem);
        try {
            CartItem increasedCart = cartDao.increase(cartItem.getProductId(), cartItem.getUserId());
            UserCartHelper cartHelper = new UserCartHelper(productDAO, userDAO);
            Product product = cartHelper.convertCartItem(increasedCart);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
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
    public ResponseEntity<Product> addToCart(@RequestBody CartItem cartItem) {
        LOG.info("POST /cart " + cartItem);
        try {
            CartItem createdCartItem = cartDao.addToCart(cartItem);
            UserCartHelper cartHelper = new UserCartHelper(productDAO, userDAO);
            Product product = cartHelper.convertCartItem(createdCartItem);
            return new ResponseEntity<Product>(product, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PutMapping("clear")
    public ResponseEntity<UserCart> clearItem(@RequestBody CartItem cartItem) {
        LOG.info("PUT /clear " + cartItem);
        try {
            cartDao.clearItem(cartItem.getProductId(), cartItem.getUserId());
            CartItem[] cart = cartDao.getCartForUser(cartItem.getUserId());
            UserCartHelper cartHelper = new UserCartHelper(productDAO, userDAO);      
            UserCart userCart = cartHelper.convertCart(cart);
            return new ResponseEntity<UserCart>(userCart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("clear/{userId}")
    public ResponseEntity<Boolean> deleteUserCart(@PathVariable int userId) {
        try {
            cartDao.clearUserCart(userId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
