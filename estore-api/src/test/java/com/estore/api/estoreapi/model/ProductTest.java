package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ProductTest {
Product product = new Product(10,"doesn't matter", "it doesn't matter", 45.78, 7);
    @Test
    void createProduct(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);
        assertEquals(1, product.getId());
        assertEquals("Fishing rod", product.getName());
        assertEquals("Can be used for fishing", product.getDescription());
        assertEquals(35.0, product.getPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void equals(){
        Product productOriginal = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);
        Product productSame = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);
        Product productNewDiff = new Product(2, "Not a fishing rod", "Can not be used for fishing", 53.0, 1);
        Product productNull = new Product(2, null, "Can not be used for fishing", 53.0, 1);

        assertTrue(productOriginal!=productNewDiff);
        assertTrue(productOriginal!=null);
        assertTrue(productOriginal.getId()!=productNewDiff.getId());
        assertTrue(productNull.getName()!=productOriginal.getName());
        assertTrue(productOriginal.getName()!=productNewDiff.getName());
        assertTrue(productOriginal.getQuantity()!=productNewDiff.getQuantity());

        assertTrue(productOriginal==productOriginal);
        assertTrue(productOriginal.getClass()==productSame.getClass());
        assertTrue(productOriginal.getClass()==productNewDiff.getClass());
        assertTrue(null==null);
        assertTrue(productOriginal.getId()==productSame.getId());
        assertTrue(productOriginal.getName()==productSame.getName());
        assertTrue(productOriginal.getQuantity()==productSame.getQuantity());
    }


    @Test
    void setId() {
        product.setId(15);
        assertEquals(15, product.getId());
    }
    @Test
    void setName(){
        product.setName("something else");
        assertEquals("something else", product.getName());
    }
    @Test
    void setPrice(){
        product.setPrice(25.67);
        assertEquals(25.67, product.getPrice());
    }
    @Test
    void setQuantity() {
        product.setQuantity(2);
        assertEquals(2, product.getQuantity());
    }
    @Test
    void setDescription(){
        product.setDescription("It doesn't matter");
        assertEquals("It doesn't matter", product.getDescription());
    }

    @Test
    public void testToString(){
        assertEquals("Product [id=" + product.getId() +", name=" + product.getName() + ", description=" + product.getDescription() + ", price=" + product.getPrice() + ", quantity=" + product.getQuantity() + "]", product.toString());
    }

}
