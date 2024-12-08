package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.models.Product;

import java.util.List;

public class FakeStoreProductService implements ProductService {
    @Override
    public Product getProduct(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}
