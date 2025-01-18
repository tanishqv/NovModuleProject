package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.dto.FakeStoreProductDTO;
import com.scaler.NovModuleProject.exceptions.CategoryNotFoundException;
import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class FakeStoreCategoryService implements FSCategoryService {

    private RestTemplate restTemplate;

    @Override
    public List<String> getAllCategories() throws CategoryNotFoundException {
        String[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );
        if (categories.length == 0) {
            throw new CategoryNotFoundException("No categories available to list");
        }

        List<String> cat = new ArrayList<>();
        Collections.addAll(cat, categories);

        return cat;
    }

    @Override
    public List<Product> getProductsByCategoryTitle(String categoryTitle) throws ProductNotFoundException {
        FakeStoreProductDTO[] fakeStoreProductDTOs = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + categoryTitle,
                FakeStoreProductDTO[].class
        );

        if (fakeStoreProductDTOs.length == 0) {
            throw new ProductNotFoundException("No products available to list in category " + "\""+ categoryTitle +"\"");
        }

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO productDTO: fakeStoreProductDTOs) {
            products.add(productDTO.getProduct());
        }

        return products;
    }
}
