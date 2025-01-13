package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.exceptions.CategoryNotFoundException;
import com.scaler.NovModuleProject.exceptions.DuplicateCategoryException;
import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.projections.CategoryProjection;
import com.scaler.NovModuleProject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) throws DuplicateCategoryException {
        Category newCategory = categoryService.createCategory(category.getTitle());
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        Category category = categoryService.getSingleCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryProjection>> getCategories() throws CategoryNotFoundException {
        List<CategoryProjection> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
