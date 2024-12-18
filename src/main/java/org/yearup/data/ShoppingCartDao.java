package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    void updateCart(int userId, int prodcutId, int quantity);

    void clearCart(int userId);

    void addProductToCart(int userId, int productId);


    // add additional method signatures here
}
