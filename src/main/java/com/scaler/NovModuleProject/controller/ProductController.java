package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    // DI for ProductService
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CRUD Operations
    @PostMapping("/product")
    public void createProduct(Product product) {}

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return null;
    }

    public void updateProduct(Product product) {}

    public void deleteProduct(Long id) {}
}
