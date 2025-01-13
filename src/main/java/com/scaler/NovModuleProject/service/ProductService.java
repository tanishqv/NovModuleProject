package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;
    Page<Product> getAllProducts(Integer pageNum, Integer pageSize, String fieldName, Boolean sortAscending) throws ProductNotFoundException;
    Product createProduct(Long id, String title, Double price, String description, String image, String category);
    Product updateProduct(Long id, String title, Double price, String description, String image, String category) throws ProductNotFoundException;
    Product deleteProductById(Long id) throws ProductNotFoundException;
}
