package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.sql.Connection;
import java.sql.SQLException;


public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart addProductToCart(int userId, int productId, int quantity);

    void clearCart(int userId);

    void updateProductInCart(int userId, int productId, int quantity);

    Connection getConnection() throws SQLException;



    // add additional method signatures here
}
