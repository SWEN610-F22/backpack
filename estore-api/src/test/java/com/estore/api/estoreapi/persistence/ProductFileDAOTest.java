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
import org.springframework.test.context.TestPropertySource;

public class ProductFileDAOTest {
    ProductFileDAO productFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);
        testProducts[1] = new Product(2, "Camping gear", "Can be used for gear", 5.0, 2);
        testProducts[2] = new Product(3, "Hiking boots", "Can be used for boots", 13.0, 5);

        when(mockObjectMapper
                .readValue(new File("products.json"), Product[].class))
                .thenReturn(testProducts);
        productFileDAO = new ProductFileDAO("products.json", mockObjectMapper);
    }

    @Test
    void getProducts() {
        Product[] products = productFileDAO.getProducts();
        assertEquals(products.length, products.length);
        for (int i = 0; i < testProducts.length; i++) {
            assertEquals(testProducts[i], products[i]);
        }
    }

    @Test
    public void findProducts() {
        Product[] products = productFileDAO.findProducts("camp");
        assertEquals(1, products.length);
    }

    @Test
    public void findProductWithoutText() {
        Product[] products = productFileDAO.findProducts(null);
        assertEquals(testProducts.length, products.length);
    }

    @Test
    void updateProduct() {
        Product product = new Product(1, "BootsBootsBoots", "They are indeed boots", 100.00, 10000);
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product), "Unexpected exception thrown");
        assertNotNull(result);
        Product realProduct = productFileDAO.getProduct(product.getId());
        assertEquals(realProduct, product);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Product[].class));
        Product product = new Product(1, "BootsBootsBoots", "They are indeed boots", 100.00, 10000);
        assertThrows(IOException.class, () -> productFileDAO.createProduct(product), "IOException not thrown");
    }

    @Test
    void getProduct() {
        Product product = productFileDAO.getProduct(1);
        assertEquals(product, testProducts[0]);
    }

    @Test
    public void testCreateProduct() {
        // Setup
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.createProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(), product.getId());
        assertEquals(actual.getName(), product.getName());
        assertEquals(actual.getDescription(), product.getDescription());
        assertEquals(actual.getPrice(), product.getPrice());
        assertEquals(actual.getQuantity(), product.getQuantity());

    }

    @Test
    public void testDeleteProduct() {
        // new product
        Product product = new Product(1, "Fishing Rod", "a fishing rod", 10.11, 100);
        // product currently in system with same id
        Product realProduct = productFileDAO.getProduct(product.getId());

        // delete product
        Boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(realProduct.getId()),
                "Unexpected exception thrown");

        // check if its there
        assertNotNull(result);

        // create new product with same id
        Product result2 = assertDoesNotThrow(() -> productFileDAO.createProduct(product),
                "Unexpected exception thrown");

        // check if its there
        assertNotNull(result2);

        assertEquals(result, true);
        assertEquals(result2, false);

    }

}
