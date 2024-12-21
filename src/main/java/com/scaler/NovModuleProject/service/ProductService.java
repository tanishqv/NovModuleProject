package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(Long id, String title, Double price, String description, String image, String category);
    Product updateProduct(Long id, String title, Double price, String description, String image, String category);
    Product deleteProductById(Long id) throws ProductNotFoundException;
}
