package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.models.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);
    List<Product> getAllProducts();
    Product createProduct(Product product);
}
