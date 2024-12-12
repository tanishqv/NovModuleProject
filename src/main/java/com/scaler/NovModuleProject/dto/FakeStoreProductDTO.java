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

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Double getPrice() {
	return price;
    }

    public void setPrice(Double price) {
	this.price = price;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

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

    @Override
    public String toString() {
	return "{id="+id+"\n,title="+title+"\n,price="+price+"\n,description="+description+"\n,image="+image+"\n,category="+category+"}\n";
    }

}
