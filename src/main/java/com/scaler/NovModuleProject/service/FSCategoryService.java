package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.CategoryNotFoundException;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;

import java.util.List;

public interface FSCategoryService {
    List<String> getAllCategories() throws CategoryNotFoundException;
    List<Product> getProductsByCategoryTitle(String title) throws ProductNotFoundException;
}
