package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.TestPropertySource;

public class CartFileDaoTest {
    CartFileDAO cartFileDAO;
    CartItem[] testProducts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new CartItem[3];
        testProducts[0] = new CartItem(1, 1, 1, 1);
        testProducts[1] = new CartItem(2, 2, 2, 2);
        testProducts[2] = new CartItem(3, 3, 3, 3);

        when(mockObjectMapper
                .readValue(new File("samplecart.json"), CartItem[].class))
                .thenReturn(testProducts);
        cartFileDAO = new CartFileDAO("samplecart.json", mockObjectMapper);
    }

    @Test
    void getCart() {
        CartItem[] cartItems = cartFileDAO.getCart();
        assertEquals(cartItems.length, cartItems.length);
        for (int i = 0; i < testProducts.length; i++) {
            assertEquals(testProducts[i], cartItems[i]);
        }
    }

    @Test
    void updateCart() {
        CartItem cartItem = new CartItem(1, 1, 1, 5);
        CartItem result = assertDoesNotThrow(() -> cartFileDAO.updateInCart(cartItem), "Unexpected exception thrown");
        assertNotNull(result);
        CartItem realItem = cartFileDAO.getProduct(cartItem.getId());
        assertEquals(realItem, cartItem);
    }

    @Test
    void getProduct() {
        CartItem cartItem = cartFileDAO.getProduct(1);
        assertEquals(cartItem, testProducts[0]);
    }

    @Test
    public void testAddItem() {
        CartItem cartItem = new CartItem(1, 1, 1, 1);
        CartItem result = assertDoesNotThrow(() -> cartFileDAO.addToCart(cartItem, 1), "Unexpected exception thrown");
        assertNotNull(result);
        CartItem actual = cartFileDAO.getProduct(cartItem.getId());
        assertEquals(actual.getId(), cartItem.getId());
        assertEquals(actual.getUserId(), cartItem.getUserId());
        assertEquals(actual.getProductId(), cartItem.getProductId());
        assertEquals(actual.getQuantity(), cartItem.getQuantity());

    }

    @Test
    public void testDeleteItem() {
        CartItem cartItem = new CartItem(2, 2, 2, 2);
        CartItem realProduct = cartFileDAO.getProduct(cartItem.getId());
        Boolean resultTrue = assertDoesNotThrow(() -> cartFileDAO.deleteFromCart(realProduct.getId()), "Unexpected exception thrown");
        assertNotNull(resultTrue);
        Boolean resultFalse = assertDoesNotThrow(() -> cartFileDAO.deleteFromCart(realProduct.getId()), "Unexpected exception thrown");
        assertEquals(resultTrue, true);
        assertEquals(resultFalse, false);

    }

}