package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();

        String sql = "SELECT shopping_cart.*, products.* " +
                "FROM shopping_cart " +
                "JOIN products ON shopping_cart.product_id = products.product_id " +
                "WHERE shopping_cart.user_id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet row = statement.executeQuery()) {
                while (row.next()) {
                    int quantity = row.getInt("quantity");
                    Product product = new Product();
                    product.setProductId(row.getInt("products.product_id"));
                    product.setName(row.getString("name"));
                    product.setPrice(row.getBigDecimal("price"));
                    product.setCategoryId(row.getInt("category_id"));
                    product.setDescription(row.getString("description"));
                    product.setColor(row.getString("color"));
                    product.setStock(row.getInt("stock"));
                    product.setFeatured(row.getBoolean("featured"));
                    product.setImageUrl(row.getString("image_url"));

                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                    shoppingCartItem.setProduct(product);
                    shoppingCartItem.setQuantity(quantity);
                    shoppingCart.add(shoppingCartItem);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting shopping cart for user ID: " + userId, e);
        }

        return shoppingCart;
    }

    @Override
    public ShoppingCart addProductToCart(int userId, int productId, int quantity) {
        String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) " +
                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setInt(4, quantity);
            statement.executeUpdate();
            return getByUserId(userId);

        } catch (SQLException e) {
            throw new RuntimeException("Error adding product to cart for user ID: " + userId, e);
        }
    }

    @Override
    public boolean updateProductInCart(int userId, int productId, int quantity) {
        String query = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating product in cart for user ID: " + userId + ", product ID: " + productId, e);
        }
    }

    @Override
    public boolean addProduct(int userId, int productId, int i) {
        return false;
    }


    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error clearing cart for user ID: " + userId, e);
        }

    }
}
