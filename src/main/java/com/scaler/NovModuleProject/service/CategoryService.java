package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.CategoryNotFoundException;
import com.scaler.NovModuleProject.exceptions.DuplicateCategoryException;
import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.projections.CategoryProjection;

import java.util.List;

public interface CategoryService {
    Category getSingleCategory(Long id) throws CategoryNotFoundException;
    List<CategoryProjection> getAllCategories() throws CategoryNotFoundException;
    Category createCategory(String title) throws DuplicateCategoryException;
}
