package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.dto.ErrorDTO;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.service.ProductService;
import org.springframework.http.ResponseEntity;
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
    public Product getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getSingleProduct(id);
    }

    @GetMapping("/product")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(
                id,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getTitle()
        );
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productService.deleteProductById(id);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorDTO handleProductNotFoundException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        return errorDTO;
    }
}
