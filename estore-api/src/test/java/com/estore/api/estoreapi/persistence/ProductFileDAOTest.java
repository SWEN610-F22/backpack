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
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductFileDAOTest {
    ProductFileDAO productFileDAO; 
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);
        testProducts[1] = new Product(2, "Fishing rod 2", "Can be used for fishing", 35.0, 10);
        testProducts[2] = new Product(3, "Fishing rod 3", "Can be used for fishing", 35.0, 10);

        when(mockObjectMapper
            .readValue(new File("products.json"),Product[].class))
                .thenReturn(testProducts);
        productFileDAO = new ProductFileDAO("products.json",mockObjectMapper);
    }

    @Test
    void getProducts(){
        Product[] products = productFileDAO.getProducts();
        System.out.println(products[0]);
    }
}
