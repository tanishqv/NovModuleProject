package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.CategoryNotFoundException;
import com.scaler.NovModuleProject.exceptions.DuplicateCategoryException;
import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.projections.CategoryProjection;
import com.scaler.NovModuleProject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbCategoryService implements CategoryService {

    CategoryRepository categoryRepository;

    public DbCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getSingleCategory(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        throw new CategoryNotFoundException("Category not found in DB with id=" + id);
    }

    @Override
    public List<CategoryProjection> getAllCategories() throws CategoryNotFoundException {
        List<CategoryProjection> categories = categoryRepository.findAllUsingProjection();
        if (!categories.isEmpty()) {
            return categories;
        }
        throw new CategoryNotFoundException("No categories available in DB to list");
    }

    @Override
    public Category createCategory(String title) {
        Optional<Category> currCategory = categoryRepository.findByTitle(title);
        if (currCategory.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setTitle(title);
            return categoryRepository.save(newCategory);
        }
        throw new DuplicateCategoryException("Category already exists with title=" + title + " at id=" + currCategory.get().getId());
    }
}
