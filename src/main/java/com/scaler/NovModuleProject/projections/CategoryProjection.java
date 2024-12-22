package com.scaler.NovModuleProject.projections;

import com.scaler.NovModuleProject.models.Product;

import java.util.List;

public interface CategoryProjection {
    Long getId();
    String getTitle();
    List<Product> getProducts();
}
