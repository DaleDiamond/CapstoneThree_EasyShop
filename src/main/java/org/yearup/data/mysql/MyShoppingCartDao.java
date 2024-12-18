package org.yearup.data.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;


@Component
public class MyShoppingCartDao implements ShoppingCartDao {
    private final JdbcTemplate jdbcTemplate;
    private final ProductDao productDao;

    public MyShoppingCartDao(JdbcTemplate jdbcTemplate, ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.productDao = productDao;
    }


    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
    }

    @Override
    public void updateCart(int userId, int prodcutId, int quantity) {

    }

    @Override
    public void clearCart(int userId) {

    }

    @Override
    public void addProductToCart(int userId, int productId) {

    }
}
