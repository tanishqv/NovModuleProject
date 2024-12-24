package com.scaler.NovModuleProject.controller;

import com.scaler.NovModuleProject.dto.ErrorDTO;
import com.scaler.NovModuleProject.exceptions.CategoryNotFoundException;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.service.FSCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FakeStoreCategoryController {

    FSCategoryService fsCategoryService;

    public FakeStoreCategoryController(FSCategoryService fsCategoryService) {
        this.fsCategoryService = fsCategoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<String>> getAllCategories() throws CategoryNotFoundException {
        List<String> categories = fsCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{title}/products")
    public ResponseEntity<List<Product>> getProductsByCategoryTitle(@PathVariable String categoryTitle) throws ProductNotFoundException {
        List<Product> products = fsCategoryService.getProductsByCategoryTitle(categoryTitle);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @ExceptionHandler({CategoryNotFoundException.class, ProductNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
