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
    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        cartController = new CartController(mockCartDAO, mockProductDAO);
    }

   @Test
   void getAllCartItems(){
    CartItem[] array = new CartItem[1];
    CartItem item = new CartItem(1, 2, 3);
    when(mockCartDAO.getCart()).thenReturn(array);
   }

}