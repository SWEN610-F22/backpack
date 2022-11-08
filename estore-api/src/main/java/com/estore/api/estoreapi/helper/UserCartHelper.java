package com.estore.api.estoreapi.helper;

import java.io.IOException;
import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.UserCart;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;

public class UserCartHelper {
    ProductDAO productDao;
    UserDAO userDao;

    public UserCartHelper(ProductDAO productDAO, UserDAO userDAO) {
        this.productDao = productDAO;
        this.userDao = userDAO;
    }

    public UserCart convertCart(CartItem[] items) throws IOException{ 
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

    public Product convertCartItem(CartItem item) throws IOException{
        Product product = productDao.getProduct(item.getProductId());
        product.setQuantity(item.getQuantity());
        return product;
    }



    
}
