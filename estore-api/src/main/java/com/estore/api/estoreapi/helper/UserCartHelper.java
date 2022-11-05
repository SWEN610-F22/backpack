package com.estore.api.estoreapi.helper;

import java.io.IOException;

import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.UserCart;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;

public class UserCartHelper {
    CartItem[] items;
    ProductDAO productDao;
    UserDAO userDao;

    public UserCartHelper(CartItem[] items, ProductDAO productDAO, UserDAO userDAO) {
        this.items = items;
        this.productDao = productDAO;
        this.userDao = userDAO;
    }

    public UserCart convert() throws IOException{
        UserCart cart = new UserCart();
        CartItem item = items[0];
        User user = userDao.getUser(item.getUserId());
        cart.setUser(user);
        for (CartItem cartItem : items) {
            Product product = productDao.getProduct(cartItem.getProductId());
            product.setQuantity(cartItem.getQuantity());
            cart.addProduct(product);
        }
        return cart;
    }



    
}
