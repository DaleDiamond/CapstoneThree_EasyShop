package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin

public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryDao.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id)
    {
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public int getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        return categoryId;
    }

    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category)
    {
        // insert the category
        categoryDao.create(category);
        return category;
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id

        categoryDao.update(id, category);
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id)
    {
        // delete the category by id
        categoryDao.delete(id);
    }
}
