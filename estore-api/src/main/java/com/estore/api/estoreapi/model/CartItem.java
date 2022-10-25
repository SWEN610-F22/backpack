package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a product
 */
public class CartItem {
    @JsonProperty("id") private int id;
    @JsonProperty("userId") private int userId;
    @JsonProperty("productId") private int productId;
    @JsonProperty("quantity") private int quantity;

    public CartItem(@JsonProperty("id") int id, @JsonProperty("userId") int userId, @JsonProperty("productId") int productId, @JsonProperty("quantity") int quantity) {
        this.id=id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId(){
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product [id=" + id +", userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        if (id != other.id)
            return false;
        return true;
    }

   
    
}
