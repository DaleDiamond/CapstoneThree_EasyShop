package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

@RestController
@RequestMapping("/categories")
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
    public List<Category> getAll(){
        return categoryDao.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id)
    {
        return categoryDao.getById(id);
    }


    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)

    public Category addCategory(@RequestBody Category category)
    {
        categoryDao.create(category);
        return category;
    }

    @PutMapping("/{categoryId}/products")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProductToCategory(@PathVariable int CategoryId, @RequestBody Product product)
    {
        return productDao.add(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category category) {
        categoryDao.update(id, category);
        return ResponseEntity.ok("Category update complete");
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id)
    {
        categoryDao.delete(id);
    }
}
