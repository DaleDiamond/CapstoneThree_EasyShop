package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId();

    void updateCart(int userId, int prodcutId, int quantity);

    void clearCart(int userId);


    // add additional method signatures here
}
