package com.estore.api.estoreapi.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.UserCart;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;

public class UserCartHelper {
    ProductDAO productDao;

    public UserCartHelper(ProductDAO productDAO) {
        this.productDao = productDAO;
    }

    public Product[] convertCart(CartItem[] items) throws IOException{ 
        ArrayList<Product> cart = new ArrayList<>();
        System.out.println(Arrays.toString(items));
        for (CartItem cartItem : items) {
            Product product = productDao.getProduct(cartItem.getProductId());
            product.setQuantity(cartItem.getQuantity());
            cart.add(product);
        }
        System.out.println("Products:"+cart);
        Product[] products = cart.toArray(new Product[0]);
        System.out.println(Arrays.toString(products));
        return products;
    }

    public Product convertCartItem(CartItem item) throws IOException{
        Product product = productDao.getProduct(item.getProductId());
        product.setQuantity(item.getQuantity());
        return product;
    }



    
}
