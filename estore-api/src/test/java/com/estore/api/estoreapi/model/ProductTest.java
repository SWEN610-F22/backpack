package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ProductTest {

    @Test
    void createProduct(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);
        assertEquals(1, product.getId());
        assertEquals("Fishing rod", product.getName());
        assertEquals("Can be used for fishing", product.getDescription());
        assertEquals(35.0, product.getPrice());
        assertEquals(10, product.getQuantity());
    }
    
}
