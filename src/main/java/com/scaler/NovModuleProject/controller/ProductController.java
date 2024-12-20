package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.dto.ErrorDTO;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.service.ProductService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getTitle()
        );
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts() throws ProductNotFoundException {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product dataSent = productService.updateProduct(
                id,
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory().getTitle()
        );
        return new ResponseEntity<>(dataSent, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Product dataDeleted = productService.deleteProductById(id);

        // HttpStatus.NO_CONTENT does not allow any request body to be sent as response
        // hence, HttpStatus.OK has to be sent instead.

        // return new ResponseEntity<>(dataDeleted, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(dataDeleted, HttpStatus.OK);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
