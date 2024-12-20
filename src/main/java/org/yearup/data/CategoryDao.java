package org.yearup.data;

import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.User;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao
{
    User getByUserName(String username);

    List<Category> getAllCategories() throws SQLException;
    Category getById(int categoryId);
    Category create(Category category);
    void update(int categoryId, Category category);
    void delete(int categoryId);


}