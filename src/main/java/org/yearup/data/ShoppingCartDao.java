package org.yearup.data;

import org.yearup.models.ShoppingCart;

import java.sql.Connection;
import java.sql.SQLException;


public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart addProductToCart(int userId, int productId, int quantity);

    void clearCart(int userId);

    boolean updateProductInCart(int userId, int productId, int quantity);

    Connection getConnection() throws SQLException;

    boolean addProduct(int userId, int productId, int i);


    // add additional method signatures here
}
