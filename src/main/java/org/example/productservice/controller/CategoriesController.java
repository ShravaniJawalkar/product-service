package org.example.productservice.controller;


import org.example.productservice.dao.CategoryRequest;
import org.example.productservice.dao.CategoryResponse;
import org.example.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoryService categoryService;


    @PostMapping
    private ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateCategory(@PathVariable("id") Long id, @RequestBody String categoryName) {
        return categoryService.updateCategory(id, categoryName);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    private ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        return categoryService.deleteCategory(id);
    }
}
