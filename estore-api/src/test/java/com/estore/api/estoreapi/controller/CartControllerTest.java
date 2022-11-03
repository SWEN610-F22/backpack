package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.CartDAO;

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;
    private ProductDAO mockProductDAO;
    private int userId;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockCartDAO = mock(CartDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        cartController = new CartController(mockCartDAO, mockProductDAO);
        userId = 1;
    }

    @Test
    public void getCartItem() throws IOException {
        CartItem product = new CartItem(3, 9, 5, 5);
    
        when(mockCartDAO.getProduct(product.getId())).thenReturn(product);
        ResponseEntity<CartItem> response = cartController.getCartItem(product.getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void getProductNotFound() throws Exception {
        int productId = 99;
        when(mockCartDAO.getProduct(productId)).thenReturn(null);
        ResponseEntity<CartItem> response = cartController.getCartItem(productId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }


    @Test
    public void createProductSuccessfully() throws IOException {
        CartItem cartItem = new CartItem(5, 5, 5, 5);
        when(mockCartDAO.addToCart(cartItem, 5)).thenReturn(cartItem);
        ResponseEntity<CartItem> response = cartController.addToCart(cartItem);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    public void updateProductSuccessfully() throws IOException {
        CartItem cartItem = new CartItem(7, 7, 7, 7);
        when(mockCartDAO.updateInCart(cartItem)).thenReturn(cartItem);
        ResponseEntity<CartItem> response = cartController.updateCart(cartItem);
        cartItem.setQuantity(9);
        response = cartController.updateCart(cartItem);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cartItem,response.getBody());
    }

    @Test
    public void updateProductFailed() throws IOException {
        CartItem cartItem = new CartItem(8, 8, 8, 8);
        when(mockCartDAO.updateInCart(cartItem)).thenReturn(null);
        ResponseEntity<CartItem> response = cartController.updateCart(cartItem);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void updateProductHandleException() throws IOException {
        CartItem cartItem = new CartItem(1, 1, 1, 1);
        doThrow(new IOException()).when(mockCartDAO).updateInCart(cartItem);
        ResponseEntity<CartItem> response = cartController.updateCart(cartItem);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void getProducts() throws IOException {
        CartItem[] cartItems = new CartItem[2];
        cartItems[0] = new CartItem(1, 1, 1, 1);
        cartItems[1] = new CartItem(2, 2, 2, 2);
        when(mockCartDAO.getCart()).thenReturn(cartItems);
        ResponseEntity<CartItem[]> response = cartController.getCart();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cartItems,response.getBody());
    }

    @Test
    public void getProductsWithException() throws IOException {
        doThrow(new IOException()).when(mockCartDAO).getCart();
        ResponseEntity<CartItem[]> response = cartController.getCart();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException {
        int productId = 99;
        when(mockCartDAO.deleteFromCart(productId)).thenReturn(true);
        ResponseEntity<CartItem> response = cartController.deleteProduct(productId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        int productId = 99;
        when(mockCartDAO.deleteFromCart(productId)).thenReturn(false);
        ResponseEntity<CartItem> response = cartController.deleteProduct(productId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException {
        int productId = 99;
        doThrow(new IOException()).when(mockCartDAO).deleteFromCart(productId);
        ResponseEntity<CartItem> response = cartController.deleteProduct(productId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testIncrease() throws IOException {
        CartItem[] cartItems = new CartItem[2];
        cartItems[0] = new CartItem(1, 1, 1, 1);
        cartItems[1] = new CartItem(2, 2, 2, 2);
        Product[] products = new Product[3];
        products[0] = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        when(mockCartDAO.getCart()).thenReturn(cartItems);
        when(mockProductDAO.getCart(cartItems, 1)).thenReturn(products);
        when(mockCartDAO.increase(1, 1)).thenReturn(cartItems);
    }

    @Test
    public void testDecrease() throws IOException {
        CartItem[] cartItems = new CartItem[2];
        cartItems[0] = new CartItem(1, 1, 1, 1);
        cartItems[1] = new CartItem(2, 2, 2, 2);
        mockCartDAO.decrease(1, 1);
        mockCartDAO.decrease(2, 2);
        when(mockCartDAO.getCart()).thenReturn(cartItems);
        ResponseEntity<CartItem[]> response = cartController.getCart();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cartItems,response.getBody());
    }

    @Test
    public void testClear() throws IOException {
        CartItem[] cartItems = new CartItem[2];
        cartItems[0] = new CartItem(1, 1, 1, 1);
        cartItems[1] = new CartItem(2, 2, 2, 2);
        mockCartDAO.clearItem(1, 1);
        mockCartDAO.clearItem(2, 2);
        when(mockCartDAO.getCart()).thenReturn(cartItems);
        ResponseEntity<CartItem[]> response = cartController.getCart();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cartItems,response.getBody());
    }

    @Test
    void testGetCartForUser() throws IOException{
        CartItem[] cartItems = new CartItem[2];
        cartItems[0] = new CartItem(1, 1, 1, 1);
        cartItems[1] = new CartItem(2, 2, 2, 2);
        Product[] products = new Product[3];
        products[0] = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        when(mockCartDAO.getCart()).thenReturn(cartItems);
        when(mockProductDAO.getCart(cartItems, 1)).thenReturn(products);

        ResponseEntity<Product[]> response = cartController.getCartForUser(1);
        assertArrayEquals(products, response.getBody());
    }

    

    // @Test
    // void testGetCartForUserWithException() throws IOException{
    //     CartItem[] cartItems = new CartItem[2];
    //     cartItems[0] = new CartItem(1, 1, 1, 1);
    //     cartItems[1] = new CartItem(2, 2, 2, 2);
    //     when(mockCartDAO.getCart()).thenReturn(cartItems);
    //     doThrow(new IOException()).when(mockProductDAO).getCart(cartItems, 1);
    //     ResponseEntity<Product[]> response = cartController.getCartForUser(1);
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

}