package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Product> getSingleProduct(Long id) throws ProductNotFoundException;
    ResponseEntity<List<Product>> getAllProducts();
    Product createProduct(Long id, String title, Double price, String description, String image, String category);
    ResponseEntity<Object> updateProduct(Long id, String title, Double price, String description, String image, String category);
    ResponseEntity<Object> deleteProductById(Long id);
}
