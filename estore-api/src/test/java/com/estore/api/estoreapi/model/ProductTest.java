package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ProductTest {
    Product product = new Product(14,"Hiking Stick","Can be used for hiking", 20.0, 5);
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
