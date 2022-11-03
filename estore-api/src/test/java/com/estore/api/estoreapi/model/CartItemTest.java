package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class CartItemTest {
CartItem cartItem = new CartItem(1, 1, 1, 1);
    @Test
    void createProduct(){
        CartItem cartItem = new CartItem(1, 2, 3, 4);
        assertEquals(1, cartItem.getId());
        assertEquals(3, cartItem.getProductId());
        assertEquals(2, cartItem.getUserId());
        assertEquals(4, cartItem.getQuantity());
    }

    @Test
    void equals(){
        CartItem cartItemOriginal = new CartItem(1, 1, 1, 1);
        CartItem cartSame = new CartItem(1, 1, 1, 1);
        CartItem cartNewDiff = new CartItem(2, 2, 2, 2);

        assertTrue(cartItemOriginal!=cartNewDiff);
        assertTrue(cartItemOriginal!=null);
        assertTrue(cartItemOriginal.getId()!=cartNewDiff.getId());
        assertTrue(cartItemOriginal.getUserId()!=cartNewDiff.getUserId());
        assertTrue(cartItemOriginal.getProductId()!=cartNewDiff.getProductId());
        assertTrue(cartItemOriginal.getQuantity()!=cartNewDiff.getQuantity());

        assertTrue(cartItemOriginal==cartItemOriginal);
        assertTrue(cartItemOriginal.getClass()==cartSame.getClass());
        assertTrue(cartItemOriginal.getClass()==cartNewDiff.getClass());
        assertTrue(null==null);
        assertTrue(cartItemOriginal.getId()==cartSame.getId());
        assertTrue(cartItemOriginal.getUserId()==cartSame.getUserId());
        assertTrue(cartItemOriginal.getProductId()==cartSame.getProductId());
        assertTrue(cartItemOriginal.getQuantity()==cartSame.getQuantity());
    }


    @Test
    void setUserId() {
        cartItem.setUserId(15);
        assertEquals(15, cartItem.getUserId());
    }

    @Test
    void setProductId() {
        cartItem.setProductId(15);
        assertEquals(15, cartItem.getProductId());
    }

    @Test
    void setQuantity() {
        cartItem.setQuantity(2);
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    public void testToString(){
        assertEquals("Product [id=" + cartItem.getId() +", userId=" + cartItem.getUserId() + ", productId=" + cartItem.getProductId() + ", quantity=" + cartItem.getQuantity() + "]",
        cartItem.toString());
    }

    @Test
    public void testEqualsNull(){
        CartItem cartItem = new CartItem(1, 2, 3, 4);
        assertFalse(cartItem.equals(null));   
    }

    @Test
    public void testEqualsDifferentObjects(){
        CartItem cartItem = new CartItem(1, 2, 3, 4);
        User user = new User(1, "user", false);
        assertFalse(cartItem.equals(user));   
    }

    @Test
    public void testEqualsProductWithDifferentId(){
        CartItem cartItem = new CartItem(1, 2, 3, 4);        
        CartItem cartItem2 = new CartItem(2, 2, 3, 4); 
        assertNotEquals(cartItem, cartItem2);
    }

    @Test
    public void testEqualsWithSameId(){
        CartItem cartItem = new CartItem(1, 2, 3, 4);        
        CartItem cartItem2 = new CartItem(1, 2, 3, 4); 
        assertEquals(cartItem, cartItem2);
    }


}