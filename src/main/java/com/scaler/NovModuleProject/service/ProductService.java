package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id);
    List<Product> getAllProducts();
    Product createProduct(Product product);
}
