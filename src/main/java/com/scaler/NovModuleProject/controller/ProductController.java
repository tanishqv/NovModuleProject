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
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getTitle()
        );
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getSingleProduct(id);
    }

    public void updateProduct(Product product) {}

    public void deleteProduct(Long id) {}
}
