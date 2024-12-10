package com.scaler.NovModuleProject.dto;

import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.models.Product;

public class FakeStoreProductDTO {
    // we can be picky on what values we want from the API
    // Even if we skip the keys from API response, it is not incorrect
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;

    // Map the FakeStore Product to Product model
    public Product getProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        Category cat = new Category();
        cat.setTitle(category);
        product.setCategory(cat);
        return product;
    }

}
