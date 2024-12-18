package org.yearup.data;

import org.yearup.models.ShoppingCart;


public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    void clearCart(int userId);

    void addProductToCart(int userId, int productId);

    void updateProductInCart(int userId, int productId, int quantity);


    // add additional method signatures here
}
