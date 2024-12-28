package com.scaler.NovModuleProject.service;

import com.scaler.NovModuleProject.exceptions.ProductNotFoundException;
import com.scaler.NovModuleProject.models.Category;
import com.scaler.NovModuleProject.models.Product;
import com.scaler.NovModuleProject.repository.CategoryRepository;
import com.scaler.NovModuleProject.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service("dbProductService")
public class DbProductService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(id);

        if (p.isPresent()) {
            return p.get();
        }
        throw new ProductNotFoundException("Product not found in DB with id=" + id);
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> products = productRepository.findAll();

        if (!products.isEmpty()) {
            return products;
        }
        throw new ProductNotFoundException("No products available in DB to list");
    }

    @Override
    public Product createProduct(Long id, String title, Double price, String description, String image, String categoryTitle) {
        Product product = new Product();

        // Only findByPK methods in JPA have Optional return type by default, rest have to be made to return Optional type through the Repository
        Optional<Category> currCategory = categoryRepository.findByTitle(categoryTitle);
        if (currCategory.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setTitle(categoryTitle);
            Category newRow = categoryRepository.save(newCategory);
            product.setCategory(newRow);
        } else {
            product.setCategory(currCategory.get());
        }

        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, String title, Double price, String description, String image, String categoryTitle) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            if (title != null) {
                product.get().setTitle(title);
            }
            if (price != null) {
                product.get().setPrice(price);
            }
            if (description != null) {
                product.get().setDescription(description);
            }
            if (image != null) {
                product.get().setImageUrl(image);
            }
            if (categoryTitle != null) {
                System.out.println("Got categoryTitle=" + categoryTitle);
                Optional<Category> currCategory = categoryRepository.findByTitle(categoryTitle);
                if (currCategory.isEmpty()) {
                    System.out.println("Creating new category with " + categoryTitle);
                    Category newCategory = new Category();
                    newCategory.setTitle(categoryTitle);
                    Category newRow = categoryRepository.save(newCategory);
                    product.get().setCategory(newRow);
                }
            }
            return productRepository.save(product.get());
        }
        throw new ProductNotFoundException("Product not found with id=" + id);
    }

    @Override
    public Product deleteProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return product.get();
        }
        throw new ProductNotFoundException("Product not found with id=" + id);
    }
}
